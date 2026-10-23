
package org.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

/**
 * Тесты для класса IncidenceMatrixGraph.
 */
class IncidenceMatrixGraphTest {

    @TempDir
    Path tempDir;

    @Test
    void newGraphShouldBeEmpty() {
        IncidenceMatrixGraph graph = new IncidenceMatrixGraph();

        assertEquals(0, graph.getVertexCount());
        assertTrue(graph.getVertices().isEmpty());
        assertTrue(graph.getEdges().isEmpty());
    }

    @Test
    void addVertexShouldAddNewVertex() {
        IncidenceMatrixGraph graph = new IncidenceMatrixGraph();

        graph.addVertex(1);

        assertEquals(1, graph.getVertexCount());
        assertTrue(graph.getVertices().contains(1));
    }

    @Test
    void addExistingVertexShouldNotDuplicateIt() {
        IncidenceMatrixGraph graph = new IncidenceMatrixGraph();

        graph.addVertex(1);
        graph.addVertex(1);

        assertEquals(1, graph.getVertexCount());
    }

    @Test
    void removeVertexShouldRemoveIt() {
        IncidenceMatrixGraph graph = new IncidenceMatrixGraph();

        graph.addVertex(1);
        graph.addVertex(2);
        graph.removeVertex(1);

        assertFalse(graph.getVertices().contains(1));
        assertEquals(1, graph.getVertexCount());
    }

    @Test
    void addEdgeShouldAddEdgeAndNeighbour() {
        IncidenceMatrixGraph graph = new IncidenceMatrixGraph();

        graph.addVertex(1);
        graph.addVertex(2);
        graph.addEdge(1, 2);

        assertEquals(1, graph.getEdges().size());
        assertEquals(List.of(2), graph.getNeighbours(1));
    }

    @Test
    void removeEdgeShouldRemoveEdgeAndNeighbour() {
        IncidenceMatrixGraph graph = new IncidenceMatrixGraph();

        graph.addVertex(1);
        graph.addVertex(2);
        graph.addEdge(1, 2);
        graph.removeEdge(1, 2);

        assertEquals(0, graph.getEdges().size());
        assertTrue(graph.getNeighbours(1).isEmpty());
    }

    @Test
    void addDuplicateEdgeShouldNotDuplicateIt() {
        IncidenceMatrixGraph graph = new IncidenceMatrixGraph();

        graph.addVertex(1);
        graph.addVertex(2);
        graph.addEdge(1, 2);
        graph.addEdge(1, 2);

        assertEquals(1, graph.getEdges().size());
    }

    @Test
    void getNeighboursShouldReturnAllOutgoingNeighbours() {
        IncidenceMatrixGraph graph = new IncidenceMatrixGraph();

        graph.addVertex(1);
        graph.addVertex(2);
        graph.addVertex(3);
        graph.addEdge(1, 2);
        graph.addEdge(1, 3);

        assertEquals(2, graph.getNeighbours(1).size());
        assertTrue(graph.getNeighbours(1).contains(2));
        assertTrue(graph.getNeighbours(1).contains(3));
    }

    @Test
    void removeVertexShouldRemoveItsIncidentEdges() {
        IncidenceMatrixGraph graph = new IncidenceMatrixGraph();

        graph.addVertex(1);
        graph.addVertex(2);
        graph.addVertex(3);
        graph.addEdge(1, 2);
        graph.addEdge(2, 3);

        graph.removeVertex(2);

        assertFalse(graph.getVertices().contains(2));
        assertEquals(0, graph.getEdges().size());
    }

    @Test
    void topologicalSortShouldRespectEdgeOrder() {
        IncidenceMatrixGraph graph = new IncidenceMatrixGraph();

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
    void topologicalSortShouldFailForCycle() {
        IncidenceMatrixGraph graph = new IncidenceMatrixGraph();

        graph.addVertex(1);
        graph.addVertex(2);
        graph.addVertex(3);
        graph.addEdge(1, 2);
        graph.addEdge(2, 3);
        graph.addEdge(3, 1);

        assertThrows(
                IllegalArgumentException.class,
                graph::topologicalSort
        );
    }

    @Test
    void readFromFileShouldLoadGraph() throws Exception {
        Path file = tempDir.resolve("graph.txt");
        Files.writeString(file, "3\n1 2 3\n2\n1 2\n2 3\n");

        IncidenceMatrixGraph graph = new IncidenceMatrixGraph();
        graph.readFromFile(file.toString());

        assertEquals(3, graph.getVertexCount());
        assertTrue(graph.getNeighbours(1).contains(2));
        assertTrue(graph.getNeighbours(2).contains(3));
    }

    @Test
    void toStringShouldNotBeEmpty() {
        IncidenceMatrixGraph graph = new IncidenceMatrixGraph();

        graph.addVertex(1);

        assertNotNull(graph.toString());
        assertFalse(graph.toString().isEmpty());
    }

    @Test
    void equalGraphsShouldBeEqual() {
        IncidenceMatrixGraph first = new IncidenceMatrixGraph();
        IncidenceMatrixGraph second = new IncidenceMatrixGraph();

        first.addVertex(1);
        first.addVertex(2);
        first.addEdge(1, 2);

        second.addVertex(1);
        second.addVertex(2);
        second.addEdge(1, 2);

        assertEquals(first, second);
        assertEquals(second, first);
    }

    @Test
    void graphsWithDifferentEdgesShouldNotBeEqual() {
        IncidenceMatrixGraph first = new IncidenceMatrixGraph();
        IncidenceMatrixGraph second = new IncidenceMatrixGraph();

        first.addVertex(1);
        first.addVertex(2);
        first.addEdge(1, 2);

        second.addVertex(1);
        second.addVertex(2);

        assertNotEquals(first, second);
    }

    @Test
    void hashCodeForEqualGraphsShouldBeEqual() {
        IncidenceMatrixGraph first = new IncidenceMatrixGraph();
        first.addVertex(1);
        first.addVertex(2);
        first.addEdge(1, 2);

        IncidenceMatrixGraph second = new IncidenceMatrixGraph();
        second.addVertex(1);
        second.addVertex(2);
        second.addEdge(1, 2);

        assertEquals(first, second);
        assertEquals(first.hashCode(), second.hashCode());
    }
}
