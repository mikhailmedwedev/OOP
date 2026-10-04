package org.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Map;

import org.junit.jupiter.api.Test;

/**
 * Тесты для класса Add.
 */
class AddTest {

    @Test
    void evalTest() {
        Expression add = new Add(new Number(10), new Variable("x"));
        Map<String, Integer> vars = Map.of("x", 5);

        assertEquals(15, add.eval(vars));
    }

    @Test
    void constructorNullOperandThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> new Add(null, new Number(1)));
        assertThrows(IllegalArgumentException.class, () -> new Add(new Number(1), null));
    }

    @Test
    void addToString() {
        Expression add = new Add(new Number(2), new Variable("a"));

        assertEquals("(2+a)", add.toString());
    }

    @Test
    void derivativeTest() {
        Expression expr = new Add(new Variable("x"), new Variable("y"));
        Expression derX = expr.derivative("x");

        assertEquals("(1+0)", derX.toString());
        assertEquals(1, derX.eval("x=2; y=3"));
    }
}
