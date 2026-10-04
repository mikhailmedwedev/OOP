package org.example;

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
}
