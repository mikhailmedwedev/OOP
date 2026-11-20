package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Ищет все вхождения подстроки в файле UTF-8.
 */
public class SubstringFinder {

    /**
     * Читает файл постепенно и возвращает индексы найденных вхождений.
     *
     * @param filename имя файла
     * @param substring непустая искомая подстрока
     * @return список индексов начала вхождений
     * @throws IOException если не удалось прочитать файл
     */
    public static List<Long> find(String filename, String substring) throws IOException {
        KmpMatcher matcher = new KmpMatcher(substring);
        List<Long> positions = new ArrayList<>();
        int length = substring.codePointCount(0, substring.length());
        long index = 0;

        try (BufferedReader reader = Files.newBufferedReader(
                Path.of(filename), StandardCharsets.UTF_8)) {
            int codePoint;
            while ((codePoint = readCodePoint(reader)) != -1) {
                if (matcher.accept(codePoint)) {
                    positions.add(index - length + 1);
                }
                index++;
            }
        }
        return positions;
    }

    /**
     * Читает символ Unicode, который может занимать один или два char.
     */
    private static int readCodePoint(BufferedReader reader) throws IOException {
        int first = reader.read();
        if (first == -1) {
            return -1;
        }
        if (Character.isHighSurrogate((char) first)) {
            int second = reader.read();
            if (second == -1 || !Character.isLowSurrogate((char) second)) {
                throw new IOException("Некорректная пара суррогатов Unicode");
            }
            return Character.toCodePoint((char) first, (char) second);
        }
        return first;
    }
}
