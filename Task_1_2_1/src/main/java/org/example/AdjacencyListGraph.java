package org.example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Реализация орграфа через список смежности.
 */
public class AdjacencyListGraph implements Graph {
    private final Map<Integer, List<Integer>> adjacencyList;

    /**
     * Создает пустой граф на основе списка смежности.
     */
    public AdjacencyListGraph() {
        this.adjacencyList = new HashMap<>();
    }

    /**
     * Добавление вершины в граф.
     *
     * @param index номер вершины
     */
    @Override
    public void addVertex(int index) {
        if (!adjacencyList.containsKey(index)) {
            adjacencyList.put(index, new ArrayList<>());
        }
    }

    /**
     * Удаление вершины из графа.
     *
     * @param index номер вершины
     */
    @Override
    public void removeVertex(int index) {
        if (!adjacencyList.containsKey(index)) {
            throw new IllegalArgumentException("Вершины с номером " + index + " нет в графе");
        }

        adjacencyList.remove(index);

        for (List<Integer> neighbours : adjacencyList.values()) {
            neighbours.remove(Integer.valueOf(index));
        }
    }

    /**
     * Добавление ребра в граф.
     *
     * @param from номер начальной вершины.
     * @param to номер конечной вершины.
     */
    @Override
    public void addEdge(int from, int to) {
        if (!(adjacencyList.containsKey(from) && adjacencyList.containsKey(to))) {
            throw new IllegalArgumentException("Вершины нет в графе");
        }

        if (!adjacencyList.get(from).contains(to)) {
            adjacencyList.get(from).add(to);
        }
    }

    /**
     * Удаление ребра из графа.
     *
     * @param from номер начальной вершины
     * @param to номер конечной вершины
     */
    @Override
    public void removeEdge(int from, int to) {
        if (!(adjacencyList.containsKey(from) && adjacencyList.containsKey(to))) {
            throw new IllegalArgumentException("Вершины нет в графе");
        }

        if (adjacencyList.get(from).contains(to)) {
            adjacencyList.get(from).remove(Integer.valueOf(to));
        }
    }

    /**
     * Возвращает список смежных вершин для заданной вершины.
     *
     * @param index номер вершины
     * @return список номеров вершин
     */
    @Override
    public List<Integer> getNeighbours(int index) {
        if (!adjacencyList.containsKey(index)) {
            throw new IllegalArgumentException("Вершины с индексом " + index + " нет в графе");
        }

        return new ArrayList<>(adjacencyList.get(index));
    }

    /**
     * Число вершин в графе.
     *
     * @return число вершин
     */
    @Override
    public int getVertexCount() {
        return adjacencyList.size();
    }

    /**
     * Возвращает список всех вершин графа.
     *
     * @return список вершин
     */
    @Override
    public List<Integer> getVertices() {
        return new ArrayList<>(adjacencyList.keySet());
    }

    /**
     * Возвращает список всех ребер графа.
     *
     * @return список всех ребер графа
     */
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
        return "Vertices: " + getVertices() + "\nEdges: " + getEdges().stream()
                .map(edge -> "(" + edge[0] + ", " + edge[1] + ")")
                .toList();
    }

    /**
     * Подсчитывает количество вхождений каждого ребра.
     *
     * @param edges список ребер
     * @return словарь с парами: ребро - количество
     */
    private static Map<List<Integer>, Integer> edgeCounts(List<int[]> edges) {
        Map<List<Integer>, Integer> counts = new HashMap<>();

        for (int[] edge : edges) {
            List<Integer> key = List.of(edge[0], edge[1]);
            counts.put(key, counts.getOrDefault(key, 0) + 1);
        }

        return counts;
    }

    /**
     * Сравнивает текущий граф с другим объектом на логическое равенство.
     * Графы равны, если содержат одинаковый набор вершин и ребер.
     *
     * @param obj объект для сравнения
     * @return true, если объекты равны, иначе false
     */
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

    /**
     * Возвращает хэш-код графа, вычисленный на основе его вершин и ребер.
     *
     * @return хэш-код графа
     */
    @Override
    public int hashCode() {
        return Objects.hash(
                new HashSet<>(getVertices()),
                edgeCounts(getEdges())
        );
    }
}
