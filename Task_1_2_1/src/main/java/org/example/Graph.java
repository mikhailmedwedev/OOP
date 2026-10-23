package org.example;

import java.util.List;

/**
 * Интерфейс, представляющий ориентированный граф.
 */
public interface Graph {

    /**
     * Добавляет новую вершину в граф.
     *
     * @param index номер вершины
     */
    void addVertex(int index);


    /**
     * Удаляет вершину из графа.
     *
     * @param index номер вершины
     */
    void removeVertex(int index);

    /**
     * Добавляет ориентированное ребром из from в to.
     *
     * @param from номер начальной вершины.
     * @param to номер конечной вершины.
     */
    void addEdge(int from, int to);

    /**
     * Удаляет ориентированное ребро из from в to.
     *
     * @param from номер начальной вершины
     * @param to номер конечной вершины
     */
    void removeEdge(int from, int to);

    /**
     * Возвращает список смежных вершин для заданной вершины.
     *
     * @param index номер вершины
     * @return список номеров соседних вершин
     */
    List<Integer> getNeighbours(int index);

    /**
     * Топологическая сортировка вершин графа.
     *
     * @return список вершин в порядке топологической сортировки
     */
    List<Integer> topologicalSort();

    /**
     * Общее число вершин в графе.
     *
     * @return количество вершин
     */
    int getVertexCount();

    /**
     * Список всех вершин графа.
     *
     * @return список номеров всех вершин
     */
    List<Integer> getVertices();

    /**
     * Список всех ребер графа.
     *
     * @return список всех ребер графа.
     */
    List<int[]> getEdges();

    /**
     * Считывает структуру графа из файла заданного формата.
     *
     * @param path путь к файлу
     */
    void readFromFile(String path);
}
