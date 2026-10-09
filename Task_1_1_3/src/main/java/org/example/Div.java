package org.example;

import java.util.Collections;
import java.util.Map;

/**
 * Класс - выражение, представляющее собой деление двух подвыражений.
 */
public class Div extends Expression {
    private final Expression left;
    private final Expression right;

    /**
     * Конструктор для создания операции деления.
     *
     * @param left левый операнд
     * @param right правый операнд
     */
    public Div(Expression left, Expression right) {
        if (left == null || right == null) {
            throw new IllegalArgumentException("Операнды не могут быть null");
        }

        this.left = left;
        this.right = right;
    }

    /**
     * Возвращает строковое представление деления.
     *
     * @return строковое представление деления
     */
    @Override
    public String toString() {
        return "(" + left.toString() + "/" + right.toString() + ")";
    }

    /**
     * Вычисляет частное результатов вычисления левого и правого операндов.
     *
     * @param vars словарь, где ключ - имя переменной, а значение - ее числовое значение
     * @return результат деления
     */
    @Override
    public int eval(Map<String, Integer> vars) {
        int rightVal = right.eval(vars);
        if (rightVal == 0) {
            throw new ArithmeticException("Деление на 0");
        }

        return left.eval(vars) / rightVal;
    }

    /**
     * Вычисляет производную частного.
     *
     * @param var имя переменной, по которой берется производная
     * @return выражение - производная частного
     */
    @Override
    public Expression derivative(String var) {
        return new Div(
                new Sub(
                        new Mul(left.derivative(var), right),
                        new Mul(left, right.derivative(var))
                ),
                new Mul(right, right)
        );
    }

    /**
     * Проверяет, является ли выражение 0.
     *
     * @param e проверяемое выражение
     * @return true - является 0; false - не является
     */
    private boolean isZero(Expression e) {
        return e instanceof Number && ((Number) e).getValue() == 0;
    }

    /**
     * Проверяет, является ли выражение 1.
     *
     * @param e проверяемое выражение
     * @return true - является 1; false - не является
     */
    private boolean isOne(Expression e) {
        return e instanceof Number && ((Number) e).getValue() == 1;
    }

    /**
     * Проверяет, содержит ли частное переменные.
     *
     * @return true, если содержит; false - иначе
     */
    @Override
    public boolean hasVariables() {
        return left.hasVariables() || right.hasVariables();
    }

    /**
     * Упрощает частное двух выражений.
     * Если оба операнда не содержат переменных, вычисляет их частное.
     * Деление на 0 приводит к исключению.
     * Деление на 1 заменяется левым операндом.
     *
     * @return упрощенное выражение
     */
    @Override
    public Expression simplify() {
        Expression l = left.simplify();
        Expression r = right.simplify();

        if (isZero(r)) {
            throw new ArithmeticException("Деление на 0");
        }

        if (!l.hasVariables() && !r.hasVariables()) {
            return new Number(l.eval(Collections.emptyMap()) / r.eval(Collections.emptyMap()));
        }

        if (isOne(r)) {
            return l;
        }

        return new Div(l, r);
    }
}
