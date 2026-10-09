package org.example;

import java.util.Collections;
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
     * Проверяет, содержит ли произведение переменные.
     *
     * @return true, если содержит; false - иначе
     */
    @Override
    public boolean hasVariables() {
        return left.hasVariables() || right.hasVariables();
    }

    /**
     * Упрощает произведение двух выражений.
     * Если оба операнда не содержат переменных, вычисляет их произведение.
     * Умножение на 0 заменяется 0.
     * Умножение на 1 заменяется соответствующим другим операндом.
     *
     * @return упрощенное выражение
     */
    @Override
    public Expression simplify() {
        Expression l = left.simplify();
        Expression r = right.simplify();

        if (!l.hasVariables() && !r.hasVariables()) {
            return new Number(l.eval(Collections.emptyMap()) * r.eval(Collections.emptyMap()));
        }

        if (isZero(l) || isZero(r)) {
            return new Number(0);
        }

        if (isOne(l)) {
            return r;
        }

        if (isOne(r)) {
            return l;
        }

        return new Mul(l, r);
    }
}
