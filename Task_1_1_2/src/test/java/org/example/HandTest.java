package org.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Тесты для класса Hand.
 */
class HandTest {

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
    void handClearAndToString() {
        Hand hand = new Hand();
        hand.addCard(new Card(Suit.CLUBS, Rank.FIVE));
        assertNotNull(hand.toString());

        hand.clear();
        assertEquals(0, hand.getScore());
    }

    @Test
    void getCardsIsUnmodifiable() {
        Hand hand = new Hand();
        hand.addCard(new Card(Suit.HEARTS, Rank.ACE));

        assertThrows(UnsupportedOperationException.class, () -> {
            hand.getCards().clear();
        });
    }
}
