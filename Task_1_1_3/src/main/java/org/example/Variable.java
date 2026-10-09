package org.example;

import java.util.Map;

/**
 * Класс, представляющий именованную переменную в выражении.
 */
public class Variable extends Expression {
    private final String name;

    /**
     * Конструктор для создания новой переменной с заданным именем.
     *
     * @param name имя переменной
     */
    public Variable(String name) {
        if (name == null) {
            throw new IllegalArgumentException("Переменная не может быть null");
        }

        this.name = name;
    }

    /**
     * Возвращает строковое представление переменной.
     *
     * @return имя переменной
     */
    @Override
    public String toString() {
        return this.name;
    }

    /**
     * Вычисляет значение переменной.
     *
     * @param vars словарь, где ключ - имя переменной, а значение - ее числовое значение
     * @return значение переменной
     */
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

    /**
     * Вычисляет производную переменной.
     *
     * @param var имя переменной, по которой берется производная
     * @return 1, если имя совпадает с var, 0 - иначе
     */
    @Override
    public Expression derivative(String var) {
        if (name.equals(var)) {
            return new Number(1);
        }
        return new Number(0);
    }

    /**
     * Упрощает переменную.
     *
     * @return упрощенное выражение
     */
    @Override
    public Expression simplify() {
        return this;
    }

    /**
     * Проверяет, содержит ли переменная переменные.
     *
     * @return всегда true
     */
    @Override
    public boolean hasVariables() {
        return true;
    }
}
