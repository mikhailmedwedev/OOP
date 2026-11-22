package org.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Тесты для класса KmpMatcher.
 */
class KmpMatcherTest {

    @Test
    void findsPattern() {
        KmpMatcher matcher = new KmpMatcher("бра");

        assertFalse(matcher.process('а'));
        assertFalse(matcher.process('б'));
        assertFalse(matcher.process('р'));
        assertTrue(matcher.process('а'));
    }

    @Test
    void returnsFalseWhenPatternIsNotFound() {
        KmpMatcher matcher = new KmpMatcher("кот");

        int[] text = "собака".codePoints().toArray();

        for (int codePoint : text) {
            assertFalse(matcher.process(codePoint));
        }
    }

    @Test
    void findsOverlappingMatches() {
        KmpMatcher matcher = new KmpMatcher("аба");

        int[] text = "абаба".codePoints().toArray();
        int matches = 0;

        for (int codePoint : text) {
            if (matcher.process(codePoint)) {
                matches++;
            }
        }

        assertEquals(2, matches);
    }

    @Test
    void findsSingleCharacterPattern() {
        KmpMatcher matcher = new KmpMatcher("а");

        assertTrue(matcher.process('а'));
    }

    @Test
    void continuesSearchAfterMismatch() {
        KmpMatcher matcher = new KmpMatcher("abab");

        int[] text = "abacabab".codePoints().toArray();
        int matches = 0;

        for (int codePoint : text) {
            if (matcher.process(codePoint)) {
                matches++;
            }
        }

        assertEquals(1, matches);
    }

    @Test
    void handlesUnicodeCharacters() {
        KmpMatcher matcher = new KmpMatcher("😀");

        int emoji = "😀".codePointAt(0);

        assertTrue(matcher.process(emoji));
    }

    @Test
    void rejectsEmptyPattern() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new KmpMatcher("")
        );
    }

    @Test
    void rejectsNullPattern() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new KmpMatcher(null)
        );
    }
}
