package com.fauzan.backrooms.service;

import com.fauzan.backrooms.Graph;
import com.fauzan.backrooms.Paths;
import com.fauzan.backrooms.dto.EdgeResponse;
import com.fauzan.backrooms.dto.GraphResponse;
import com.fauzan.backrooms.dto.NodeResponse;
import com.fauzan.backrooms.entity.Level;
import com.fauzan.backrooms.enums.Difficulty;
import com.fauzan.backrooms.repository.LevelRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.Optional;

@Service
public class LevelServiceImpl implements LevelService {
    private final LevelRepository levelRepository;
    public LevelServiceImpl(LevelRepository levelRepository) {
        this.levelRepository = levelRepository;
    }

    @Override
    public GraphResponse getAllNodesAndEdges() {
        List<Level> levels = levelRepository.findAll();
        List<NodeResponse> nodeResponses = new ArrayList<>();
        List<EdgeResponse> edgeResponses = new ArrayList<>();
        levels.forEach(level -> {
            String color;
            if (level.getDifficulty() != null) {
                switch (level.getDifficulty()) {
                    case 0 -> color = Difficulty.ZERO.getHexColor();
                    case 1 -> color = Difficulty.ONE.getHexColor();
                    case 2 -> color = Difficulty.TWO.getHexColor();
                    case 3 -> color = Difficulty.THREE.getHexColor();
                    case 4 -> color = Difficulty.FOUR.getHexColor();
                    case 5 -> color = Difficulty.FIVE.getHexColor();
                    default -> color = Difficulty.UNKNOWN.getHexColor();
                }
            } else {
                color = Difficulty.UNKNOWN.getHexColor();
            }
            nodeResponses.add(new NodeResponse(level.getUrl(), level.getName(), color));
            level.getExits().forEach(exit -> {
                edgeResponses.add(new EdgeResponse(level.getUrl(), exit.getUrl()));
            });
        });
        return new GraphResponse(nodeResponses, edgeResponses);
    }

    @Override
    public List<String> getClosestRoute(String start, String end) {
        List<Level> levels = levelRepository.findAll();
        Graph graph = new Graph();
        levels.forEach(level -> {
            graph.add(level.getUrl());
            level.getExits().forEach(exit -> graph.addEdge(level.getUrl(), exit.getUrl()));
        });
        Paths paths = new Paths();
        paths.breadthFirstSearch(graph, start);
        Deque<String> path = paths.pathTo(end);
        return path.stream().toList();
    }

    @Transactional
    @Override
    public void linkToExit(String sourceUrl, String nextUrl) {
        Level source = levelRepository.findByUrl(sourceUrl).orElseGet(() -> {
            Level stub = new Level();
            stub.setUrl(sourceUrl);
            return levelRepository.save(stub);
        });
        Level targetExit = levelRepository.findByUrl(nextUrl).orElseGet(() -> {
            Level stub = new Level();
            stub.setUrl(nextUrl);
            return levelRepository.save(stub);
        });

        source.addExit(targetExit);
        levelRepository.save(source);
    }



    @Override
    public Level upsert(Level currentLevel) {
        Optional<Level> storedLevel = levelRepository.findByUrl(currentLevel.getUrl());
        if (storedLevel.isPresent()) {
            Level level = storedLevel.get();
            level.setDifficulty(currentLevel.getDifficulty());
            level.setName(currentLevel.getName());
            level.setUrl(currentLevel.getUrl());
            return levelRepository.save(level);
        } else {
            return levelRepository.save(currentLevel);
        }
    }


}
