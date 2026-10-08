package org.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

    @Test
    void hasVariablesTest() {
        Expression withVariables = new Mul(new Variable("x"), new Number(2));
        Expression withoutVariables = new Mul(new Number(3), new Number(4));

        assertTrue(withVariables.hasVariables());
        assertFalse(withoutVariables.hasVariables());
    }

    @Test
    void simplifyConstantOperands() {
        Expression mul = new Mul(new Number(3), new Number(4));

        Expression simplified = mul.simplify();

        assertEquals("12", simplified.toString());
        assertEquals(12, simplified.eval(Map.of()));
    }

    @Test
    void simplifyZeroLeftOperand() {
        Expression mul = new Mul(new Number(0), new Variable("x"));

        assertEquals("0", mul.simplify().toString());
    }

    @Test
    void simplifyZeroRightOperand() {
        Expression mul = new Mul(new Variable("x"), new Number(0));

        assertEquals("0", mul.simplify().toString());
    }

    @Test
    void simplifyOneLeftOperand() {
        Expression mul = new Mul(new Number(1), new Variable("x"));

        assertEquals("x", mul.simplify().toString());
    }

    @Test
    void simplifyOneRightOperand() {
        Expression mul = new Mul(new Variable("x"), new Number(1));

        assertEquals("x", mul.simplify().toString());
    }

    @Test
    void simplifyDoesNotModifyOriginalExpression() {
        Expression mul = new Mul(new Number(1), new Variable("x"));

        Expression simplified = mul.simplify();

        assertEquals("(1*x)", mul.toString());
        assertEquals("x", simplified.toString());
    }
}
