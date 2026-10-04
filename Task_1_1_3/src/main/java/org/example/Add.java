package org.example;

import java.util.Map;

/**
 * Класс - выражение, представляющее собой сложение двух подвыражений.
 */
public class Add extends Expression {
    private final Expression left;
    private final Expression right;

    /**
     * Конструктор для создания сложения.
     *
     * @param left левый операнд
     * @param right правый операнд
     */
    public Add(Expression left, Expression right) {
        if (left == null || right == null) {
            throw new IllegalArgumentException("Операнды не могут быть null");
        }

        this.left = left;
        this.right = right;
    }

    /**
     * Возвращает строковое представление сложения.
     *
     * @return строковое представления сложения
     */
    @Override
    public String toString() {
        return "(" + left.toString() + "+" + right.toString() + ")";
    }

    /**
     * Вычисляет сумму результатов вычисления левого и правого операндов.
     *
     * @param vars словарь, где ключ - имя переменной, а значение - ее числовое значение
     * @return результат сложения
     */
    @Override
    public int eval(Map<String, Integer> vars) {
        return left.eval(vars) + right.eval(vars);
    }

    /**
     * Вычисляет производную сложения.
     *
     * @param var имя переменной, по которой берется производная
     * @return выражение - производная суммы
     */
    @Override
    public Expression derivative(String var) {
        return new Add(left.derivative(var), right.derivative(var));
    }
}
