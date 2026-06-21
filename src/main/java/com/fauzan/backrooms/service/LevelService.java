package com.fauzan.backrooms.service;

import com.fauzan.backrooms.entity.Level;

public interface LevelService {
    Level upsert(Level currentLevel);
    void linkToExit(String sourceUrl, String nextUrl);
}
