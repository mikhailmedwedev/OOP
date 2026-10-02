package org.example;

import java.util.Scanner;

/**
 * Управляющий класс игры.
 * Игровой цикл, взаимодействие с пользователем через консоль и подсчет очков.
 */
public class BlackJackGame {
    private final Deck deck;
    private final Player player;
    private final Dealer dealer;
    private final Scanner scanner;
    private int roundCount;
    private int playerScore;
    private int dealerScore;

    /**
     * Инициализация игры с указанным именем игрока.
     *
     * @param playerName имя игрока
     */
    public BlackJackGame(String playerName) {
        this.deck = new Deck();
        this.player = new Player(playerName);
        this.dealer = new Dealer();
        this.scanner = new Scanner(System.in);
        this.roundCount = 0;
        this.playerScore = 0;
        this.dealerScore = 0;
    }

    /**
     * Запускает главный цикл игры.
     */
    public void start() {
        System.out.print("=== Добро пожаловать в BlackJack! ===");
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

    /**
     * Один раунд игры: раздача карт, ход игрока, ход дилера и определение победителя.
     */
    private void playRound() {
        roundCount++;
        player.resetHand();
        dealer.resetHand();

        player.receiveCard(deck.drawCard());
        dealer.receiveCard(deck.drawCard());
        player.receiveCard(deck.drawCard());
        dealer.receiveCard(deck.drawCard());

        System.out.println("\nРаунд " + roundCount);
        System.out.println("Дилер раздал карты");
        printHandsStateWithHiddenCard();

        if (player.getScore() == 21 || dealer.getScore() == 21) {
            System.out.println();
            printHandsStateWithOpenCards();
            GameResult result = getGameResult(true);
            handleRoundResult(result);
            return;
        }

        playerTurn();

        if (!player.isBust()) {
            dealerTurn();
        }

        System.out.println();
        printHandsStateWithOpenCards();
        GameResult result = getGameResult(false);
        handleRoundResult(result);
    }

    /**
     * Ход игрока.
     */
    private void playerTurn() {
        System.out.println("\nВаш ход");
        System.out.println("-------");

        while (!player.isBust()) {
            System.out.println("Введите \"1\", чтобы взять карту, "
                    + "или \"0\", чтобы остановиться... ");
            String choice = scanner.nextLine().trim();

            if (choice.equals("1")) {
                Card drawnCard = deck.drawCard();
                player.receiveCard(drawnCard);

                System.out.println("Вы открыли карту " + drawnCard);
                printHandsStateWithHiddenCard();
                System.out.println();
            } else if (choice.equals("0")) {
                break;
            }
        }
    }

    /**
     * Ход дилера.
     */
    private void dealerTurn() {
        System.out.println("\nХод дилера");
        System.out.println("-------");

        Card hiddenCard = dealer.getHand().getCards().get(1);
        System.out.println("Дилер открывает закрытую карту " + hiddenCard);
        printHandsStateWithOpenCards();

        while (dealer.shouldHit()) {
            Card drawnCard = deck.drawCard();
            dealer.receiveCard(drawnCard);
            System.out.println("\nДилер открывает карту " + drawnCard);
            printHandsStateWithOpenCards();
        }
    }

    /**
     * Определяет результат раунда.
     *
     * @param isRightAfterDeal флаг, указывающий, вызвана ли проверка сразу после раздачи карт
     * @return исход раунда
     */
    private GameResult getGameResult(boolean isRightAfterDeal) {
        boolean playerHasBlackJack = player.getScore() == 21;
        boolean dealerHasBlackJack = dealer.getScore() == 21;

        if (isRightAfterDeal) {
            if (playerHasBlackJack && dealerHasBlackJack) {
                return GameResult.BOTH_BLACKJACK;
            } else if (playerHasBlackJack) {
                return GameResult.PLAYER_BLACKJACK;
            } else if (dealerHasBlackJack) {
                return GameResult.DEALER_BLACKJACK;
            }
        }

        if (player.isBust()) {
            return GameResult.PLAYER_BUST;
        }
        if (dealer.isBust()) {
            return GameResult.DEALER_BUST;
        }

        if (player.getScore() > dealer.getScore()) {
            return GameResult.PLAYER_WIN;
        } else if (dealer.getScore() > player.getScore()) {
            return GameResult.DEALER_WIN;
        } else {
            return GameResult.DRAW;
        }
    }

    /**
     * Обновляет счет игры и выводит сообщение о результате раунда.
     *
     * @param roundResult исход раунда
     */
    private void handleRoundResult(GameResult roundResult) {
        System.out.println();

        switch (roundResult) {
            case PLAYER_BLACKJACK -> {
                playerScore++;
                System.out.println("У вас блэкджек! Вы выиграли раунд! Счет "
                        + playerScore + ":" + dealerScore + ".");
            }
            case DEALER_BLACKJACK -> {
                dealerScore++;
                System.out.println("У дилера блэкджек! Дилер выиграл раунд. Счет "
                        + playerScore + ":" + dealerScore + ".");
            }
            case BOTH_BLACKJACK -> System.out.println("У обоих Блэкджек! Ничья! Счет "
                        + playerScore + ":" + dealerScore + ".");
            case PLAYER_BUST -> {
                dealerScore++;
                System.out.println("Перебор! Вы проиграли этот раунд. Счет "
                        + playerScore + ":" + dealerScore + ".");
            }
            case DEALER_BUST -> {
                playerScore++;
                System.out.println("У дилера перебор! Вы выиграли раунд! Счет "
                        + playerScore + ":" + dealerScore + ".");
            }
            case PLAYER_WIN -> {
                playerScore++;
                System.out.println("Вы выиграли раунд по очкам! Счет "
                        + playerScore + ":" + dealerScore + ".");
            }
            case DEALER_WIN -> {
                dealerScore++;
                System.out.println("Дилер выиграл раунд по очкам. Счет "
                        + playerScore + ":" + dealerScore + ".");
            }
            case DRAW -> System.out.println("Ничья! Счет "
                        + playerScore + ":" + dealerScore + ".");
        }
    }

    /**
     * Выводит текущий набор карт у участников со скрытой второй картой дилера.
     */
    private void printHandsStateWithHiddenCard() {
        System.out.println("    Ваши карты: " + player.getHand().getCards()
                + " => " + player.getScore());
        System.out.println("    Карты дилера: [" + dealer.getHand().getCards().get(0)
                + ", <закрытая карта>]");
    }

    /**
     * Выводит текущий набор карт у участников со всеми открытыми картами.
     */
    private void printHandsStateWithOpenCards() {
        System.out.println("    Ваши карты: " + player.getHand().getCards()
                + " => " + player.getScore());
        System.out.println("    Карты дилера: " + dealer.getHand().getCards()
                + " => " + dealer.getScore());
    }
}
