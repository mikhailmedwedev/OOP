package org.example;

/**
 * Класс для демонстрации работы выраженийю
 */
public class Main {

    /**
     * Точка входа в программу.
     *
     * @param args аргументы командной строки
     */
    public static void main(String[] args) {
        Expression e = new Add(new Number(3), new Mul(new Number(2), new Variable("x")));
        e.print();

        System.out.println();

        Expression de = e.derivative("x");
        de.print();

        System.out.println();

        Expression expr = new Add(new Number(3), new Mul(new Number(2),

                new Variable("x")));
        int result = expr.eval("x = 10; y = 13");
        System.out.println(result);
    }
}
