package org.example;

import java.io.IOException;

/**
 * Запускает поиск подстроки в файле.
 */
public class Main {

    /**
     * Выводит индексы найденных вхождений.
     *
     * @param args имя файла и искомая подстрока
     */
    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Не задано имя файла и искомая подстрока");
            return;
        }

        try {
            System.out.println(SubstringFinder.find(args[0], args[1]));
        } catch (IOException e) {
            System.err.println("Не удалось прочитать файл: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }
    }
}
