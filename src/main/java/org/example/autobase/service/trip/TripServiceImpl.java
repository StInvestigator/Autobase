package org.example.autobase.service.trip;

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
            System.out.println("\nCar broke down!" +
                    "\nTrip to " + failedTrip.getRequest().getDestinationPoint().getName() + " has been failed..." +
                    "\nDriver " + failedTrip.getDriver().getName() + " " + failedTrip.getDriver().getSurname() + " is now free" +
                    "\nCar \"" + failedTrip.getCar().getName() + "\" is repaired and free");
            tripFailed(failedTrip);
            try {
                sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        for (Trip trip : tripRepository.findAll()) {
            trip.setDaysRemaining(trip.getDaysRemaining() - 1);
            if (trip.getDaysRemaining() <= 0) {
                System.out.println("\nTrip to " + trip.getRequest().getDestinationPoint().getName() + " has been successfully completed!" +
                        "\nDriver " + trip.getDriver().getName() + " " + trip.getDriver().getSurname() + " got the payment and now free" +
                        "\nCar \"" + trip.getCar().getName() + "\" is now free");
                completedTrips.add(tripCompleted(trip));
                try {
                    sleep(500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                continue;
            }
            System.out.println("\nOngoing trip to " + trip.getRequest().getDestinationPoint().getName() +
                    ":\nDelivering type: " + trip.getRequest().getGoodsType().getName() +
                    "\nDays remaining: " + trip.getDaysRemaining() +
                    "\nDriver: " + trip.getDriver().getName() + " " + trip.getDriver().getSurname() +
                    "\nCar: \"" + trip.getCar().getName() + "\"");
            try {
                sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
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