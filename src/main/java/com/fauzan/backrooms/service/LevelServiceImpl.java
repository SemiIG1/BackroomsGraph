package com.fauzan.backrooms.service;

import com.fauzan.backrooms.entity.Level;
import com.fauzan.backrooms.repository.LevelRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LevelServiceImpl implements LevelService {
    private LevelRepository levelRepository;
    public LevelServiceImpl(LevelRepository levelRepository) {
        this.levelRepository = levelRepository;
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
