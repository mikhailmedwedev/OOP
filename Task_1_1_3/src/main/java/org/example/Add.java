package org.example;

import java.util.Collections;
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
     * @return строковое представление сложения
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

    /**
     * Проверяет, является ли выражение нулем.
     *
     * @param e проверяемое выражение
     * @return true - является 0; false - не является 0
     */
    private boolean isZero(Expression e) {
        return e instanceof Number && ((Number) e).getValue() == 0;
    }

    /**
     * Проверяет, содержит ли сумма переменные.
     *
     * @return true, если содержит; false - иначе
     */
    @Override
    public boolean hasVariables() {
        return left.hasVariables() || right.hasVariables();
    }

    /**
     * Упрощает сумму двух выражений.
     * Если два операнда не содержат переменных, вычисляет их сумму.
     * Если один из операндов равен 0, возвращает другой операнд.
     *
     * @return упрощенное выражение
     */
    @Override
    public Expression simplify() {
        Expression l = left.simplify();
        Expression r = right.simplify();

        if (!l.hasVariables() && !r.hasVariables()) {
            return new Number(l.eval(Collections.emptyMap()) + r.eval(Collections.emptyMap()));
        }

        if (isZero(l)) {
            return r;
        }

        if (isZero(r)) {
            return l;
        }

        return new Add(l, r);
    }
}
