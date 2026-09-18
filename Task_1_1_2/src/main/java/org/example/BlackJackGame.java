package org.example;

import java.util.Locale;
import java.util.Scanner;

public class BlackJackGame {
    private final Deck deck;
    private final Player player;
    private final Dealer dealer;
    private final Scanner scanner;
    private int roundCount;

    public BlackJackGame(String playerName) {
        this.deck = new Deck();
        this.player = new Player(playerName);
        this.dealer = new Dealer();
        this.scanner = new Scanner(System.in);
        roundCount = 0;
    }

    public void start() {
        System.out.println("=== Добро пожаловать в BlackJack! ===");
        deck.shuffle();

        boolean keepPlaying = true;
        while (keepPlaying) {
            playRound();
            System.out.print("\nХотите сыграть ещё раз? (y/n): ");
            String input = scanner.nextLine().trim().toLowerCase();
            keepPlaying = input.equals("y");
        }

        System.out.println("Спасибо за игру!");
    }

    private void playRound() {
        roundCount++;
        player.resetHand();
        dealer.resetHand();

        player.receiveCard(deck.drawCard());
        dealer.receiveCard(deck.drawCard());
        player.receiveCard(deck.drawCard());
        dealer.receiveCard(deck.drawCard());

        System.out.println("\n---Раунд " + roundCount + " ---");
        System.out.println(player);
        System.out.println(dealer.getName() + " показывает: [" + dealer.getHand().getCards().get(0) + ", <скрытая карта>]");

        playerTurn();

        if (!player.isBust()) {
            dealerTurn();
        }

        determineWinner();
    }

    private void playerTurn() {
        while (!player.isBust()) {
            System.out.print("\nВведите \"1\", чтобы взять карту, или \"0\", чтобы остановиться: ");
            String choice = scanner.nextLine().trim();

            if (choice.equals("1")) {
                Card drawnCard = deck.drawCard();
                player.receiveCard(drawnCard);
                System.out.println("Вы вытянули: " + drawnCard);
                System.out.println(player);
            }
            else if (choice.equals("0")) {
                break;
            }

        }

        if (player.isBust()) {
            System.out.println("Перебор! Вы проиграли этот раунд.");
        }
    }

    private void dealerTurn() {
        System.out.println("\n--- Ход Дилера ---");
        System.out.println(dealer);

        while (dealer.shouldHit()) {
            Card drawnCard = deck.drawCard();
            dealer.receiveCard(drawnCard);
            System.out.println("Дилер открывает карту: " + drawnCard);
            System.out.println(dealer);
        }

        if (dealer.isBust()) {
            System.out.println("У дилера перебор");
        }
    }

    private void determineWinner() {
        System.out.println("\n=== Итоги раунда " + roundCount + " ===");
        System.out.println(player);
        System.out.println(dealer);

        int playerScore = player.getScore();
        int dealerScore = dealer.getScore();

        if (player.isBust()) {
            System.out.println("Победил Дилер!");
        }
        else if (dealer.isBust()) {
            System.out.println("Поздравляем! Вы победили!");
        }
        else if (playerScore > dealerScore) {
            System.out.println("Поздравляем! Вы победили по очкам!");
        }
        else if (dealerScore > playerScore) {
            System.out.println("Победил Дилер по очкам!");
        }
        else {
            System.out.println("Ничья!");
        }
    }
}
