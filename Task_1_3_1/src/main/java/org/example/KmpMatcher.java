package org.example;

/**
 * Ищет подстроку алгоритмом Кнута — Морриса — Пратта,
 * получая текст по одному символу Unicode.
 */
public class KmpMatcher {
    private final int[] pattern;
    private final int[] prefix;
    private int matched;

    /**
     * Создаёт объект для поиска непустой подстроки.
     *
     * @param substring искомая подстрока
     */
    public KmpMatcher(String substring) {
        if (substring.isEmpty()) {
            throw new IllegalArgumentException("Подстрока не должна быть пустой");
        }
        pattern = substring.codePoints().toArray();
        prefix = buildPrefix();
    }

    /**
     * Строит таблицу длин совпадающих префиксов и суффиксов.
     */
    private int[] buildPrefix() {
        int[] result = new int[pattern.length];
        int length = 0;
        for (int i = 1; i < pattern.length; i++) {
            while (length > 0 && pattern[i] != pattern[length]) {
                length = result[length - 1];
            }
            if (pattern[i] == pattern[length]) {
                length++;
            }
            result[i] = length;
        }
        return result;
    }

    /**
     * Обрабатывает следующий символ текста. Учитывает перекрывающиеся вхождения.
     *
     * @param codePoint кодовая точка Unicode
     * @return true, если на этом символе заканчивается вхождение подстроки
     */
    public boolean accept(int codePoint) {
        while (matched > 0 && codePoint != pattern[matched]) {
            matched = prefix[matched - 1];
        }
        if (codePoint == pattern[matched]) {
            matched++;
        }
        if (matched == pattern.length) {
            matched = prefix[matched - 1];
            return true;
        }
        return false;
    }
}
