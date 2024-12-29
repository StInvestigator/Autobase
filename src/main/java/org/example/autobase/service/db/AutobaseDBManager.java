package org.example.autobase.service.db;

import org.example.autobase.entity.*;
import org.example.autobase.service.car.CarService;
import org.example.autobase.service.completedTrip.CompletedTripService;
import org.example.autobase.service.destinationPoint.DestinationPointService;
import org.example.autobase.service.driver.DriverService;
import org.example.autobase.service.goodsType.GoodsTypeService;
import org.example.autobase.service.request.RequestService;
import org.example.autobase.service.trip.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Service
public class AutobaseDBManager {
    @Value("${data.names}")
    private String namesPath;

    @Value("${data.surnames}")
    private String surnamesPath;

    @Value("${data.carNames}")
    private String carNamesPath;

    @Value("${data.goodsTypes}")
    private String goodsTypesPath;

    @Value("${data.destinations}")
    private String destinationsPath;

    @Autowired
    private CarService carService;

    @Autowired
    private CompletedTripService completedTripService;

    @Autowired
    private TripService tripService;

    @Autowired
    private RequestService requestService;

    @Autowired
    private GoodsTypeService goodsTypeService;

    @Autowired
    private DriverService driverService;

    @Autowired
    private DestinationPointService destinationPointService;

    private final Random RANDOM_GENERATOR = new Random();

    public void fillAllRowsInDB() {
        createRandomCars();
        createRandomDestinations();
        createRandomDrivers();
        createGoodTypes();
        createRandomCompletedTrips();
    }

    public void deleteAllRowsInDB() {
        completedTripService.deleteAll();
        tripService.deleteAll();
        requestService.deleteAll();
        goodsTypeService.deleteAll();
        driverService.deleteAll();
        carService.deleteAll();
        destinationPointService.deleteAll();
    }

    public void createRandomCars() {
        List<String> randomNames;
        try {
            randomNames = Files.readAllLines(Paths.get(carNamesPath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        List<Car> cars = new ArrayList<>();

        for (int i = 0; i < 20; i++) {
            Car car = new Car();
            car.setName(randomNames.get(RANDOM_GENERATOR.nextInt(randomNames.size())));
            car.setMaxWeight(BigDecimal.valueOf(RANDOM_GENERATOR.nextInt(1000, 4000) / 100.));
            cars.add(car);
        }
        carService.saveAll(cars);
    }

    public void createRandomDestinations() {
        List<String> randomNames;
        try {
            randomNames = Files.readAllLines(Paths.get(destinationsPath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        List<DestinationPoint> destinationPoints = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            DestinationPoint destinationPoint = new DestinationPoint();
            destinationPoint.setName(randomNames.get(i));
            destinationPoint.setDistance(RANDOM_GENERATOR.nextInt(300, 2000));
            destinationPoints.add(destinationPoint);
        }
        destinationPointService.saveAll(destinationPoints);
    }

    public void createRandomDrivers() {
        List<String> randomNames;
        List<String> randomSurnames;
        try {
            randomNames = Files.readAllLines(Paths.get(namesPath));
            randomSurnames = Files.readAllLines(Paths.get(surnamesPath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        List<Driver> drivers = new ArrayList<>();

        for (int i = 0; i < 20; i++) {
            Driver driver = new Driver();
            driver.setName(randomNames.get(RANDOM_GENERATOR.nextInt(randomNames.size())));
            driver.setSurname(randomSurnames.get(RANDOM_GENERATOR.nextInt(randomSurnames.size())));
            driver.setExperience(RANDOM_GENERATOR.nextInt(0, 11));
            drivers.add(driver);
        }
        driverService.saveAll(drivers);
    }

    public void createGoodTypes() {
        List<String> randomNames;
        try {
            randomNames = Files.readAllLines(Paths.get(goodsTypesPath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        List<GoodsType> goodTypes = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            GoodsType goodsType = new GoodsType();
            goodsType.setName(randomNames.get(i));
            goodsType.setExperienceNeeded(i < 3 ? 0 : i - RANDOM_GENERATOR.nextInt(3));
            goodTypes.add(goodsType);
        }
        goodsTypeService.saveAll(goodTypes);
    }

    public void createRandomCompletedTrips() {
        List<CompletedTrip> completedTrips = new ArrayList<>();

        for (int i = 0; i < 20; i++) {
            CompletedTrip completedTrip = new CompletedTrip();

            DestinationPoint destinationPoint = destinationPointService.findAll().get(RANDOM_GENERATOR.nextInt(10));
            completedTrip.setDestinationPoint(destinationPoint);

            GoodsType goodsType = goodsTypeService.findAll().get(RANDOM_GENERATOR.nextInt(10));
            completedTrip.setGoodsType(goodsType);

            completedTrip.setPayment(BigDecimal.valueOf(RANDOM_GENERATOR.nextInt(100, 300) / 100.
                    * destinationPoint.getDistance() / 100 * (goodsType.getExperienceNeeded() + 1) * 50));

            Driver driver = driverService.findAll().get(RANDOM_GENERATOR.nextInt(20));
            driverService.endWork(driver, completedTrip.getPayment());
            completedTrip.setDriver(driver);

            BigDecimal weight = BigDecimal.valueOf(RANDOM_GENERATOR.nextInt(1000, 4000) / 100.);
            completedTrip.setGoodsWeight(weight);

            Car car = carService.findMostEfficientFreeCar(weight);
            if (car == null) continue;
            completedTrip.setCar(carService.findMostEfficientFreeCar(weight));

            LocalDate now = LocalDate.now();
            Date date = Date.from(now.plusDays(RANDOM_GENERATOR.nextInt(-5, 0)).atStartOfDay(ZoneId.systemDefault()).toInstant());
            completedTrip.setCompletionDate(date);

            completedTrips.add(completedTrip);
        }
        completedTripService.saveAll(completedTrips);
    }
}
