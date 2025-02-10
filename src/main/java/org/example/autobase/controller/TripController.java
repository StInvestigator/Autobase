package org.example.autobase.controller;

import org.example.autobase.service.trip.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/trips")
public class TripController {
    @Autowired
    private TripService tripService;

    @GetMapping
    public String getTrips(Model model) {
        model.addAttribute("trips", tripService.findAll());

        return "ongoingTrips";
    }
}
