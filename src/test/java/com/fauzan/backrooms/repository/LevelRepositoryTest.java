package com.fauzan.backrooms.repository;

import com.fauzan.backrooms.entity.Level;
import com.fauzan.backrooms.enums.Difficulty;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jpa.test.autoconfigure.TestEntityManager;

import java.util.Optional;

@DataJpaTest
public class LevelRepositoryTest {
    private Level testLevel;

    private final LevelRepository levelRepository;

    private final TestEntityManager testEntityManager;

    @Autowired
    public LevelRepositoryTest(LevelRepository levelRepository, TestEntityManager testEntityManager) {
        this.levelRepository = levelRepository;
        this.testEntityManager = testEntityManager;
    }

    @BeforeEach
    public void setUp() {
        testLevel = new Level();
        testLevel.setName("Level 2");
        testLevel.setUrl("https://backrooms-wiki.wikidot.com/level-2");
    }
    @Test
    public void givenLevel_whenMultipleExitsAdded_ThenLevelCanQueryExits() {
        Level exitToLevel1 = new Level();
        exitToLevel1.setName("Level 1");
        exitToLevel1.setUrl("https://backrooms-wiki.wikidot.com/level-1");
        Level exitToLevel3 = new Level();
        exitToLevel3.setName("Level 3");
        exitToLevel3.setUrl("https://backrooms-wiki.wikidot.com/level-3");
        Level exitToLevel4 = new Level();
        exitToLevel4.setName("Level 4");
        exitToLevel4.setUrl("https://backrooms-wiki.wikidot.com/level-4");

        testLevel.addExit(exitToLevel1);
        testLevel.addExit(exitToLevel3);
        testLevel.addExit(exitToLevel4);
        testEntityManager.persist(testLevel);
        Level currentLevel = levelRepository.findByName("Level 2");
        assertEquals(3, currentLevel.getExits().size());
    }
    @Test
    public void givenLevel_whenExitAddedAndUpdated_ThenExitIsQueriedProperly() {
        Level exitToLevel3 = new Level();
        exitToLevel3.setUrl("https://backrooms-wiki.wikidot.com/level-3");
        testLevel.addExit(exitToLevel3);
        testEntityManager.persist(testLevel);

        Optional<Level> storedLevel = levelRepository.findByUrl("https://backrooms-wiki.wikidot.com/level-3");
        assertTrue(storedLevel.isPresent());
        Level level3 = storedLevel.get();
        level3.setDifficulty(Difficulty.getValue("4").ordinal());
        level3.setName("Level 3");
        Long id = (Long) testEntityManager.persistAndGetId(level3);
        assertNotNull(id);
        assertTrue(levelRepository.findById(id).isPresent());
        assertFalse(levelRepository.findByName("Level 2").getExits().isEmpty());
    }

    @Test
    public void givenUpsert_whenThereIsNoLevel_ThenInsertTheLevel() {
        Optional<Level> storedLevel = levelRepository.findByUrl("https://backrooms-wiki.wikidot.com/level-3");
        assertTrue(storedLevel.isEmpty());
        Level level3 = new Level();
        level3.setUrl("https://backrooms-wiki.wikidot.com/level-3");
        level3.setDifficulty(Difficulty.getValue("4").ordinal());
        level3.setName("Level 3");
        Long id = (Long) testEntityManager.persistAndGetId(level3);
        assertNotNull(id);
        assertTrue(levelRepository.findById(id).isPresent());
    }

}