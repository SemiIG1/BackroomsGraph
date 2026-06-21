package com.fauzan.backrooms.service;

public interface ScraperWorkerService {
    boolean isRunning();
    void runScraperInBackground();
}
