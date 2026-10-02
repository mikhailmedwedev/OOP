package org.example;

/**
 * Точка входа в программу.
 */
public class Main {

    /**
     * Запуск игры.
     *
     * @param args аргументы командной строки - не используются
     */
    public static void main(String[] args) {
        BlackJackGame game = new BlackJackGame("Игрок");
        game.start();
    }
}