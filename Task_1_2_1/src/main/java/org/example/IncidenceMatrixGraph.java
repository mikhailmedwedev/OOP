package org.example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class IncidenceMatrixGraph implements Graph {
    private final Map<Integer, Integer> vertexToIndexInMatrix = new HashMap<>();

    private int[][] incidenceMatrix = new int[0][0];

    private List<int[]> edges = new ArrayList<>();

    @Override
    public void addVertex(int index) {
        if (vertexToIndexInMatrix.containsKey(index)) {
            return;
        }

        int vertexCount = vertexToIndexInMatrix.size();
        int edgeCount = edges.size();
        int[][] newMatrix = new int[vertexCount + 1][edgeCount];
        for (int i = 0; i < vertexCount; i++) {
            for (int j = 0; j < edgeCount; j++) {
                newMatrix[i][j] = incidenceMatrix[i][j];
            }
        }
        incidenceMatrix = newMatrix;
        vertexToIndexInMatrix.put(index, vertexCount);
    }

    @Override
    public void addEdge(int from, int to) {
        if (!(vertexToIndexInMatrix.containsKey(from) && vertexToIndexInMatrix.containsKey(to))) {
            throw new IllegalArgumentException("Вершины нет в графе");
        }

        for (int[] edge : edges) {
            if (edge[0] == from && edge[1] == to) {
                return;
            }
        }

        edges.add(new int[]{from, to});
        int fromIndex = vertexToIndexInMatrix.get(from);
        int toIndex = vertexToIndexInMatrix.get(to);
        int vertexCount = vertexToIndexInMatrix.size();
        int edgeCount = edges.size();
        int[][] newMatrix = new int[vertexCount][edgeCount];
        for (int i = 0; i < vertexCount; i++) {
            for (int j = 0; j < edgeCount - 1; j++) {
                newMatrix[i][j] = incidenceMatrix[i][j];
            }
        }
        incidenceMatrix = newMatrix;


        int edgeIndex = edgeCount - 1;
        if (fromIndex != toIndex) {
            incidenceMatrix[fromIndex][edgeIndex] = -1;
            incidenceMatrix[toIndex][edgeIndex] = 1;
        } else {
            incidenceMatrix[fromIndex][edgeIndex] = -1;
        }
    }

    @Override
    public void removeEdge(int from, int to) {
        if (!(vertexToIndexInMatrix.containsKey(from) && vertexToIndexInMatrix.containsKey(to))) {
            throw new IllegalArgumentException("Вершины нет в графе");
        }

        int removedEdge = -1;
        for (int i = 0; i < edges.size(); i++) {
            if (edges.get(i)[0] == from && edges.get(i)[1] == to) {
                removedEdge = i;
                edges.remove(i);
                break;
            }
        }

        if (removedEdge != -1) {
            int vertexCount = vertexToIndexInMatrix.size();
            int edgeCount = edges.size();
            int[][] newMatrix = new int[vertexCount][edgeCount];

            for (int i = 0; i < vertexCount; i++) {
                int newJ = 0;
                for (int j = 0; j < incidenceMatrix[i].length; j++) {
                    if (j == removedEdge) {
                        continue;
                    }

                    newMatrix[i][newJ] = incidenceMatrix[i][j];
                    newJ++;
                }
            }

            incidenceMatrix = newMatrix;
        }
    }

    @Override
    public void removeVertex(int index) {
        if (!vertexToIndexInMatrix.containsKey(index)) {
            throw new IllegalArgumentException("Вершины с номером " + index + " нет в графе");
        }

        List<int[]> edgesToRemove = new ArrayList<>();
        for (int i = 0; i < edges.size(); i++) {
            if (edges.get(i)[0] == index || edges.get(i)[1] == index) {
                edgesToRemove.add(edges.get(i));
            }
        }

        for (int i = 0; i < edgesToRemove.size(); i++) {
            removeEdge(edgesToRemove.get(i)[0], edgesToRemove.get(i)[1]);
        }

        int removedVertex = vertexToIndexInMatrix.get(index);
        int vertexCount = vertexToIndexInMatrix.size();
        int edgeCount = edges.size();
        int[][] newMatrix = new int[vertexCount - 1][edgeCount];

        int newI = 0;
        for (int i = 0; i < vertexCount; i++) {
            if (i == removedVertex) {
                continue;
            }

            for (int j = 0; j < edgeCount; j++) {
                newMatrix[newI][j] = incidenceMatrix[i][j];
            }

            newI++;
        }

        incidenceMatrix = newMatrix;

        vertexToIndexInMatrix.remove(index);
        for (Map.Entry<Integer, Integer> entry : vertexToIndexInMatrix.entrySet()) {
            if (entry.getValue() > removedVertex) {
                entry.setValue(entry.getValue() - 1);
            }
        }
    }

    @Override
    public List<Integer> getNeighbours(int index) {
        if (!vertexToIndexInMatrix.containsKey(index)) {
            throw new IllegalArgumentException("Вершины с номером " + index + " нет в графе");
        }

        List<Integer> neighbours = new ArrayList<>();

        int indexInMatrix = vertexToIndexInMatrix.get(index);
        for (int j = 0; j < incidenceMatrix[indexInMatrix].length; j++) {
            if (incidenceMatrix[indexInMatrix][j] == -1) {
                boolean isLoop = true;

                for (int i = 0; i < incidenceMatrix.length; i++) {
                    if (incidenceMatrix[i][j] == 1) {
                        isLoop = false;
                        break;
                    }
                }

                if (isLoop) {
                    neighbours.add(index);
                    continue;
                }

                for (int i = 0; i < incidenceMatrix.length; i++) {
                    if (incidenceMatrix[i][j] == 1) {
                        for (Map.Entry<Integer, Integer> entry : vertexToIndexInMatrix.entrySet()) {
                            if (entry.getValue() == i) {
                                neighbours.add(entry.getKey());
                                break;
                            }
                        }
                        break;
                    }
                }
            }
        }

        return neighbours;
    }

    @Override
    public int getVertexCount() {
        return vertexToIndexInMatrix.size();
    }

    @Override
    public List<Integer> getVertices() {
        return new ArrayList<>(vertexToIndexInMatrix.keySet());
    }

    @Override
    public List<int[]> getEdges() {
        List<int[]> result = new ArrayList<>();

        for (int[] edge : edges) {
            result.add(edge.clone());
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
        return "Vertices: " + getVertices() + "\nEdges: " + edges.stream()
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
