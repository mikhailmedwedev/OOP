package org.example;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Абстрактный базовый класс для представления выражений.
 */
public abstract class Expression {

    /**
     * Возвращает строковое представление выражения.
     *
     * @return строковое представление выражения
     */
    @Override
    public abstract String toString();

    /**
     * Выводит строковое представление выражения.
     */
    public void print() {
        System.out.println(this.toString());
    }

    /**
     * Вычисляет значение выражения.
     *
     * @param vars словарь, где ключ - имя переменной, а значение - ее числовое значение
     * @return вычисленное значение выражения
     */
    public abstract int eval(Map<String, Integer> vars);

    /**
     * Вычисляет значение выражения строки с переменными.
     *
     * @param varsStr строка с переменными
     * @return вычисленное значение
     */
    public int eval(String varsStr) {
        return eval(parseVars(varsStr));
    }

    /**
     * Преобразует строку с переменными для словаря.
     *
     * @param varsStr строка с переменными
     * @return словарь с названием переменных и их значениями
     */
    private Map<String, Integer> parseVars(String varsStr) {
        if (varsStr == null || varsStr.isBlank()) {
            return Collections.emptyMap();
        }

        Map<String, Integer> vars = new HashMap<>();
        String[] pairs = varsStr.split(";");
        for (String pair : pairs) {
            String[] keyValue = pair.split("=", 2);
            if (keyValue.length == 2) {
                String varName = keyValue[0].trim();
                int value = Integer.parseInt(keyValue[1].trim());
                vars.put(varName, value);
            }
        }

        return vars;
    }

    /**
     * Вычисляет производную выражения по заданной переменной.
     *
     * @param var имя переменной, по которой берется производная
     * @return новое выражение - производная
     */
    public abstract Expression derivative(String var);

    /**
     * Проверяет, содержит ли выражение переменные.
     *
     * @return true, если содержит переменные; false - иначе
     */
    public abstract boolean hasVariables();


    /**
     * Упрощает выражение, не изменяя исходное выражение.
     *
     * @return упрощенное выражение
     */
    public abstract Expression simplify();
}
