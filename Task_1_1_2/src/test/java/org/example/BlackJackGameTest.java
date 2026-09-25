package org.example;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Тесты логики определения результатов раунда.
 */
class BlackJackGameTest {

    private BlackJackGame game;
    private Player player;
    private Dealer dealer;
    private Method getGameResultMethod;

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
        player.receiveCard(new Card(Suit.HEARTS, Rank.FIVE)); // 25 очков

        assertEquals(GameResult.PLAYER_BUST, invokeGetGameResult(false));
    }

    @Test
    void dealerBust() throws Exception {
        player.receiveCard(new Card(Suit.HEARTS, Rank.TEN));
        player.receiveCard(new Card(Suit.HEARTS, Rank.EIGHT)); // 18 очков

        dealer.receiveCard(new Card(Suit.HEARTS, Rank.TEN));
        dealer.receiveCard(new Card(Suit.HEARTS, Rank.JACK));
        dealer.receiveCard(new Card(Suit.HEARTS, Rank.FIVE)); // 25 очков

        assertEquals(GameResult.DEALER_BUST, invokeGetGameResult(false));
    }

    @Test
    void playerWinByPoints() throws Exception {
        player.receiveCard(new Card(Suit.HEARTS, Rank.TEN));
        player.receiveCard(new Card(Suit.HEARTS, Rank.NINE)); // 19 очков

        dealer.receiveCard(new Card(Suit.HEARTS, Rank.TEN));
        dealer.receiveCard(new Card(Suit.HEARTS, Rank.SEVEN)); // 17 очков

        assertEquals(GameResult.PLAYER_WIN, invokeGetGameResult(false));
    }

    @Test
    void dealerWinByPoints() throws Exception {
        player.receiveCard(new Card(Suit.HEARTS, Rank.TEN));
        player.receiveCard(new Card(Suit.HEARTS, Rank.SEVEN)); // 17 очков

        dealer.receiveCard(new Card(Suit.HEARTS, Rank.TEN));
        dealer.receiveCard(new Card(Suit.HEARTS, Rank.NINE)); // 19 очков

        assertEquals(GameResult.DEALER_WIN, invokeGetGameResult(false));
    }

    @Test
    void drawByPoints() throws Exception {
        player.receiveCard(new Card(Suit.HEARTS, Rank.TEN));
        player.receiveCard(new Card(Suit.HEARTS, Rank.EIGHT)); // 18 очков

        dealer.receiveCard(new Card(Suit.SPADES, Rank.TEN));
        dealer.receiveCard(new Card(Suit.SPADES, Rank.EIGHT)); // 18 очков

        assertEquals(GameResult.DRAW, invokeGetGameResult(false));
    }
}
