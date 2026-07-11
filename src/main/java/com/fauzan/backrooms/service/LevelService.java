package com.fauzan.backrooms.service;

import com.fauzan.backrooms.dto.GraphResponse;
import com.fauzan.backrooms.entity.Level;


public interface LevelService {
    GraphResponse getAllNodesAndEdges();
    Level upsert(Level currentLevel);
    void linkToExit(String sourceUrl, String nextUrl);

}
