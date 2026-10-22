package org.example;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class TopologicalSort {
    public List<Integer> topologicalSort(Graph graph) {
        Map<Integer, Integer> incomingEdgesCount = new HashMap<>();

        for (int vertex : graph.getVertices()) {
            incomingEdgesCount.put(vertex, 0);
        }

        for (int from : graph.getVertices()) {
            for (int to : graph.getNeighbours(from)) {
                incomingEdgesCount.put(to, incomingEdgesCount.get(to) + 1);
            }
        }

        Queue<Integer> queue = new ArrayDeque<>();

        for (int vertex : graph.getVertices()) {
            if (incomingEdgesCount.get(vertex) == 0) {
                queue.add(vertex);
            }
        }

        List<Integer> sortedGraph = new ArrayList<>();

        while (!queue.isEmpty()) {
            int currentVertex = queue.poll();
            sortedGraph.add(currentVertex);

            for (int neighbour : graph.getNeighbours(currentVertex)) {
                incomingEdgesCount.put(neighbour, incomingEdgesCount.get(neighbour) - 1);

                if (incomingEdgesCount.get(neighbour) == 0) {
                    queue.add(neighbour);
                }
            }
        }

        if (sortedGraph.size() != graph.getVertexCount()) {
            throw new IllegalArgumentException("В графе цикл. Топологическая сортировка невозможна");
        }

        return sortedGraph;
    }
}
