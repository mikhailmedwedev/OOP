package org.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class GraphFileReader {
    public void readIntoGraph(String path, Graph graph) {
        try (Scanner scanner = new Scanner(new File(path))) {
            int vertexCount = scanner.nextInt();
            if (vertexCount < 0) {
                throw new IllegalArgumentException("Некорректное количество вершин");
            }
            for (int vertex = 0; vertex < vertexCount; vertex++) {
                graph.addVertex(scanner.nextInt());
            }

            int edgeCount = scanner.nextInt();
            if (edgeCount < 0) {
                throw new IllegalArgumentException("Некорректное количество ребер");
            }
            for (int edge = 0; edge < edgeCount; edge++) {
                int from = scanner.nextInt();
                int to = scanner.nextInt();
                graph.addEdge(from, to);
            }
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("Проблемы с файлом " + path);
        }
    }
}
