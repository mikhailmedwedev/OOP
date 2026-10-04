package org.example;

import java.util.Map;

public class Div extends Expression {
    private final Expression left;
    private final Expression right;

    public Div(Expression left, Expression right) {
        if (left == null || right == null) {
            throw new IllegalArgumentException("Операнды не могут быть null");
        }

        this.left = left;
        this.right = right;
    }

    @Override
    public String toString() {
        return "(" + left.toString() + "/" + right.toString() + ")";
    }

    @Override
    public int eval(Map<String, Integer> vars) {
        int rightVal = right.eval(vars);
        if (rightVal == 0) {
            throw new ArithmeticException("Деление на 0");
        }

        return left.eval(vars) / rightVal;
    }
}
