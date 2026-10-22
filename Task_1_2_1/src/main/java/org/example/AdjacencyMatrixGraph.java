package org.example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdjacencyMatrixGraph implements Graph {
    private final Map<Integer, Integer> vertexToIndexInMatrix = new HashMap<>();

    private int[][] adjacencyMatrix = new int[0][0];

    @Override
    public void addVertex(int index) {
        if (vertexToIndexInMatrix.containsKey(index)) {
            return;
        }

        int matrixSize = vertexToIndexInMatrix.size();
        int matrixIndex = matrixSize;
        int[][] newMatrix = new int[matrixSize + 1][matrixSize + 1];
        for (int i = 0; i < matrixSize; i++) {
            for (int j = 0; j < matrixSize; j++) {
                newMatrix[i][j] = adjacencyMatrix[i][j];
            }
        }
        adjacencyMatrix = newMatrix;
        vertexToIndexInMatrix.put(index, matrixIndex);
    }

    @Override
    public void addEdge(int from, int to) {
        if (!(vertexToIndexInMatrix.containsKey(from) && vertexToIndexInMatrix.containsKey(to))) {
            throw new IllegalArgumentException("Вершины нет в графе");
        }

        int fromIndex = vertexToIndexInMatrix.get(from);
        int toIndex = vertexToIndexInMatrix.get(to);
        adjacencyMatrix[fromIndex][toIndex] = 1;
    }

    @Override
    public void removeEdge(int from, int to) {
        if (!(vertexToIndexInMatrix.containsKey(from) && vertexToIndexInMatrix.containsKey(to))) {
            throw new IllegalArgumentException("Вершины нет в графе");
        }

        int fromIndex = vertexToIndexInMatrix.get(from);
        int toIndex = vertexToIndexInMatrix.get(to);
        adjacencyMatrix[fromIndex][toIndex] = 0;
    }

    @Override
    public List<Integer> getNeighbours(int index) {
        if (!vertexToIndexInMatrix.containsKey(index)) {
            throw new IllegalArgumentException("Вершины с номером " + index + " нет в графе");
        }

        List<Integer> neighbours = new ArrayList<>();

        int indexInMatrix = vertexToIndexInMatrix.get(index);
        for (int j = 0; j < adjacencyMatrix.length; j++) {
            if (adjacencyMatrix[indexInMatrix][j] == 1) {
                for (Map.Entry<Integer, Integer> entry : vertexToIndexInMatrix.entrySet()) {
                    if (entry.getValue() == j) {
                        neighbours.add(entry.getKey());
                    }
                }
            }
        }

        return neighbours;
    }

    @Override
    public void removeVertex(int index) {
        if (!vertexToIndexInMatrix.containsKey(index)) {
            throw new IllegalArgumentException("Вершины с номером " + index + " нет в графе");
        }

        int removedIndex = vertexToIndexInMatrix.get(index);
        int matrixSize = vertexToIndexInMatrix.size();
        int[][] newMatrix = new int[matrixSize - 1][matrixSize - 1];
        for (int i = 0; i < newMatrix.length; i++) {
            int oldI = (i < removedIndex) ? i : i + 1;
            for (int j = 0; j < newMatrix.length; j++) {
                int oldJ = (j < removedIndex) ? j : j + 1;

                newMatrix[i][j] = adjacencyMatrix[oldI][oldJ];
            }
        }

        adjacencyMatrix = newMatrix;
        vertexToIndexInMatrix.remove(index);

        for (Map.Entry<Integer, Integer> entry : vertexToIndexInMatrix.entrySet()) {
            if (entry.getValue() > removedIndex) {
                entry.setValue(entry.getValue() - 1);
            }
        }
    }

    @Override
    public int getVertexCount() {
        return adjacencyMatrix.length;
    }

    @Override
    public List<Integer> getVertices() {
        return new ArrayList<>(vertexToIndexInMatrix.keySet());
    }

    @Override
    public List<Integer> topologicalSort() {
        return new TopologicalSort().topologicalSort(this);
    }

    @Override
    public void readFromFile(String path) {
        new GraphFileReader().readIntoGraph(path, this);
    }
}
