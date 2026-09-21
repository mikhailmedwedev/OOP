package org.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

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
 }
