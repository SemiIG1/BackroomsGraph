package com.fauzan.backrooms.restcontroller;

import com.fauzan.backrooms.dto.GraphResponse;
import com.fauzan.backrooms.service.LevelService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LevelRestController {
    private final LevelService levelService;

    public LevelRestController(LevelService levelService) {
        this.levelService = levelService;
    }
    @GetMapping("/api/graph")
    public GraphResponse getAllNodesAndEdges() {
        return levelService.getAllNodesAndEdges();
    }
}
