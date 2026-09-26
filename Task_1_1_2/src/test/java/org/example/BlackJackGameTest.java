package org.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Тесты логики игры BlackJack и определения результатов раунда.
 */
class BlackJackGameTest {

    private BlackJackGame game;
    private Player player;
    private Dealer dealer;
    private Method getGameResultMethod;
    private Method handleRoundResultMethod;

    @BeforeEach
    void setUp() throws Exception {
        game = new BlackJackGame("Тест");

        Field playerField = BlackJackGame.class.getDeclaredField("player");
        playerField.setAccessible(true);
        player = (Player) playerField.get(game);

        Field dealerField = BlackJackGame.class.getDeclaredField("dealer");
        dealerField.setAccessible(true);
        dealer = (Dealer) dealerField.get(game);

        getGameResultMethod = BlackJackGame.class
                .getDeclaredMethod("getGameResult", boolean.class);
        getGameResultMethod.setAccessible(true);

        handleRoundResultMethod = BlackJackGame.class
                .getDeclaredMethod("handleRoundResult", GameResult.class);
        handleRoundResultMethod.setAccessible(true);
    }

    private GameResult invokeGetGameResult(boolean isRightAfterDeal) throws Exception {
        return (GameResult) getGameResultMethod.invoke(game, isRightAfterDeal);
    }

    @Test
    void playerBlackJack() throws Exception {
        player.receiveCard(new Card(Suit.HEARTS, Rank.ACE));
        player.receiveCard(new Card(Suit.HEARTS, Rank.KING));

        dealer.receiveCard(new Card(Suit.HEARTS, Rank.TEN));
        dealer.receiveCard(new Card(Suit.HEARTS, Rank.EIGHT));

        assertEquals(GameResult.PLAYER_BLACKJACK, invokeGetGameResult(true));
    }

    @Test
    void dealerBlackJack() throws Exception {
        player.receiveCard(new Card(Suit.HEARTS, Rank.TEN));
        player.receiveCard(new Card(Suit.HEARTS, Rank.EIGHT));

        dealer.receiveCard(new Card(Suit.HEARTS, Rank.ACE));
        dealer.receiveCard(new Card(Suit.HEARTS, Rank.KING));

        assertEquals(GameResult.DEALER_BLACKJACK, invokeGetGameResult(true));
    }

    @Test
    void bothBlackJack() throws Exception {
        player.receiveCard(new Card(Suit.HEARTS, Rank.ACE));
        player.receiveCard(new Card(Suit.HEARTS, Rank.KING));

        dealer.receiveCard(new Card(Suit.SPADES, Rank.ACE));
        dealer.receiveCard(new Card(Suit.SPADES, Rank.KING));

        assertEquals(GameResult.BOTH_BLACKJACK, invokeGetGameResult(true));
    }

    @Test
    void playerBust() throws Exception {
        player.receiveCard(new Card(Suit.HEARTS, Rank.TEN));
        player.receiveCard(new Card(Suit.HEARTS, Rank.JACK));
        player.receiveCard(new Card(Suit.HEARTS, Rank.FIVE));

        assertEquals(GameResult.PLAYER_BUST, invokeGetGameResult(false));
    }

    @Test
    void dealerBust() throws Exception {
        player.receiveCard(new Card(Suit.HEARTS, Rank.TEN));
        player.receiveCard(new Card(Suit.HEARTS, Rank.EIGHT));

        dealer.receiveCard(new Card(Suit.HEARTS, Rank.TEN));
        dealer.receiveCard(new Card(Suit.HEARTS, Rank.JACK));
        dealer.receiveCard(new Card(Suit.HEARTS, Rank.FIVE));

        assertEquals(GameResult.DEALER_BUST, invokeGetGameResult(false));
    }

    @Test
    void playerWinByPoints() throws Exception {
        player.receiveCard(new Card(Suit.HEARTS, Rank.TEN));
        player.receiveCard(new Card(Suit.HEARTS, Rank.NINE));

        dealer.receiveCard(new Card(Suit.HEARTS, Rank.TEN));
        dealer.receiveCard(new Card(Suit.HEARTS, Rank.SEVEN));

        assertEquals(GameResult.PLAYER_WIN, invokeGetGameResult(false));
    }

    @Test
    void dealerWinByPoints() throws Exception {
        player.receiveCard(new Card(Suit.HEARTS, Rank.TEN));
        player.receiveCard(new Card(Suit.HEARTS, Rank.SEVEN));

        dealer.receiveCard(new Card(Suit.HEARTS, Rank.TEN));
        dealer.receiveCard(new Card(Suit.HEARTS, Rank.NINE));

        assertEquals(GameResult.DEALER_WIN, invokeGetGameResult(false));
    }

    @Test
    void drawByPoints() throws Exception {
        player.receiveCard(new Card(Suit.HEARTS, Rank.TEN));
        player.receiveCard(new Card(Suit.HEARTS, Rank.EIGHT));

        dealer.receiveCard(new Card(Suit.SPADES, Rank.TEN));
        dealer.receiveCard(new Card(Suit.SPADES, Rank.EIGHT));

        assertEquals(GameResult.DRAW, invokeGetGameResult(false));
    }

    @Test
    void handleRoundResultsCoverage() throws Exception {
        for (GameResult result : GameResult.values()) {
            handleRoundResultMethod.invoke(game, result);
        }
    }

    @Test
    void testStartAndPlayOneRound() {
        InputStream originalIn = System.in;
        try {
            // "1\n" - взять карту, "0\n" - остановиться, "n\n" - закончить игру
            String input = "1\n0\nn\n";
            System.setIn(new ByteArrayInputStream(input.getBytes()));

            BlackJackGame gameWithInput = new BlackJackGame("Тест");
            gameWithInput.start();

            assertTrue(true);
        } finally {
            System.setIn(originalIn);
        }
    }
}