package org.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

/**
 * Тесты для класса FileSearcher.
 */
class FileSearcherTest {
    @TempDir
    Path tempDir;

    @Test
    void findsPattern() throws IOException {
        Path file = tempDir.resolve("input.txt");
        Files.writeString(file, "абракадабра", StandardCharsets.UTF_8);

        List<Long> positions = FileSearcher.find(file.toString(), "бра");

        assertEquals(List.of(1L, 8L), positions);
    }

    @Test
    void returnsEmptyListWhenPatternIsNotFound() throws IOException {
        Path file = tempDir.resolve("input.txt");
        Files.writeString(file, "абракадабра", StandardCharsets.UTF_8);

        List<Long> positions = FileSearcher.find(file.toString(), "кот");

        assertEquals(List.of(), positions);
    }

    @Test
    void findsOverlappingOccurrences() throws IOException {
        Path file = tempDir.resolve("input.txt");
        Files.writeString(file, "абаба", StandardCharsets.UTF_8);

        List<Long> positions = FileSearcher.find(file.toString(), "аба");

        assertEquals(List.of(0L, 2L), positions);
    }

    @Test
    void handlesUnicodeCharacters() throws IOException {
        Path file = tempDir.resolve("input.txt");
        Files.writeString(file, "a😀b😀", StandardCharsets.UTF_8);

        List<Long> positions = FileSearcher.find(file.toString(), "😀");

        assertEquals(List.of(1L, 3L), positions);
    }

    @Test
    void returnsEmptyListForEmptyFile() throws IOException {
        Path file = tempDir.resolve("input.txt");
        Files.writeString(file, "", StandardCharsets.UTF_8);

        List<Long> positions = FileSearcher.find(file.toString(), "бра");

        assertEquals(List.of(), positions);
    }

    @Test
    void throwsExceptionWhenFileDoesNotExist() {
        Path file = tempDir.resolve("missing.txt");

        assertThrows(
                IOException.class,
                () -> FileSearcher.find(file.toString(), "бра")
        );
    }

    @Test
    void findsPatternInLargeFile() throws IOException {
        String story;

        try (InputStream input = getClass().getResourceAsStream(
                "/chudesny_doctor.txt")) {
            assertNotNull(input);
            story = new String(input.readAllBytes(), StandardCharsets.UTF_8);
        }

        String searchString = "По рецепту профессора Пирогова";
        int repeatCount = 1000;

        int searchIndex = story.indexOf(searchString);
        assertTrue(searchIndex >= 0);

        int patternPosition = story.codePointCount(0, searchIndex);
        int storyLength = story.codePointCount(0, story.length());

        Path file = tempDir.resolve("large.txt");

        try (BufferedWriter writer = Files.newBufferedWriter(
                file, StandardCharsets.UTF_8)) {

            for (int i = 0; i < repeatCount; i++) {
                writer.write(story);
            }
        }

        List<Long> positions = FileSearcher.find(
                file.toString(), searchString);

        List<Long> expectedPositions = new ArrayList<>();

        for (int i = 0; i < repeatCount; i++) {
            expectedPositions.add((long) i * storyLength + patternPosition);
        }

        assertEquals(expectedPositions, positions);
    }
}
