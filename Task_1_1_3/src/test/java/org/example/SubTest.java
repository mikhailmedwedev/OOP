package org.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Map;

import org.junit.jupiter.api.Test;

class SubTest {

    @Test
    void evalTest() {
        Expression sub = new Sub(new Variable("a"), new Number(3));
        Map<String, Integer> vars = Map.of("a", 10);

        assertEquals(7, sub.eval(vars));
    }

    @Test
    void constructorNullOperandThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> new Sub(null, new Number(1)));
        assertThrows(IllegalArgumentException.class, () -> new Sub(new Number(1), null));
    }

    @Test
    void subToString() {
        Expression sub = new Sub(new Variable("x"), new Number(5));

        assertEquals("(x-5)", sub.toString());
    }

    @Test
    void derivativeTest() {
        Expression expr = new Sub(new Variable("x"), new Number(5));
        Expression derX = expr.derivative("x");

        assertEquals("(1-0)", derX.toString());
        assertEquals(1, derX.eval("x=10"));
    }
}
