package org.example;

import java.util.Map;

/**
 * Класс - выражение, представляющее умножение двух подвыражений.
 */
public class Mul extends Expression {
    private final Expression left;
    private final Expression right;

    /**
     * Конструктор для создания операции умножения.
     *
     * @param left левый операнд
     * @param right правый операнд
     */
    public Mul(Expression left, Expression right) {
        if (left == null || right == null) {
            throw new IllegalArgumentException("Операнды не могут быть null");
        }

        this.left = left;
        this.right = right;
    }

    /**
     * Возвращает строковое представление умножения.
     *
     * @return строковое представление умножения
     */
    @Override
    public String toString() {
        return "(" + left.toString() + "*" + right.toString() + ")";
    }

    /**
     * Вычисляет произведение результатов вычисления левого и правого операндов.
     *
     * @param vars словарь, где ключ - имя переменной, а значение - ее числовое значение
     * @return результат умножения
     */
    @Override
    public int eval(Map<String, Integer> vars) {
        return left.eval(vars) * right.eval(vars);
    }

    /**
     * Вычисляет производную произведения.
     *
     * @param var имя переменной, по которой берется производная
     * @return выражение - производная произведения
     */
    @Override
    public Expression derivative(String var) {
        return new Add(
                new Mul(left.derivative(var), right),
                new Mul(left, right.derivative(var))
        );
    }
}
