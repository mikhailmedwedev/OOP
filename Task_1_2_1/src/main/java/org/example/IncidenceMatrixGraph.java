package org.example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Реализация графа через матрицу инцидентности.
 */
public class IncidenceMatrixGraph extends AbstractGraph {
    private final Map<Integer, Integer> vertexToIndexInMatrix = new HashMap<>();

    private int[][] incidenceMatrix = new int[0][0];

    private List<int[]> edges = new ArrayList<>();

    /**
     * Добавляет вершину в граф.
     *
     * @param index номер вершины
     */
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

    /**
     * Добавляет ребро в граф.
     *
     * @param from номер начальной вершины.
     * @param to номер конечной вершины.
     */
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

    /**
     * Удаляет ребро из графа.
     *
     * @param from номер начальной вершины
     * @param to номер конечной вершины
     */
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

    /**
     * Удаляет вершину из графа.
     *
     * @param index номер вершины
     */
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

    /**
     * Возвращает список смежных вершин для заданной вершины.
     *
     * @param index номер вершины
     * @return список номеров вершин.
     */
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

    /**
     * Возвращает число вершин графа.
     *
     * @return число вершин
     */
    @Override
    public int getVertexCount() {
        return vertexToIndexInMatrix.size();
    }

    /**
     * Возвращает список всех вершин графа.
     *
     * @return список номеров всех вершин
     */
    @Override
    public List<Integer> getVertices() {
        return new ArrayList<>(vertexToIndexInMatrix.keySet());
    }

    /**
     * Возвращает список всех ребер.
     *
     * @return список всех ребер
     */
    @Override
    public List<int[]> getEdges() {
        List<int[]> result = new ArrayList<>();

        for (int[] edge : edges) {
            result.add(edge.clone());
        }

        return result;
    }

    /**
     * Топологическая сортировка вершин графа.
     *
     * @return список вершин в порядке топологической сортировки
     */
    @Override
    public List<Integer> topologicalSort() {
        return new TopologicalSort().topologicalSort(this);
    }

    /**
     * Считывает структуру графа из файла.
     *
     * @param path путь к файлу
     */
    @Override
    public void readFromFile(String path) {
        new GraphFileReader().readIntoGraph(path, this);
    }

    /**
     * Возвращает строковое представление графа.
     *
     * @return строка с описанием вершин и ребер графа
     */
    @Override
    public String toString() {
        return "Vertices: " + getVertices() + "\nEdges: " + edges.stream()
                .map(edge -> "(" + edge[0] + ", " + edge[1] + ")")
                .toList();
    }
}
