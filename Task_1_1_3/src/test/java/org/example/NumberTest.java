package org.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.Collections;
import java.util.Map;
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

    @Test
    void getValueTest() {
        Number number = new Number(50);

        assertEquals(50, number.getValue());
    }

    @Test
    void hasVariablesTest() {
        Number number = new Number(50);

        assertFalse(number.hasVariables());
    }

    @Test
    void simplifyTest() {
        Number number = new Number(50);

        Expression simplified = number.simplify();

        assertEquals("50", simplified.toString());
        assertEquals(50, simplified.eval(Collections.emptyMap()));
    }
}
