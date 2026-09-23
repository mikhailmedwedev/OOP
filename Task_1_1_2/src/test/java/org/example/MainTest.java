package org.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Тестовый класс.
 */
public class MainTest {

    @Test
    void standardScore() {
        Hand hand = new Hand();
        hand.addCard(new Card(Suit.HEARTS, Rank.TEN));
        hand.addCard(new Card(Suit.SPADES, Rank.SEVEN));

        assertEquals(17, hand.getScore());
    }

    @Test
    void aceAsEleven() {
        Hand hand = new Hand();
        hand.addCard(new Card(Suit.CLUBS, Rank.ACE));
        hand.addCard(new Card(Suit.DIAMONDS, Rank.EIGHT));

        assertEquals(19, hand.getScore());
    }

    @Test
    void aceRecalculationWhenBust() {
        Hand hand = new Hand();
        hand.addCard(new Card(Suit.CLUBS, Rank.ACE));
        hand.addCard(new Card(Suit.DIAMONDS, Rank.FOUR));
        hand.addCard(new Card(Suit.SPADES, Rank.KING));

        assertEquals(15, hand.getScore());
    }

    @Test
    void isBustWhenOver21() {
        Hand hand = new Hand();
        hand.addCard(new Card(Suit.CLUBS, Rank.TEN));
        hand.addCard(new Card(Suit.HEARTS, Rank.JACK));
        hand.addCard(new Card(Suit.SPADES, Rank.FIVE));

        assertTrue(hand.isBust());
    }

    @Test
    void dealerShouldHitUnder17() {
        Dealer dealer = new Dealer();
        dealer.receiveCard(new Card(Suit.SPADES, Rank.TEN));
        dealer.receiveCard(new Card(Suit.HEARTS, Rank.SIX));

        assertTrue(dealer.shouldHit());
    }

    @Test
    void dealerShouldStopWhenEqualsOrGreater17() {
        Dealer dealer = new Dealer();
        dealer.receiveCard(new Card(Suit.SPADES, Rank.TEN));
        dealer.receiveCard(new Card(Suit.HEARTS, Rank.SEVEN));

        assertFalse(dealer.shouldHit());
    }

    @Test
    void deckOperations() {
        Deck deck = new Deck();
        assertEquals(52, deck.cardsLeft());

        Card card = deck.drawCard();
        assertNotNull(card);
        assertEquals(51, deck.cardsLeft());

        deck.reset();
        assertEquals(52, deck.cardsLeft());
    }

    @Test
    void playerAndParticipantMethods() {
        Player player = new Player("Тестовый Игрок");
        assertEquals("Тестовый Игрок", player.getName());

        Card card = new Card(Suit.HEARTS, Rank.ACE);
        assertEquals(Suit.HEARTS, card.getSuit());
        assertEquals(Rank.ACE, card.getRank());
        assertEquals(11, card.getValue());

        player.receiveCard(card);
        assertFalse(player.getHand().getCards().isEmpty());
        assertEquals(11, player.getScore());

        player.resetHand();
        assertTrue(player.getHand().getCards().isEmpty());
    }

    @Test
    void handClearAndToString() {
        Hand hand = new Hand();
        hand.addCard(new Card(Suit.CLUBS, Rank.FIVE));
        assertNotNull(hand.toString());

        hand.clear();
        assertEquals(0, hand.getScore());
    }
}
