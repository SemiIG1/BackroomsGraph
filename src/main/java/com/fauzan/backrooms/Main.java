package com.fauzan.backrooms;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        WebDriver driver = new ChromeDriver();
        Map<String, Boolean> marked = new HashMap<>();
        Graph graph = new Graph();
        Deque<String> queue = new ArrayDeque<>();
        String starterLink = "https://backrooms-wiki.wikidot.com/level-0";
        queue.offer(starterLink);
        marked.put(starterLink, true);
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));
        while(!queue.isEmpty()) {
            driver.get(queue.poll());

            List<WebElement> exitLinks = driver.findElements(By.xpath(
                    "//div[@id='page-content']//*[starts-with(text(),'Exit')]/ancestor-or-self::*[starts-with(name(), 'h')]/following-sibling::*[1]//a"));
            String currentLink = driver.getCurrentUrl();
            System.out.println(currentLink);
            exitLinks.forEach((element) -> {
                String link = element.getAttribute("href");
                if (link != null && !link.startsWith("javascript")) {
                    graph.add(currentLink);
                    graph.addEdge(currentLink, link);
                    if (!marked.containsKey(link)) {
                        queue.offer(link);
                        marked.put(link, true);
                    }
                }
            });
        }
        driver.quit();
    }
}