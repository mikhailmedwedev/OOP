package org.example;

import java.util.List;

public interface Graph {
    public void addVertex(int index);

    public void removeVertex(int index);

    public void addEdge(int from, int to);

    public void removeEdge(int from, int to);

    public List<Integer> getNeighbours(int index);

    public List<Integer> topologicalSort();

    public int getVertexCount();

    public List<Integer> getVertices();

    public void readFromFile(String path);
}
