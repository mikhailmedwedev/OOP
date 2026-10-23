package org.example;

import java.util.List;

public interface Graph {
    void addVertex(int index);

    void removeVertex(int index);

    void addEdge(int from, int to);

    void removeEdge(int from, int to);

    List<Integer> getNeighbours(int index);

    List<Integer> topologicalSort();

    int getVertexCount();

    List<Integer> getVertices();

    List<int[]> getEdges();

    void readFromFile(String path);
}
