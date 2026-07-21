package com.fauzan.backrooms;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PathsTest {
    private Graph graph;
    private Paths paths;
    @BeforeEach
    void initialize() {
        graph = new Graph();
        paths = new Paths();
    }
    @Test
    void givenDirectedGraph_whenAPathIsAvailable_thenReturnThatPath() {
        graph.add("Level 0");
        graph.add("Level 1");
        graph.add("Level 2");
        graph.add("Level 3");
        graph.addEdge("Level 0", "Level 1");
        graph.addEdge("Level 1", "Level 2");
        graph.addEdge("Level 2", "Level 3");
        graph.addEdge("Level 0", "Level 2");
        graph.addEdge("Level 1", "Level 3");
        paths.breadthFirstSearch(graph, "Level 0");
        assertEquals(3, paths.pathTo("Level 3").size());
    }
    @Test
    void givenDirectedGraph_whenPathIsNotAvailable_thenReturnNull() {
        graph.add("Level 0");
        graph.add("Level 1");
        graph.add("Level 2");
        graph.add("Level 3");
        graph.addEdge("Level 1", "Level 2");
        graph.addEdge("Level 2", "Level 3");
        graph.addEdge("Level 0", "Level 2");
        graph.addEdge("Level 1", "Level 3");
        paths.breadthFirstSearch(graph, "Level 0");
        assertNull(paths.pathTo("Level 1"));
    }

}