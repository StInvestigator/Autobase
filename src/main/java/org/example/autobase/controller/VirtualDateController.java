package org.example.autobase.controller;

import org.example.autobase.entity.CompletedTrip;
import org.example.autobase.service.VirtualDateStaticService;
import org.example.autobase.service.logs.LogsManager;
import org.example.autobase.service.request.RequestService;
import org.example.autobase.service.trip.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/Vdate")
public class VirtualDateController {
    @Autowired
    private RequestService requestService;

    @Autowired
    private TripService tripService;

    @Autowired
    private LogsManager logsManager;

    @PostMapping("/nextDay")
    public ModelAndView nextDay() {
        LogController.getLogs().clear();
        VirtualDateStaticService.setNextVirtualDay();
        requestService.getNextDayRandomRequests();
        List<CompletedTrip> completedTrips = tripService.nextDay();
        if (!completedTrips.isEmpty()) {
            logsManager.createLogsFile(completedTrips);
        }
        return new ModelAndView("redirect:/logs");
    }
}
