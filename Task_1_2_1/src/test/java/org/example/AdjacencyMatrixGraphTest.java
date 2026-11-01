package org.example;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.Test;

/**
 * Тесты для класса AdjacencyMatrixGraph.
 */
class AdjacencyMatrixGraphTest {

    @Test
    void addVertexShouldIncreaseVertexCount() {
        AdjacencyMatrixGraph graph = new AdjacencyMatrixGraph();

        graph.addVertex(1);
        graph.addVertex(2);

        assertEquals(2, graph.getVertexCount());
    }

    @Test
    void addVertexShouldIgnoreDuplicateVertex() {
        AdjacencyMatrixGraph graph = new AdjacencyMatrixGraph();

        graph.addVertex(1);
        graph.addVertex(1);

        assertEquals(1, graph.getVertexCount());
    }

    @Test
    void removeVertexShouldRemoveVertexAndIncidentEdges() {
        AdjacencyMatrixGraph graph = new AdjacencyMatrixGraph();
        graph.addVertex(1);
        graph.addVertex(2);
        graph.addVertex(3);
        graph.addEdge(1, 2);
        graph.addEdge(2, 3);

        graph.removeVertex(2);

        assertEquals(2, graph.getVertexCount());
        assertEquals(List.of(), graph.getNeighbours(1));
        assertFalse(graph.getNeighbours(3).contains(2));
    }

    @Test
    void removeMissingVertexShouldThrowException() {
        AdjacencyMatrixGraph graph = new AdjacencyMatrixGraph();

        assertThrows(
                IllegalArgumentException.class,
                () -> graph.removeVertex(1)
        );
    }

    @Test
    void addEdgeShouldConnectVertices() {
        AdjacencyMatrixGraph graph = new AdjacencyMatrixGraph();
        graph.addVertex(1);
        graph.addVertex(2);

        graph.addEdge(1, 2);

        assertEquals(List.of(2), graph.getNeighbours(1));
    }

    @Test
    void addEdgeShouldIgnoreDuplicateEdge() {
        AdjacencyMatrixGraph graph = new AdjacencyMatrixGraph();
        graph.addVertex(1);
        graph.addVertex(2);

        graph.addEdge(1, 2);
        graph.addEdge(1, 2);

        assertEquals(List.of(2), graph.getNeighbours(1));
    }

    @Test
    void addEdgeWithMissingVertexShouldThrowException() {
        AdjacencyMatrixGraph graph = new AdjacencyMatrixGraph();
        graph.addVertex(1);

        assertThrows(
                IllegalArgumentException.class,
                () -> graph.addEdge(1, 2)
        );
    }

    @Test
    void removeEdgeShouldDisconnectVertices() {
        AdjacencyMatrixGraph graph = new AdjacencyMatrixGraph();
        graph.addVertex(1);
        graph.addVertex(2);
        graph.addEdge(1, 2);

        graph.removeEdge(1, 2);

        assertEquals(List.of(), graph.getNeighbours(1));
    }

    @Test
    void removeMissingEdgeShouldNotChangeGraph() {
        AdjacencyMatrixGraph graph = new AdjacencyMatrixGraph();
        graph.addVertex(1);
        graph.addVertex(2);

        assertDoesNotThrow(() -> graph.removeEdge(1, 2));
        assertEquals(2, graph.getVertexCount());
    }

    @Test
    void removeEdgeWithMissingVertexShouldThrowException() {
        AdjacencyMatrixGraph graph = new AdjacencyMatrixGraph();
        graph.addVertex(1);

        assertThrows(
                IllegalArgumentException.class,
                () -> graph.removeEdge(1, 2)
        );
    }

    @Test
    void addSelfLoopShouldMakeVertexItsOwnNeighbour() {
        AdjacencyMatrixGraph graph = new AdjacencyMatrixGraph();
        graph.addVertex(1);

        graph.addEdge(1, 1);

        assertEquals(List.of(1), graph.getNeighbours(1));
    }

    @Test
    void getNeighboursForMissingVertexShouldThrowException() {
        AdjacencyMatrixGraph graph = new AdjacencyMatrixGraph();

        assertThrows(
                IllegalArgumentException.class,
                () -> graph.getNeighbours(1)
        );
    }

    @Test
    void getNeighboursShouldReturnCopy() {
        AdjacencyMatrixGraph graph = new AdjacencyMatrixGraph();
        graph.addVertex(1);
        graph.addVertex(2);
        graph.addEdge(1, 2);

        List<Integer> neighbours = graph.getNeighbours(1);
        neighbours.clear();

        assertEquals(List.of(2), graph.getNeighbours(1));
    }

    @Test
    void getVerticesShouldReturnAllVertices() {
        AdjacencyMatrixGraph graph = new AdjacencyMatrixGraph();
        graph.addVertex(3);
        graph.addVertex(1);
        graph.addVertex(2);

        assertEquals(3, graph.getVertices().size());
        assertTrue(graph.getVertices().containsAll(List.of(1, 2, 3)));
    }

    @Test
    void getEdgesShouldReturnAllEdges() {
        AdjacencyMatrixGraph graph = new AdjacencyMatrixGraph();
        graph.addVertex(1);
        graph.addVertex(2);
        graph.addVertex(3);
        graph.addEdge(1, 2);
        graph.addEdge(2, 3);

        assertEquals(2, graph.getEdges().size());
    }

    @Test
    void equalsShouldReturnTrueForEqualGraphs() {
        AdjacencyMatrixGraph first = new AdjacencyMatrixGraph();
        first.addVertex(1);
        first.addVertex(2);
        first.addEdge(1, 2);

        AdjacencyMatrixGraph second = new AdjacencyMatrixGraph();
        second.addVertex(2);
        second.addVertex(1);
        second.addEdge(1, 2);

        assertEquals(first, second);
        assertEquals(first.hashCode(), second.hashCode());
    }

    @Test
    void equalsShouldReturnFalseForDifferentGraphs() {
        AdjacencyMatrixGraph first = new AdjacencyMatrixGraph();
        first.addVertex(1);
        first.addVertex(2);
        first.addEdge(1, 2);

        AdjacencyMatrixGraph second = new AdjacencyMatrixGraph();
        second.addVertex(1);
        second.addVertex(2);
        second.addEdge(2, 1);

        assertNotEquals(first, second);
    }

    @Test
    void equalsShouldReturnFalseForNull() {
        AdjacencyMatrixGraph graph = new AdjacencyMatrixGraph();
        graph.addVertex(1);

        assertNotEquals(null, graph);
    }

    @Test
    void toStringShouldContainVerticesAndEdges() {
        AdjacencyMatrixGraph graph = new AdjacencyMatrixGraph();
        graph.addVertex(1);
        graph.addVertex(2);
        graph.addEdge(1, 2);

        String result = graph.toString();

        assertTrue(result.contains("1"));
        assertTrue(result.contains("2"));
    }

    @Test
    void topologicalSortShouldRespectEdgeDirection() {
        AdjacencyMatrixGraph graph = new AdjacencyMatrixGraph();
        graph.addVertex(1);
        graph.addVertex(2);
        graph.addVertex(3);
        graph.addEdge(1, 2);
        graph.addEdge(2, 3);

        List<Integer> result = graph.topologicalSort();

        assertTrue(result.indexOf(1) < result.indexOf(2));
        assertTrue(result.indexOf(2) < result.indexOf(3));
    }

    @Test
    void topologicalSortShouldThrowForCycle() {
        AdjacencyMatrixGraph graph = new AdjacencyMatrixGraph();
        graph.addVertex(1);
        graph.addVertex(2);
        graph.addEdge(1, 2);
        graph.addEdge(2, 1);

        assertThrows(
                IllegalArgumentException.class,
                graph::topologicalSort
        );
    }

    @Test
    void readFromFileShouldLoadGraph() throws Exception {
        Path file = Files.createTempFile("graph-test", ".txt");

        try {
            Files.writeString(
                    file,
                    "3\n1 2 3\n2\n1 2\n2 3\n"
            );

            AdjacencyMatrixGraph graph = new AdjacencyMatrixGraph();
            graph.readFromFile(file.toString());

            assertEquals(3, graph.getVertexCount());
            assertEquals(List.of(2), graph.getNeighbours(1));
            assertEquals(List.of(3), graph.getNeighbours(2));
        } finally {
            Files.deleteIfExists(file);
        }
    }
}
