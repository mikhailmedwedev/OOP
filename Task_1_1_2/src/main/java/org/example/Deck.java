package org.example;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Класс, представляющий колоду карт.
 */
public class Deck {
    private final List<Card> cards;

    /**
     * Создает и инициализирует колоду карт.
     */
    public Deck() {
        cards = new ArrayList<>();
        reset();
    }

    /**
     * Заполняет колоду всеми картами.
     */
    public final void reset() {
        cards.clear();
        for (Suit suit : Suit.values()) {
            for (Rank rank : Rank.values()) {
                cards.add(new Card(suit, rank));
            }
        }
    }

    /**
     * Перемешивание карт в колоде.
     */
    public void shuffle() {
        Collections.shuffle(cards);
    }

    /**
     * Достает верхнюю карту из колоды.
     * Если колода пуста, то пересоздает ее и перемешивает.
     *
     * @return вытянутая карта
     */
    public Card drawCard() {
        if (cards.isEmpty()) {
            reset();
            shuffle();
        }

        return cards.remove(cards.size() - 1);
    }

    /**
     * Возвращает оставшееся количество карт в колоде.
     *
     * @return число карт
     */
    public int cardsLeft() {
        return cards.size();
    }
}
