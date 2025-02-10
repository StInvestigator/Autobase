package org.example.autobase.controller;

import org.example.autobase.entity.Driver;
import org.example.autobase.entity.Request;
import org.example.autobase.service.car.CarService;
import org.example.autobase.service.driver.DriverService;
import org.example.autobase.service.request.RequestService;
import org.example.autobase.service.trip.TripService;
import org.example.autobase.service.util.RequestDoabilityChecker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/requests")
public class RequestController {
    @Autowired
    private RequestService requestService;

    @Autowired
    private DriverService driverService;

    @Autowired
    private RequestDoabilityChecker requestDoabilityChecker;

    @Autowired
    private TripService tripService;

    @GetMapping
    public String requests(Model model) {
        model.addAttribute("requests", requestService.findAllNotTaken());
        model.addAttribute("drivers", driverService.findAllFreeDrivers());
        model.addAttribute("predicates", requestDoabilityChecker);

        return "requestsManagement";
    }

    @PostMapping("/send")
    public ModelAndView sendRequest(@RequestParam Long requestId, @RequestParam Long driverId) {
        tripService.sendToTrip(requestService.findById(requestId),driverService.findById(driverId));
        return new ModelAndView("redirect:/requests");
    }
}
