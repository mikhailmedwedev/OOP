package org.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

/**
 * Тесты для класса Deck.
 */
class DeckTest {
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
}
