package org.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Map;

import org.junit.jupiter.api.Test;

class ExpressionTest {

    @Test
    void evalStringParsing() {
        Expression expr = new Add(new Variable("x"), new Variable("y"));

        assertEquals(30, expr.eval("x=10; y=20"));
    }

    @Test
    void evalStringParsingWithSpaces() {
        Expression expr = new Mul(new Variable("a"), new Variable("b"));

        assertEquals(12, expr.eval(" a = 3 ; b = 4 "));
    }

    @Test
    void evalStringNullOrBlank() {
        Expression expr = new Number(99);

        assertEquals(99, expr.eval((String) null));
        assertEquals(99, expr.eval(""));
        assertEquals(99, expr.eval("   "));
    }

    @Test
    void complexExpression() {
        // (x + 5) * (y / 2)
        Expression expr = new Mul(
                new Add(new Variable("x"), new Number(5)),
                new Div(new Variable("y"), new Number(2))
        );

        assertEquals(40, expr.eval("x=3; y=10"));
    }
}
