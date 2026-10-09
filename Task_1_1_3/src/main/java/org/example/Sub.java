package org.example;

import java.util.Collections;
import java.util.Map;

/**
 * Класс - выражение, представляющее вычитание двух подвыражений.
 */
public class Sub extends Expression {
    private final Expression left;
    private final Expression right;

    /**
     * Конструктор для создания операции вычитания.
     *
     * @param left левый операнд
     * @param right правый операнд
     */
    public Sub(Expression left, Expression right) {
        if (left == null || right == null) {
            throw new IllegalArgumentException("Операнды не могут быть null");
        }

        this.left = left;
        this.right = right;
    }

    /**
     * Возвращает строковое представление вычитания.
     *
     * @return строковое представление вычитания
     */
    @Override
    public String toString() {
        return "(" + left.toString() + "-" + right.toString() + ")";
    }

    /**
     * Вычисляет разность результатов вычисления левого и правого операндов.
     *
     * @param vars словарь, где ключ - имя переменной, а значение - ее числовое значение
     * @return результат вычитания
     */
    @Override
    public int eval(Map<String, Integer> vars) {
        return left.eval(vars) - right.eval(vars);
    }

    /**
     * Вычисляет производную разности.
     *
     * @param var имя переменной, по которой берется производная
     * @return выражение - производная разности
     */
    @Override
    public Expression derivative(String var) {
        return new Sub(left.derivative(var), right.derivative(var));
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
     * Проверяет, содержит ли разность переменные.
     *
     * @return true, если содержит; false - иначе
     */
    @Override
    public boolean hasVariables() {
        return left.hasVariables() || right.hasVariables();
    }

    /**
     * Упрощает разность двух выражений.
     * Если оба операнда не содержат переменных, вычисляет их разность.
     * Если правый операнд равен 0, возвращает левый операнд.
     * Если операнды равны, возвращает 0.
     *
     * @return упрощенное выражение
     */
    @Override
    public Expression simplify() {
        Expression l = left.simplify();
        Expression r = right.simplify();

        if (!l.hasVariables() && !r.hasVariables()) {
            return new Number(l.eval(Collections.emptyMap()) - r.eval(Collections.emptyMap()));
        }

        if (isZero(r)) {
            return l;
        }

        if (l.toString().equals(r.toString())) {
            return new Number(0);
        }

        return new Sub(l, r);
    }
}
