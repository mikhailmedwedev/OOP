package org.example;

/**
 * Ранги карт.
 */
public enum Rank {
    TWO("Двойка", 2),
    THREE("Тройка", 3),
    FOUR("Четверка", 4),
    FIVE("Пятерка", 5),
    SIX("Шестерка", 6),
    SEVEN("Семерка", 7),
    EIGHT("Восьмерка", 8),
    NINE("Девятка", 9),
    TEN("Десятка", 10),
    JACK("Валет", 10),
    QUEEN("Дама", 10),
    KING("Король", 10),
    ACE("Туз", 11);

    private final String displayName;
    private final int value;

    /**
     * Конструктор ранга карты.
     *
     * @param displayName ранг карты на русском
     * @param value базовое количество очков
     */
    Rank(String displayName, int value) {
        this.displayName = displayName;
        this.value = value;
    }

    /**
     * Возвращает отображаемое название ранга.
     *
     * @return название ранга
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * Возвращает базовое значение ранга.
     *
     * @return очки
     */
    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        return displayName;
    }
}

