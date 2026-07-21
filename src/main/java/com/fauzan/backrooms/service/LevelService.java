package com.fauzan.backrooms.service;

import com.fauzan.backrooms.dto.GraphResponse;
import com.fauzan.backrooms.entity.Level;

import java.util.List;


public interface LevelService {
    GraphResponse getAllNodesAndEdges();
    List<String> getClosestRoute(String start, String end);
    Level upsert(Level currentLevel);
    void linkToExit(String sourceUrl, String nextUrl);

}
