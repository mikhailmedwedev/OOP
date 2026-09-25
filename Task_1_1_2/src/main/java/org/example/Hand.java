package org.example;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Класс, представляющий набор карт на руках у участника.
 */
public class Hand {
    private final List<Card> cards;

    /**
     * Создает пустой набор карт.
     */
    public Hand() {
        cards = new ArrayList<>();
    }

    /**
     * Добавляет карты в набор карт.
     *
     * @param card карта для добавления
     */
    public void addCard(Card card) {
        cards.add(card);
    }

    /**
     * Возвращает неизменяемый список карт на руках.
     *
     * @return неизменяемый список карт на руках
     */
    public List<Card> getCards() {
        return Collections.unmodifiableList(cards);
    }

    /**
     * Рассчет суммарного количества очков в руке.
     * Гибкое значение Тузов (11 - без перебора, 1 при переборе).
     *
     * @return итоговая сумма очков
     */
    public int getScore() {
        int score = 0;
        int aceCount = 0;

        for (Card card : cards) {
            score += card.getValue();
            if (card.getRank() == Rank.ACE) {
                aceCount++;
            }
        }

        while (score > 21 && aceCount > 0) {
            score -= 10;
            aceCount--;
        }

        return score;
    }

    /**
     * Проверяем, превышает ли сумма очков 21.
     *
     * @return true, если перебор; false, если перебора нет
     */
    public boolean isBust() {
        return getScore() > 21;
    }

    /**
     * Очищает руку от всех карт.
     */
    public void clear() {
        cards.clear();
    }

    @Override
    public String toString() {
        return cards.toString() + " (Очки: " + getScore() + ")";
    }
}
