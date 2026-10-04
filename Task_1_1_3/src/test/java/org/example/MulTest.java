package org.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Map;

import org.junit.jupiter.api.Test;

/**
 * Тесты для класса Mul.
 */
class MulTest {

    @Test
    void evalTest() {
        Expression mul = new Mul(new Variable("x"), new Variable("y"));
        Map<String, Integer> vars = Map.of("x", 4, "y", 3);

        assertEquals(12, mul.eval(vars));
    }

    @Test
    void constructorNullOperandThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> new Mul(null, new Number(1)));
        assertThrows(IllegalArgumentException.class, () -> new Mul(new Number(1), null));
    }

    @Test
    void mulToString() {
        Expression mul = new Mul(new Number(3), new Variable("y"));

        assertEquals("(3*y)", mul.toString());
    }

    @Test
    void derivativeTest() {
        Expression expr = new Mul(new Number(2), new Variable("x"));
        Expression derX = expr.derivative("x");

        assertEquals("((0*x)+(2*1))", derX.toString());
        assertEquals(2, derX.eval("x=7"));
    }
}
