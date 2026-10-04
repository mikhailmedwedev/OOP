package org.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Map;

import org.junit.jupiter.api.Test;

class VariableTest {

    @Test
    void evalWithValidVariable() {
        Variable x = new Variable("x");
        Map<String, Integer> vars = Map.of("x", 40, "y", 20);

        assertEquals(40, x.eval(vars));
    }

    @Test
    void evalMissingVariableThrowsException() {
        Variable x = new Variable("x");
        Map<String, Integer> vars = Map.of("y", 20);

        assertThrows(IllegalArgumentException.class, () -> x.eval(vars));
    }

    @Test
    void evalNullMapThrowsException() {
        Variable x = new Variable("x");

        assertThrows(IllegalArgumentException.class, () -> x.eval((Map<String, Integer>) null));
    }

    @Test
    void constructorNullNameThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> new Variable(null));
    }

    @Test
    void variableToString() {
        Variable x = new Variable("varName");

        assertEquals("varName", x.toString());
    }

    @Test
    void derivativeTest() {
        Expression var = new Variable("x");

        Expression derSame = var.derivative("x");
        Expression derOther = var.derivative("y");

        assertEquals("1", derSame.toString());
        assertEquals("0", derOther.toString());

        assertEquals(1, derSame.eval("x=5"));
        assertEquals(0, derOther.eval("x=5; y=10"));
    }
}
