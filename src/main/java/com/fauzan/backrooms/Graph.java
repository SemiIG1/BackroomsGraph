package com.fauzan.backrooms;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;

public class Graph {
    private final Map<String, LinkedList<String>> graph = new LinkedHashMap<>();
    public void add(String vertex) {
        graph.putIfAbsent(vertex, new LinkedList<>());
    }
    public void addEdge(String vertex1, String vertex2) {
        graph.get(vertex1).add(vertex2);
    }
    public boolean contains(String vertex) {
        return graph.containsKey(vertex);
    }
    public void print() {

    }
}
