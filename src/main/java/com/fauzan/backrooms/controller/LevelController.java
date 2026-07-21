package com.fauzan.backrooms.controller;

import com.fauzan.backrooms.service.ScraperWorkerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LevelController {

    @GetMapping("/map")
    public String showMapForUser(Model model) {
        return "index";
    }

}
