package com.fauzan.backrooms;

import java.util.*;

public class Paths {
    private final Deque<String> queue = new ArrayDeque<>();
    private final Map<String, String> edgeTo = new HashMap<>();
    private final Map<String, Boolean> marked = new HashMap<>();
    private String source;

    public void breadthFirstSearch(Graph graph, String source) {
        this.source = source;
        marked.put(source, true);
        queue.offer(source);
        while (!queue.isEmpty()) {
            String currentLevel = queue.poll();
            for (String adjacentLevel : graph.getAdjacent(currentLevel)) {
                if (!marked.containsKey(adjacentLevel)) {
                    edgeTo.put(adjacentLevel, currentLevel);
                    marked.put(adjacentLevel, true);
                    queue.offer(adjacentLevel);
                }
            }
        }
    }

    private boolean hasPathTo(String vertex) {
        return marked.containsKey(vertex);
    }

    public Deque<String> pathTo(String vertex) {
        if (!hasPathTo(vertex)) {
            return null;
        }
        Deque<String> arrayDeque = new ArrayDeque<>();
        for (String destination = vertex; !Objects.equals(destination, source); destination = edgeTo.get(destination)) {
            arrayDeque.addFirst(destination);
        }
        arrayDeque.push(source);
        return arrayDeque;
    }
}
