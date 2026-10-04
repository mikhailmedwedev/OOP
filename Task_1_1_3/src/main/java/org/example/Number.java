package org.example;

import java.util.Map;

public class Number extends Expression {
    private final int value;

    public Number(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    @Override
    public int eval(Map<String, Integer> vars) {
        return value;
    }

    @Override
    public Expression derivative(String var) {
        return new Number(0);
    }
}
