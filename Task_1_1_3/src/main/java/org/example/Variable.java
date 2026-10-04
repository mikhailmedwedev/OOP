package org.example;

import java.util.Map;

public class Variable extends Expression {
    private final String name;

    public Variable(String name) {
        if (name == null) {
            throw new IllegalArgumentException("Переменная не может быть null");
        }

        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }

    @Override
    public int eval(Map<String, Integer> vars) {
        if (vars == null) {
            throw new IllegalArgumentException("Словарь не может быть null");
        }
        if (!vars.containsKey(name)) {
            throw new IllegalArgumentException("Не найдено значение для переменной: " + name);
        }

        return vars.get(name);
    }
}
