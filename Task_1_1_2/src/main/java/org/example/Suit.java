package org.example;

/**
 * Масти карт.
 */
public enum Suit {
    SPADES("Пики"), HEARTS("Червы"),
    DIAMONDS("Буби"), CLUBS("Трефы");

    private final String displayName;

    /**
     * Конструктор масти карт.
     *
     * @param displayName название масти на русском
     */
    Suit(String displayName) {
        this.displayName = displayName;
    }

    /**
     * Возвращает отображаемое название масти.
     *
     * @return название масти
     */
    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
