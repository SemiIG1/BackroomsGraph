package com.fauzan.backrooms.service;

import com.fauzan.backrooms.Graph;
import com.fauzan.backrooms.UrlNormalizer;
import com.fauzan.backrooms.entity.Level;
import com.fauzan.backrooms.enums.Difficulty;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
public class ScraperWorkerServiceImpl implements ScraperWorkerService {
    AtomicBoolean isRunning = new AtomicBoolean(false);
    private final LevelService levelService;

    public ScraperWorkerServiceImpl(LevelService levelService) {
        this.levelService = levelService;
    }

    @Override
    public boolean isRunning() {
        return isRunning.get();
    }

    @Async
    @Override
    public void runScraperInBackground() {
        if (!isRunning.compareAndSet(false, true)) {
            System.out.println("Scraper is already running!");
            return;
        }
        try {
            WebDriver driver = new ChromeDriver();
            Map<String, Boolean> marked = new HashMap<>();
            Graph graph = new Graph();
            Deque<String> queue = new ArrayDeque<>();
            String starterLink = "https://backrooms-wiki.wikidot.com/level-0/";
            queue.offer(starterLink);
            marked.put(starterLink, true);
            while (!queue.isEmpty()) {
                driver.get(queue.poll());

                List<WebElement> pageTitleElements = driver.findElements(By.xpath("//*[@id=\"page-title\"]"));
                if (pageTitleElements.isEmpty()) {
                    System.out.println(driver.getCurrentUrl());
                    System.out.println("The level doesn't exist");
                    continue;
                }

                Level currentLevel = new Level();
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofMinutes(1));
                wait.until(ExpectedConditions.attributeToBeNotEmpty(pageTitleElements.get(0), "textContent"));
                System.out.println(pageTitleElements.get(0).getAttribute("textContent").trim());
                currentLevel.setName(pageTitleElements.get(0).getAttribute("textContent").trim());

                List<WebElement> exitLinks = driver.findElements(By.xpath(
                        "//div[@id='page-content']//*[starts-with(text(),'Exit')]/ancestor-or-self::*[starts-with(name(), 'h')]/following-sibling::*[1]//a"));
                List<WebElement> difficultyElements = driver.findElements(By.xpath("//div[@class='sd-container']//div[@class='gradient-box']/div[2]"));
                if (!difficultyElements.isEmpty()) {
                    wait.until(ExpectedConditions.attributeToBeNotEmpty(difficultyElements.get(0), "textContent"));
                    System.out.println(difficultyElements.get(0).getAttribute("textContent").trim());
                    System.out.print("Difficulty in int: ");
                    System.out.println(Difficulty.getValue(difficultyElements.get(0).getAttribute("textContent").trim()));
                    currentLevel.setDifficulty(Difficulty.getValue(difficultyElements.get(0).getAttribute("textContent").trim()).ordinal());
                } else {
                    System.out.println(Difficulty.UNKNOWN);
                }

                String currentLink = UrlNormalizer.normalize(driver.getCurrentUrl());
                System.out.println(currentLink);
                currentLevel.setUrl(currentLink);
                levelService.upsert(currentLevel);
                for (Iterator<WebElement> it = exitLinks.iterator(); it.hasNext();) {
                    WebElement element = it.next();
                    String nextLink = UrlNormalizer.normalize(element.getAttribute("href"));
                    if (nextLink != null && !nextLink.startsWith("javascript") && !nextLink.contains("object") && !nextLink.contains("entity") && !nextLink.contains("entities")) {
                        graph.add(currentLink);
                        graph.addEdge(currentLink, nextLink);
                        levelService.linkToExit(currentLink, nextLink);
                        if (!marked.containsKey(nextLink)) {
                            queue.offer(nextLink);
                            marked.put(nextLink, true);
                        }
                    }
                }

            }
            driver.quit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            isRunning.set(false);
            System.out.println("Done");
        }
    }
}
