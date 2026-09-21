package org.example;

/**
 * Класс, представлющий карту.
 * Содержит информацию о масти и ранге карты.
 */
public class Card {
    private final Suit suit;
    private final Rank rank;

    /**
     * Конструктор картыю
     *
     * @param suit масть карты
     * @param rank ранг карты
     */
    public Card(Suit suit, Rank rank) {
        this.suit = suit;
        this.rank = rank;
    }

    /**
     * Возвращает масть карты.
     *
     * @return масть карты
     */
    public Suit getSuit() {
        return suit;
    }

    /**
     * Возвращет ранг карты.
     *
     * @return ранг карты
     */
    public Rank getRank() {
        return rank;
    }

    /**
     * Возвращает базовое значение картыю
     *
     * @return очки карты
     */
    public int getValue() {
        return rank.getValue();
    }

    @Override
    public String toString() {
        return suit.getDisplayName() + " " + rank.getDisplayName() + " (" + rank.getValue() + ")";
    }
}
