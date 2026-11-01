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
import org.junit.jupiter.api.io.TempDir;

/**
 * Тесты для класса AdjacencyListGraph.
 */
class AdjacencyListGraphTest {

    @Test
    void addVertexShouldIncreaseVertexCount() {
        AdjacencyListGraph graph = new AdjacencyListGraph();

        graph.addVertex(1);
        graph.addVertex(2);

        assertEquals(2, graph.getVertexCount());
    }

    @Test
    void addSameVertexShouldNotCreateDuplicate() {
        AdjacencyListGraph graph = new AdjacencyListGraph();

        graph.addVertex(1);
        graph.addVertex(1);

        assertEquals(1, graph.getVertexCount());
    }

    @Test
    void removeVertexShouldRemoveVertexAndIncidentEdges() {
        AdjacencyListGraph graph = new AdjacencyListGraph();
        graph.addVertex(1);
        graph.addVertex(2);
        graph.addVertex(3);
        graph.addEdge(1, 2);
        graph.addEdge(3, 2);

        graph.removeVertex(2);

        assertEquals(List.of(3, 1).size(), graph.getVertexCount());
        assertFalse(graph.getVertices().contains(2));
        assertEquals(List.of(), graph.getNeighbours(1));
        assertEquals(List.of(), graph.getNeighbours(3));
    }

    @Test
    void removeMissingVertexShouldThrow() {
        AdjacencyListGraph graph = new AdjacencyListGraph();

        assertThrows(
                IllegalArgumentException.class,
                () -> graph.removeVertex(10)
        );
    }

    @Test
    void addEdgeShouldCreateNeighbour() {
        AdjacencyListGraph graph = new AdjacencyListGraph();
        graph.addVertex(1);
        graph.addVertex(2);

        graph.addEdge(1, 2);

        assertEquals(List.of(2), graph.getNeighbours(1));
        assertEquals(List.of(), graph.getNeighbours(2));
    }

    @Test
    void addSameEdgeShouldNotCreateDuplicate() {
        AdjacencyListGraph graph = new AdjacencyListGraph();
        graph.addVertex(1);
        graph.addVertex(2);

        graph.addEdge(1, 2);
        graph.addEdge(1, 2);

        assertEquals(List.of(2), graph.getNeighbours(1));
    }

    @Test
    void addEdgeWithMissingVertexShouldThrow() {
        AdjacencyListGraph graph = new AdjacencyListGraph();
        graph.addVertex(1);

        assertThrows(
                IllegalArgumentException.class,
                () -> graph.addEdge(1, 2)
        );
    }

    @Test
    void removeEdgeShouldRemoveNeighbour() {
        AdjacencyListGraph graph = new AdjacencyListGraph();
        graph.addVertex(1);
        graph.addVertex(2);
        graph.addEdge(1, 2);

        graph.removeEdge(1, 2);

        assertEquals(List.of(), graph.getNeighbours(1));
    }

    @Test
    void removeMissingEdgeShouldNotChangeGraph() {
        AdjacencyListGraph graph = new AdjacencyListGraph();
        graph.addVertex(1);
        graph.addVertex(2);

        assertDoesNotThrow(() -> graph.removeEdge(1, 2));
        assertEquals(List.of(), graph.getNeighbours(1));
    }

    @Test
    void removeEdgeWithMissingVertexShouldThrow() {
        AdjacencyListGraph graph = new AdjacencyListGraph();
        graph.addVertex(1);

        assertThrows(
                IllegalArgumentException.class,
                () -> graph.removeEdge(1, 2)
        );
    }

    @Test
    void selfLoopShouldBeNeighbourOfItself() {
        AdjacencyListGraph graph = new AdjacencyListGraph();
        graph.addVertex(3);

        graph.addEdge(3, 3);

        assertEquals(List.of(3), graph.getNeighbours(3));
    }

    @Test
    void getNeighboursOfMissingVertexShouldThrow() {
        AdjacencyListGraph graph = new AdjacencyListGraph();

        assertThrows(
                IllegalArgumentException.class,
                () -> graph.getNeighbours(10)
        );
    }

    @Test
    void getNeighboursShouldReturnIndependentList() {
        AdjacencyListGraph graph = new AdjacencyListGraph();
        graph.addVertex(1);
        graph.addVertex(2);
        graph.addEdge(1, 2);

        List<Integer> neighbours = graph.getNeighbours(1);
        neighbours.clear();

        assertEquals(List.of(2), graph.getNeighbours(1));
    }

    @Test
    void getVerticesShouldReturnAllVertices() {
        AdjacencyListGraph graph = new AdjacencyListGraph();
        graph.addVertex(10);
        graph.addVertex(20);
        graph.addVertex(30);

        assertEquals(3, graph.getVertices().size());
        assertTrue(graph.getVertices().containsAll(List.of(10, 20, 30)));
    }

    @Test
    void getEdgesShouldReturnAllEdges() {
        AdjacencyListGraph graph = new AdjacencyListGraph();
        graph.addVertex(1);
        graph.addVertex(2);
        graph.addVertex(3);
        graph.addEdge(1, 2);
        graph.addEdge(2, 3);
        graph.addEdge(3, 3);

        List<int[]> edges = graph.getEdges();

        assertEquals(3, edges.size());
        assertTrue(edges.stream().anyMatch(e -> e[0] == 1 && e[1] == 2));
        assertTrue(edges.stream().anyMatch(e -> e[0] == 2 && e[1] == 3));
        assertTrue(edges.stream().anyMatch(e -> e[0] == 3 && e[1] == 3));
    }

    @Test
    void equalGraphsShouldBeEqualAndHaveSameHashCode() {
        AdjacencyListGraph first = new AdjacencyListGraph();
        first.addVertex(1);
        first.addVertex(2);
        first.addEdge(1, 2);

        AdjacencyListGraph second = new AdjacencyListGraph();
        second.addVertex(2);
        second.addVertex(1);
        second.addEdge(1, 2);

        assertEquals(first, second);
        assertEquals(first.hashCode(), second.hashCode());
    }

    @Test
    void graphsWithDifferentEdgesShouldNotBeEqual() {
        AdjacencyListGraph first = new AdjacencyListGraph();
        first.addVertex(1);
        first.addVertex(2);
        first.addEdge(1, 2);

        AdjacencyListGraph second = new AdjacencyListGraph();
        second.addVertex(1);
        second.addVertex(2);

        assertNotEquals(first, second);
    }

    @Test
    void graphShouldNotEqualNull() {
        AdjacencyListGraph graph = new AdjacencyListGraph();

        assertNotEquals(null, graph);
    }

    @Test
    void toStringShouldContainVerticesAndEdges() {
        AdjacencyListGraph graph = new AdjacencyListGraph();
        graph.addVertex(1);
        graph.addVertex(2);
        graph.addEdge(1, 2);

        String result = graph.toString();

        assertTrue(result.contains("Vertices:"));
        assertTrue(result.contains("Edges:"));
        assertTrue(result.contains("(1, 2)"));
    }

    @Test
    void topologicalSortShouldRespectEdgeDirection() {
        AdjacencyListGraph graph = new AdjacencyListGraph();
        graph.addVertex(1);
        graph.addVertex(2);
        graph.addVertex(3);
        graph.addEdge(1, 2);
        graph.addEdge(2, 3);

        List<Integer> sorted = graph.topologicalSort();

        assertTrue(sorted.indexOf(1) < sorted.indexOf(2));
        assertTrue(sorted.indexOf(2) < sorted.indexOf(3));
    }

    @Test
    void topologicalSortShouldThrowForCycle() {
        AdjacencyListGraph graph = new AdjacencyListGraph();
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
    void readFromFileShouldLoadGraph(@TempDir Path tempDir) throws Exception {
        Path file = tempDir.resolve("graph.txt");
        Files.writeString(file, "3\n1 2 3\n2\n1 2\n2 3\n");

        AdjacencyListGraph graph = new AdjacencyListGraph();
        graph.readFromFile(file.toString());

        assertEquals(3, graph.getVertexCount());
        assertEquals(List.of(2), graph.getNeighbours(1));
        assertEquals(List.of(3), graph.getNeighbours(2));
        assertEquals(List.of(), graph.getNeighbours(3));
    }
}
