package org.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Map;
import org.junit.jupiter.api.Test;

/**
 * Тесты для класса Sub.
 */
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

    @Test
    void hasVariablesTest() {
        Expression withVariables = new Sub(new Variable("x"), new Number(2));
        Expression withoutVariables = new Sub(new Number(5), new Number(3));

        assertTrue(withVariables.hasVariables());
        assertFalse(withoutVariables.hasVariables());
    }

    @Test
    void simplifyConstantOperands() {
        Expression sub = new Sub(new Number(8), new Number(3));

        Expression simplified = sub.simplify();

        assertEquals("5", simplified.toString());
        assertEquals(5, simplified.eval(Map.of()));
    }

    @Test
    void simplifyZeroRightOperand() {
        Expression sub = new Sub(new Variable("x"), new Number(0));

        assertEquals("x", sub.simplify().toString());
    }

    @Test
    void simplifyIdenticalOperands() {
        Expression sub = new Sub(new Variable("x"), new Variable("x"));

        Expression simplified = sub.simplify();

        assertEquals("0", simplified.toString());
        assertEquals(0, simplified.eval(Map.of()));
    }

    @Test
    void simplifyDoesNotModifyOriginalExpression() {
        Expression sub = new Sub(new Variable("x"), new Number(0));

        Expression simplified = sub.simplify();

        assertEquals("(x-0)", sub.toString());
        assertEquals("x", simplified.toString());
    }
}
