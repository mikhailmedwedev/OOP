package org.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Тесты для Player и Dealer.
 */
class ParticipantsTest {

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
