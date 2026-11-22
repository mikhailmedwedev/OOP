package org.example;

import java.io.IOException;
import java.util.List;

/**
 * Демонстрация примера из задания.
 */
public class Main {

    /**
     * Запускает демонстрацию поиска подстроки в файле.
     *
     * @param args аргументы командной строки
     */
    public static void main(String[] args) {
        String fileName = "input.txt";
        String searchString = "бра";

        try {
            List<Long> positions = FileSearcher.find(fileName, searchString);
            System.out.println("Искомая строка: " + searchString);
            System.out.println("Найденные позиции: " + positions);
        } catch (IOException e) {
            System.out.println("Ошибка при чтении файла: " + e.getMessage());
        }
    }
}