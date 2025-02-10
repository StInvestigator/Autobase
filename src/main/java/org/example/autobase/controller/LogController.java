package org.example.autobase.controller;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/logs")
public class LogController {
    @Getter
    private static List<String> logs = new ArrayList<>();

    @GetMapping
    public String showLogs(Model model) {
        model.addAttribute("logs", logs);
        return "todayLogs";
    }
}
