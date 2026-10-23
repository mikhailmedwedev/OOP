package org.example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class AdjacencyListGraph implements Graph {
    private final Map<Integer, List<Integer>> adjacencyList;

    public AdjacencyListGraph() {
        this.adjacencyList = new HashMap<>();
    }

    @Override
    public void addVertex(int index) {
        if (!adjacencyList.containsKey(index)) {
            adjacencyList.put(index, new ArrayList<>());
        }
    }

    @Override
    public void removeVertex(int index) {
        adjacencyList.remove(index);

        for (List<Integer> neighbours : adjacencyList.values()) {
            neighbours.remove(index);
        }
    }

    @Override
    public void addEdge(int from, int to) {
        if (adjacencyList.containsKey(from) && adjacencyList.containsKey(to)
                && !adjacencyList.get(from).contains(to)) {
            adjacencyList.get(from).add(to);
        }
    }

    @Override
    public void removeEdge(int from, int to) {
        if (adjacencyList.containsKey(from) && adjacencyList.containsKey(to)
                && adjacencyList.get(from).contains(to)) {
            adjacencyList.get(from).remove(to);
        }
    }

    @Override
    public List<Integer> getNeighbours(int index) {
        if (!adjacencyList.containsKey(index)) {
            throw new IllegalArgumentException("Вершины с индексом " + index + " нет в графе");
        }

        return new ArrayList<>(adjacencyList.get(index));
    }

    @Override
    public int getVertexCount() {
        return adjacencyList.size();
    }

    @Override
    public List<Integer> getVertices() {
        return new ArrayList<>(adjacencyList.keySet());
    }

    @Override
    public List<int[]> getEdges() {
        List<int[]> result = new ArrayList<>();

        for (Map.Entry<Integer, List<Integer>> entry : adjacencyList.entrySet()) {
            int from = entry.getKey();

            for (int to : entry.getValue()) {
                result.add(new int[]{from, to});
            }
        }

        return result;
    }

    @Override
    public List<Integer> topologicalSort() {
        return new TopologicalSort().topologicalSort(this);
    }

    @Override
    public void readFromFile(String path) {
        new GraphFileReader().readIntoGraph(path, this);
    }

    @Override
    public String toString() {
        return "Vertices: " + getVertices() + "\nEdges: " + getEdges().stream()
                .map(edge -> "(" + edge[0] + ", " + edge[1] + ")")
                .toList();
    }

    private static Map<List<Integer>, Integer> edgeCounts(List<int[]> edges) {
        Map<List<Integer>, Integer> counts = new HashMap<>();

        for (int[] edge : edges) {
            List<Integer> key = List.of(edge[0], edge[1]);
            counts.put(key, counts.getOrDefault(key, 0) + 1);
        }

        return counts;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof Graph other)) {
            return false;
        }

        return new HashSet<>(getVertices()).equals(new HashSet<>(other.getVertices()))
                && edgeCounts(getEdges()).equals(edgeCounts(other.getEdges()));
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                new HashSet<>(getVertices()),
                edgeCounts(getEdges())
        );
    }
}
