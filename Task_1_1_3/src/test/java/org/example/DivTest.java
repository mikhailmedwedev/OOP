package org.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Map;
import org.junit.jupiter.api.Test;

/**
 * Тесты для класса Div.
 */
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

    @Test
    void hasVariables() {
        Expression withVariables = new Div(new Variable("x"), new Number(2));
        Expression withoutVariables = new Div(new Number(6), new Number(3));

        assertTrue(withVariables.hasVariables());
        assertFalse(withoutVariables.hasVariables());
    }

    @Test
    void simplifyConstantOperands() {
        Expression div = new Div(new Number(12), new Number(3));
        Expression simplified = div.simplify();

        assertEquals("4", simplified.toString());
        assertEquals(4, simplified.eval(Map.of()));
    }

    @Test
    void simplifyDivisionByOne() {
        Expression div = new Div(new Variable("x"), new Number(1));

        assertEquals("x", div.simplify().toString());
    }

    @Test
    void simplifyDivisionByZeroThrowsException() {
        Expression div = new Div(
                new Variable("x"),
                new Sub(new Number(5), new Number(5))
        );

        assertThrows(ArithmeticException.class, div::simplify);
    }

    @Test
    void simplifyDoesNotModifyOriginalExpression() {
        Expression div = new Div(new Variable("x"), new Number(1));
        Expression simplified = div.simplify();

        assertEquals("(x/1)", div.toString());
        assertEquals("x", simplified.toString());
    }
}
