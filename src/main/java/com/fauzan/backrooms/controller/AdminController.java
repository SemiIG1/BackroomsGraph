package com.fauzan.backrooms.controller;

import com.fauzan.backrooms.service.ScraperWorkerService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@Profile("!prod")
public class AdminController {
    private final ScraperWorkerService scraperWorkerService;

    public AdminController(ScraperWorkerService scraperWorkerService) {
        this.scraperWorkerService = scraperWorkerService;
    }

    @GetMapping("/admin/map")
    public String showMap(Model model) {
        model.addAttribute("isScraperRunning", scraperWorkerService.isRunning());
        return "admin-map";
    }
    @PostMapping("/admin/refresh-data")
    public String refreshData(Model model) {
        scraperWorkerService.runScraperInBackground();
        return "redirect:/admin/map";
    }
}
