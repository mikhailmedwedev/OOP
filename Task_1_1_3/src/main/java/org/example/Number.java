package org.example;

import java.util.Map;

/**
 * Класс, представляющий число в выражении.
 */
public class Number extends Expression {
    private final int value;

    /**
     * Конструктор для создания нового числа.
     *
     * @param value значение числа
     */
    public Number(int value) {
        this.value = value;
    }

    /**
     * Возвращает строковое представление числа.
     *
     * @return строковое представление числа
     */
    @Override
    public String toString() {
        return String.valueOf(value);
    }

    /**
     * Возвращает значение числа.
     *
     * @return числовое значение
     */
    public int getValue() {
        return this.value;
    }

    /**
     * Вычисляет значение числа.
     *
     * @param vars словарь, где ключ - имя переменной, а значение - ее числовое значение
     * @return числовое значение числа
     */
    @Override
    public int eval(Map<String, Integer> vars) {
        return value;
    }

    /**
     * Вычисляет производную числа.
     *
     * @param var имя переменной, по которой берется производная
     * @return новое выражение со значением 0
     */
    @Override
    public Expression derivative(String var) {
        return new Number(0);
    }

    /**
     * Упрощает число.
     *
     * @return упрощенное выражение
     */
    @Override
    public Expression simplify() {
        return this;
    }

    /**
     * Проверяет, содержит ли число переменные.
     *
     * @return всегда false
     */
    @Override
    public boolean hasVariables() {
        return false;
    }
}
