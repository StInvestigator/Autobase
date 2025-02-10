package org.example.autobase.controller;

import org.example.autobase.dto.DestinationTotalWeightDTO;
import org.example.autobase.dto.DriverStatsDTO;
import org.example.autobase.entity.DestinationPoint;
import org.example.autobase.entity.Driver;
import org.example.autobase.service.completedTrip.CompletedTripService;
import org.example.autobase.service.destinationPoint.DestinationPointService;
import org.example.autobase.service.driver.DriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/stats")
public class StatsController {
    @Autowired
    private CompletedTripService completedTripService;

    @Autowired
    private DestinationPointService destinationPointService;

    @Autowired
    private DriverService driverService;

    @GetMapping("/destinations")
    public String destinationsStats(Model model) {
        List<DestinationTotalWeightDTO> dtos = new ArrayList<>();
        List<DestinationPoint> destinationPoints = destinationPointService.findAll();
        destinationPoints.forEach(destinationPoint -> {
            BigDecimal weight = completedTripService.getTotalWeightTransportedToDestination(destinationPoint);
            dtos.add(new DestinationTotalWeightDTO(destinationPoint.getName(),
                    weight == null ? BigDecimal.valueOf(0) : weight));
        });
        model.addAttribute("destinations", dtos);
        return "destinationsStats";
    }

    @GetMapping("/drivers")
    public String driversStats(Model model) {
        List<DriverStatsDTO> dtos = new ArrayList<>();
        BigDecimal maxBalance = driverService.getMaxBalance();
        for (Driver driver : driverService.findAll()) {
            BigDecimal transported = completedTripService.getTotalWeightTransportedByDriver(driver);
            dtos.add(new DriverStatsDTO(driver.getName() + " " + driver.getSurname(),
                    transported == null ? BigDecimal.valueOf(0) : transported,
                    driver.getBalance(),
                    Objects.equals(driver.getBalance(), maxBalance)));
        }
        model.addAttribute("drivers", dtos);
        return "driversStats";
    }
}
