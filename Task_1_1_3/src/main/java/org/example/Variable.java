package org.example;

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
}
