package org.example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdjacencyListGraph implements Graph {
    private final Map<Integer, List<Integer>> adjacencyList;

    public AdjacencyListGraph() {
        this.adjacencyList = new HashMap<>();
    }

    public void addVertex(int index) {
        if (!adjacencyList.containsKey(index)) {
            adjacencyList.put(index, new ArrayList<>());
        }
    }

    public void removeVertex(int index) {
        adjacencyList.remove(index);

        for (List<Integer> neighbours : adjacencyList.values()) {
            neighbours.remove(index);
        }
    }

    public void addEdge(int from, int to) {
        if (adjacencyList.containsKey(from) && adjacencyList.containsKey(to)
                && !adjacencyList.get(from).contains(to)) {
            adjacencyList.get(from).add(to);
        }
    }

    public void removeEdge(int from, int to) {
        if (adjacencyList.containsKey(from) && adjacencyList.containsKey(to)
                && adjacencyList.get(from).contains(to)) {
            adjacencyList.get(from).remove(to);
        }
    }

    public List<Integer> getNeighbours(int index) {
        if (!adjacencyList.containsKey(index)) {
            throw new IllegalArgumentException("Вершины с индексом " + index + " нет в графе");
        }

        return new ArrayList<>(adjacencyList.get(index));
    }

    public int getVertexCount() {
        return adjacencyList.size();
    }

    public List<Integer> getVertices() {
        return new ArrayList<>(adjacencyList.keySet());
    }

    public List<Integer> topologicalSort() {
        return new TopologicalSort().topologicalSort(this);
    }

    public void readFromFile(String path) {
        new GraphFileReader().readIntoGraph(path, this);
    }
}
