package org.example;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Map;
import java.util.Collections;

import org.junit.jupiter.api.Test;

/**
 * Тесты для класса Number.
 */
class NumberTest {

    @Test
    void evalWithVariables() {
        Number number = new Number(86);
        Map<String, Integer> vars = Map.of("x", 10, "y", 20);

        assertEquals(86, number.eval(vars));
    }

    @Test
    void evalWithNullOrEmptyVars() {
        Number number = new Number(-10);

        assertEquals(-10, number.eval(Collections.emptyMap()));
        assertEquals(-10, number.eval((Map<String, Integer>) null));
    }

    @Test
    void numberToString() {
        Number number = new Number(100);

        assertEquals("100", number.toString());
    }

    @Test
    void derivativeTest() {
        Expression num = new Number(42);
        Expression der = num.derivative("x");

        assertEquals("0", der.toString());
        assertEquals(0, der.eval("x=10"));
    }
}
