package org.example;

/**
 * Реализует алгоритм Кнута-Морриса-Пратта для поиска подстроки.
 */
public class KmpMatcher {
    private final int[] pattern;
    private final int[] prefixTable;
    private int matchedLength;

    /**
     * Конструктор объекта для поиска заданной строки.
     *
     * @param searchString строка, которую необходимо найти
     */
    public KmpMatcher(String searchString) {
        if (searchString == null || searchString.isEmpty()) {
            throw new IllegalArgumentException("Строка не может быть пустой");
        }

        pattern = searchString.codePoints().toArray();
        prefixTable = buildPrefixTable();
        matchedLength = 0;
    }

    /**
     * Строит таблицу префиксов для алгоритма КМП.
     *
     * @return массив значений таблицы префиксов
     */
    private int[] buildPrefixTable() {
        int[] table = new int[pattern.length];

        int matched = 0;

        for (int i = 1; i < pattern.length; i++) {
            while (matched > 0 && pattern[i] != pattern[matched]) {
                matched = table[matched - 1];
            }

            if (pattern[i] == pattern[matched]) {
                matched++;
            }

            table[i] = matched;
        }

        return table;
    }

    /**
     * Обрабатывает следующий кодовую точку и проверяет наличие искомой строки.
     *
     * @param codePoint кодовая точка Unicode для обработки
     * @return true, если искомая строка найдена, иначе false
     */
    public boolean process(int codePoint) {
        while (matchedLength > 0 && codePoint != pattern[matchedLength]) {
            matchedLength = prefixTable[matchedLength - 1];
        }

        if (codePoint == pattern[matchedLength]) {
            matchedLength++;
        }

        if (matchedLength == pattern.length) {
            matchedLength = prefixTable[matchedLength - 1];
            return true;
        }

        return false;
    }
}
