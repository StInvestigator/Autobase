package org.example.autobase.service.trip;

import org.example.autobase.controller.LogController;
import org.example.autobase.entity.*;
import org.example.autobase.repository.TripRepository;
import org.example.autobase.service.car.CarService;
import org.example.autobase.service.completedTrip.CompletedTripService;
import org.example.autobase.service.driver.DriverService;
import org.example.autobase.service.request.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Thread.sleep;

@Service
@Transactional
public class TripServiceImpl implements TripService {
    @Autowired
    private TripRepository tripRepository;

    @Autowired
    private CompletedTripService completedTripService;

    @Autowired
    private DriverService driverService;

    @Autowired
    private CarService carService;

    @Autowired
    private RequestService requestService;

    @Override
    public void save(Trip trip) {
        tripRepository.save(trip);
    }

    @Override
    public void saveAll(List<Trip> trips) {
        tripRepository.saveAll(trips);
    }

    @Override
    public List<Trip> findAll() {
        return tripRepository.findAll();
    }

    @Override
    public void deleteAll() {
        tripRepository.deleteAll();
    }

    @Override
    public CompletedTrip tripCompleted(Trip trip) {
        driverService.endWork(trip.getDriver(), trip.getRequest().getPayment());
        trip.getCar().setIsFree(true);
        carService.save(trip.getCar());

        tripRepository.delete(trip);
        return completedTripService.save(trip);
    }

    @Override
    public void tripFailed(Trip trip) {
        driverService.endWork(trip.getDriver(), BigDecimal.ZERO);

        trip.getCar().setIsFree(true);
        trip.getCar().setIsBroken(false);
        carService.save(trip.getCar());

        trip.getRequest().setIsTaken(false);
        requestService.save(trip.getRequest());

        tripRepository.delete(trip);
    }

    @Override
    public List<CompletedTrip> nextDay() {
        List<CompletedTrip> completedTrips = new ArrayList<>();
        carService.setBrokenAfterDayRiding();
        for (Trip failedTrip : tripRepository.findAllByCar_IsBroken(true)) {
            LogController.getLogs().add("Car broke down!" +
                    "<br><span>Trip to " + failedTrip.getRequest().getDestinationPoint().getName() + " has been failed..." +
                    "<br>Driver " + failedTrip.getDriver().getName() + " " + failedTrip.getDriver().getSurname() + " is now free" +
                    "<br>Car \"" + failedTrip.getCar().getName() + "\" is repaired and free</span>");
            tripFailed(failedTrip);
        }
        for (Trip trip : tripRepository.findAll()) {
            trip.setDaysRemaining(trip.getDaysRemaining() - 1);
            if (trip.getDaysRemaining() <= 0) {
                LogController.getLogs().add("Trip to " + trip.getRequest().getDestinationPoint().getName() + " has been successfully completed!" +
                        "<br><span>Driver " + trip.getDriver().getName() + " " + trip.getDriver().getSurname() + " got the payment and now free" +
                        "<br>Car \"" + trip.getCar().getName() + "\" is now free</span>");
                completedTrips.add(tripCompleted(trip));
                try {
                    sleep(500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                continue;
            }
            LogController.getLogs().add("Ongoing trip to " + trip.getRequest().getDestinationPoint().getName() +
                    "<br><span>Days remaining: " + trip.getDaysRemaining() + "</span>");
        }
        return completedTrips;
    }

    @Override
    public boolean sendToTrip(Request request, Driver driver) {
        Car car = carService.findMostEfficientFreeCar(request.getGoodsWeight());
        if(car == null){
            return false;
        }
        request.setIsTaken(true);
        driver.setIsFree(false);
        car.setIsFree(false);

        Trip trip = new Trip();
        trip.setRequest(request);
        trip.setDriver(driver);
        trip.setCar(car);
        trip.setDaysRemaining((int) Math.ceil(request.getDestinationPoint().getDistance() / 450.));
        tripRepository.save(trip);
        requestService.save(request);
        driverService.save(driver);
        carService.save(car);
        return true;
    }
}