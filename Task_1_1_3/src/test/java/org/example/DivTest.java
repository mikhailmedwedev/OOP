package org.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Map;

import org.junit.jupiter.api.Test;

class DivTest {

    @Test
    void evalValidDivision() {
        Expression div = new Div(new Variable("a"), new Number(2));
        Map<String, Integer> vars = Map.of("a", 10);

        assertEquals(5, div.eval(vars));
    }

    @Test
    void evalDivisionByZeroThrowsException() {
        Expression div = new Div(new Number(10), new Sub(new Number(5), new Number(5)));

        assertThrows(ArithmeticException.class, () -> div.eval(Map.of()));
    }

    @Test
    void constructorNullOperandThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> new Div(null, new Number(1)));
        assertThrows(IllegalArgumentException.class, () -> new Div(new Number(1), null));
    }

    @Test
    void divToString() {
        Expression div = new Div(new Variable("x"), new Number(2));

        assertEquals("(x/2)", div.toString());
    }

    @Test
    void derivativeTest() {
        Expression expr = new Div(new Variable("x"), new Number(2));
        Expression derX = expr.derivative("x");

        assertEquals("(((1*2)-(x*0))/(2*2))", derX.toString());
        assertEquals(0, derX.eval("x=10"));
    }
}
