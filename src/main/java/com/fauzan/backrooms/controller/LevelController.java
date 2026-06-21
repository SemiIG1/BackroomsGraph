package com.fauzan.backrooms.controller;

import com.fauzan.backrooms.service.ScraperWorkerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LevelController {
    private final ScraperWorkerService scraperWorkerService;

    public LevelController(ScraperWorkerService scraperWorkerService) {
        this.scraperWorkerService = scraperWorkerService;
    }
    @GetMapping("/admin/map")
    public String showMap(Model model) {
        model.addAttribute("isScraperRunning", scraperWorkerService.isRunning());
        return "map";
    }
    @PostMapping("/admin/refresh-data")
    public String refreshData(Model model) {
        scraperWorkerService.runScraperInBackground();
        return "redirect:/admin/map";
    }
}
