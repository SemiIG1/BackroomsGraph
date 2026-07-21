package com.fauzan.backrooms.restcontroller;

import com.fauzan.backrooms.dto.GraphResponse;
import com.fauzan.backrooms.service.LevelService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class LevelRestController {
    private final LevelService levelService;

    public LevelRestController(LevelService levelService) {
        this.levelService = levelService;
    }
    @GetMapping("/api/graph")
    public GraphResponse getAllNodesAndEdges() {
        return levelService.getAllNodesAndEdges();
    }

    @GetMapping("/api/route")
    public List<String> getShortestRoute(
            @RequestParam String start,
            @RequestParam String end) {
        return levelService.getClosestRoute(start, end);
    }
}
