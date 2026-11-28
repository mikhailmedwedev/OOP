package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Выполняет поиск подстроки в файле с помощью алгоритма КМП.
 */
public class FileSearcher {

    /**
     * Находит все вхождения заданной строки в файле.
     *
     * @param fileName путь к файлу
     * @param searchString строка, которую необходимо найти
     * @return список позиций найденных вхождений
     * @throws IOException если произошла ошибка при чтении файла
     */
    public static List<Long> find(String fileName, String searchString) throws IOException {
        KmpMatcher matcher = new KmpMatcher(searchString);
        List<Long> positions = new ArrayList<>();

        try (BufferedReader reader = Files.newBufferedReader(
                Path.of(fileName), StandardCharsets.UTF_8)) {
            long position = 0;
            int searchLength = searchString.codePointCount(0, searchString.length());
            int codePoint;

            while ((codePoint = readCodePoint(reader)) != -1) {
                if (matcher.process(codePoint)) {
                    positions.add(position - searchLength + 1);
                }

                position++;
            }
        }

        return positions;
    }

    /**
     * Читает следующую кодовую точку Unicode из файла.
     *
     * @param reader объект для чтения файла
     * @return кодовая точка Unicode или -1, если файл закончился
     * @throws IOException если произошла ошибка при чтении
     */
    private static int readCodePoint(BufferedReader reader) throws IOException {
        int first = reader.read();

        if (first == -1) {
            return -1;
        }

        char firstChar = (char) first;

        if (!Character.isHighSurrogate(firstChar)) {
            return first;
        }

        reader.mark(1);
        int second = reader.read();
        char secondChar = (char) second;

        if (second != -1 &&  Character.isLowSurrogate(secondChar)) {
            return Character.toCodePoint(firstChar, secondChar);
        }

        if (second != -1) {
            reader.reset();
        }

        return first;
    }

}
