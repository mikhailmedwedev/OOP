package org.example;

import java.util.HashSet;
import java.util.Objects;

/**
 * Базовый абстрактный граф с общими методами для сравнения и вывода.
 */
public abstract class AbstractGraph implements Graph {

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
