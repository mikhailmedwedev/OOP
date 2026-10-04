package org.example;

import java.util.Map;

public class Sub extends Expression {
    private final Expression left;
    private final Expression right;

    public Sub(Expression left, Expression right) {
        if (left == null || right == null) {
            throw new IllegalArgumentException("Операнды не могут быть null");
        }

        this.left = left;
        this.right = right;
    }

    @Override
    public String toString() {
        return "(" + left.toString() + "-" + right.toString() + ")";
    }

    @Override
    public int eval(Map<String, Integer> vars) {
        return left.eval(vars) - right.eval(vars);
    }

    @Override
    public Expression derivative(String var) {
        return new Sub(left.derivative(var), right.derivative(var));
    }
}
