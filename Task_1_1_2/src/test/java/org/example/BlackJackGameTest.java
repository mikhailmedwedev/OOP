package org.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

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
    private Method playerTurnMethod;
    private Method dealerTurnMethod;
    private Method handleRoundResultMethod;
    private Method playRoundMethod;

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

        playerTurnMethod = BlackJackGame.class.getDeclaredMethod("playerTurn");
        playerTurnMethod.setAccessible(true);

        dealerTurnMethod = BlackJackGame.class.getDeclaredMethod("dealerTurn");
        dealerTurnMethod.setAccessible(true);

        handleRoundResultMethod = BlackJackGame.class
                .getDeclaredMethod("handleRoundResult", GameResult.class);
        handleRoundResultMethod.setAccessible(true);

        playRoundMethod = BlackJackGame.class.getDeclaredMethod("playRound");
        playRoundMethod.setAccessible(true);
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

    @Test
    void startAndExitImmediately() throws Exception {
        System.setIn(new ByteArrayInputStream("n\n".getBytes()));
        BlackJackGame gameWithInput = new BlackJackGame("Тест");
        gameWithInput.start();
    }

    @Test
    void playerTurnStandImmediately() throws Exception {
        System.setIn(new ByteArrayInputStream("0\n".getBytes()));
        BlackJackGame gameWithInput = new BlackJackGame("Тест");
        playerTurnMethod.invoke(gameWithInput);

        Field playerField = BlackJackGame.class.getDeclaredField("player");
        playerField.setAccessible(true);
        Player p = (Player) playerField.get(gameWithInput);
        assertEquals(0, p.getHand().getCards().size());
    }

    @Test
    void playerTurnHitThenStand() throws Exception {
        System.setIn(new ByteArrayInputStream("1\n0\n".getBytes()));
        BlackJackGame gameWithInput = new BlackJackGame("Тест");
        playerTurnMethod.invoke(gameWithInput);

        Field playerField = BlackJackGame.class.getDeclaredField("player");
        playerField.setAccessible(true);
        Player p = (Player) playerField.get(gameWithInput);
        assertEquals(1, p.getHand().getCards().size());
    }

    @Test
    void dealerTurnExecution() throws Exception {
        dealer.receiveCard(new Card(Suit.HEARTS, Rank.TEN));
        dealer.receiveCard(new Card(Suit.HEARTS, Rank.SIX));

        dealerTurnMethod.invoke(game);

        List<Card> cards = dealer.getHand().getCards();
        assertTrue(cards.size() > 2);
    }

    @Test
    void handleRoundResultsCoverage() throws Exception {
        for (GameResult result : GameResult.values()) {
            handleRoundResultMethod.invoke(game, result);
        }
    }

    @Test
    void playRoundWhenPlayerAndDealerStop() throws Exception {
        System.setIn(new ByteArrayInputStream("0\n".getBytes()));
        BlackJackGame gameWithInput = new BlackJackGame("Тест");

        Field deckField = BlackJackGame.class.getDeclaredField("deck");
        deckField.setAccessible(true);
        Deck d = (Deck) deckField.get(gameWithInput);

        while(d.drawCard().getRank() == Rank.ACE);

        playRoundMethod.invoke(gameWithInput);
    }
}
