package org.example.autobase.menu;

import org.example.autobase.entity.CompletedTrip;
import org.example.autobase.entity.DestinationPoint;
import org.example.autobase.entity.Driver;
import org.example.autobase.entity.Request;
import org.example.autobase.service.VirtualDateStaticService;
import org.example.autobase.service.car.CarService;
import org.example.autobase.service.completedTrip.CompletedTripService;
import org.example.autobase.service.destinationPoint.DestinationPointService;
import org.example.autobase.service.driver.DriverService;
import org.example.autobase.service.goodsType.GoodsTypeService;
import org.example.autobase.service.logs.LogsManager;
import org.example.autobase.service.request.RequestService;
import org.example.autobase.service.trip.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

import static java.lang.Thread.sleep;
import static org.example.autobase.menu.MenuPublisher.showMenu;
import static org.example.autobase.menu.MenuPublisher.showStatsMenu;

@Service
public class MenuExecutor {
    @Autowired
    private CarService carService;

    @Autowired
    private CompletedTripService completedTripService;

    @Autowired
    private DestinationPointService destinationPointService;

    @Autowired
    private DriverService driverService;

    @Autowired
    private GoodsTypeService goodsTypeService;

    @Autowired
    private RequestService requestService;

    @Autowired
    private TripService tripService;

    @Autowired
    private LogsManager logsManager;

    public void startMenu() {
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            showMenu();
            choice = scanner.nextInt();

            if (choice == 1) {
                manageRequests();
            }
            if (choice == 2) {
                openStatsMenu();
                continue;
            }
            if (choice == 3) {
                startNextDay();
            }
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        } while (choice != -1);
    }

    private void manageRequests() {
        Scanner scanner = new Scanner(System.in);
        List<Request> requests = requestService.findAllNotTaken();
        if (requests.isEmpty()) {
            System.out.println("All requests are already managed!");
            return;
        }
        int count = 1;
        for (Request request : requests) {
            System.out.println(count++ + ". " + request);
        }
        System.out.print("Select a request number: ");
        Request request = requests.get(scanner.nextInt() - 1);

        List<Driver> drivers = driverService.getFreeDriversWithExperienceGreaterThan(request.getGoodsType().getExperienceNeeded());
        if (drivers.isEmpty()) {
            System.out.println("Error: We dont have a free driver with enough experience for this trip.");
            return;
        }
        count = 1;
        for (Driver driver : drivers) {
            System.out.println(count++ + ". " + driver);
        }
        System.out.print("Select a driver number: ");
        Driver driver = drivers.get(scanner.nextInt() - 1);

        if (tripService.sendToTrip(request, driver)) {
            System.out.println("Trip was successfully started!");
        } else {
            System.out.println("Error: We dont have a car needed for this trip.");
        }
    }

    private void startNextDay() {
        VirtualDateStaticService.setNextVirtualDay();
        System.out.println("Today is " + VirtualDateStaticService.getCurrentVirtualLocalDate());
        System.out.println("You have received new requests:");
        requestService.getNextDayRandomRequests().forEach(System.out::println);
        List<CompletedTrip> completedTrips = tripService.nextDay();
        if (!completedTrips.isEmpty()) {

            logsManager.createLogsFile(completedTrips);
            System.out.println(completedTrips.size() + " trip(s) was completed today. Today`s logs was created");
        }
    }

    private void openStatsMenu() {
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            showStatsMenu();
            choice = scanner.nextInt();

            if (choice == 1) {
                showTransportedAmountForEachDriver();
            }
            if (choice == 2) {
                showTransportedAmountForDestination();
            }
            if (choice == 3) {
                showDriversWithMaxBalance();
            }
            if (choice == -1) {
                continue;
            }
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        } while (choice != -1);
    }

    private void showTransportedAmountForEachDriver() {
        for (Driver driver : driverService.findAll()) {
            BigDecimal transported = completedTripService.getTotalWeightTransportedByDriver(driver);
            System.out.println(driver);
            System.out.println("Transported weight: " + (transported == null ? 0 : transported) + " ton(s)\n");
        }
    }

    private void showTransportedAmountForDestination() {
        Scanner scanner = new Scanner(System.in);
        int count = 1;
        List<DestinationPoint> destinationPoints = destinationPointService.findAll();
        for (DestinationPoint destinationPoint : destinationPoints) {
            System.out.println(count++ + ". " + destinationPoint);
        }
        System.out.print("Select a destination point number: ");
        DestinationPoint destinationPoint = destinationPoints.get(scanner.nextInt() - 1);
        BigDecimal weight = completedTripService.getTotalWeightTransportedToDestination(destinationPoint);
        System.out.println("Amount of weight transported to that destination point: " + (weight == null ? 0 : weight) + " ton(s)");
    }

    private void showDriversWithMaxBalance() {
        for (Driver driver : driverService.findDriversWithMaxBalance()) {
            System.out.println(driver);
        }
    }
}
