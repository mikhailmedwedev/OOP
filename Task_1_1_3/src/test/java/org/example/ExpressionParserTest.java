package org.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Тесты для класса ExpressionParser.
 */
class ExpressionParserTest {

    @Test
    void parseNumber() {
        Expression expr = new ExpressionParser("50").parse();

        assertEquals("50", expr.toString());
    }

    @Test
    void parseVariable() {
        Expression expr = new ExpressionParser("var").parse();

        assertEquals("var", expr.toString());
    }

    @Test
    void parsePriority() {
        Expression expr = new ExpressionParser("a + b * c").parse();

        assertEquals("(a+(b*c))", expr.toString());
    }

    @Test
    void parseParentheses() {
        Expression expr = new ExpressionParser("(a + b) * c").parse();

        assertEquals("((a+b)*c)", expr.toString());
    }

    @Test
    void parseLeftAssociativity() {
        Expression expr = new ExpressionParser("a - b - c").parse();

        assertEquals("((a-b)-c)", expr.toString());
    }

    @Test
    void parseSpaces() {
        Expression expr = new ExpressionParser(" 3 + 2 * x ").parse();

        assertEquals("(3+(2*x))", expr.toString());
    }

    @Test
    void evaluateExpression() {
        Expression expr = new ExpressionParser("x + 2 * y").parse();

        assertEquals(11, expr.eval("x=3; y=4"));


    }

    @Test
    void rejectInvalidExpressions() {
        assertThrows(IllegalArgumentException.class,
                () -> new ExpressionParser("a + * b").parse());

        assertThrows(IllegalArgumentException.class,
                () -> new ExpressionParser("(a + b").parse());

        assertThrows(IllegalArgumentException.class,
                () -> new ExpressionParser("a + b)").parse());

        assertThrows(IllegalArgumentException.class,
                () -> new ExpressionParser("").parse());

        assertThrows(IllegalArgumentException.class,
                () -> new ExpressionParser("a b").parse());
    }
}
