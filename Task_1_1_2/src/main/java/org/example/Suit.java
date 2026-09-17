package org.example;

public enum Suit {
    SPADES("Пиковая"), HEARTS("Червы"),
    DIAMONDS("Бубновый"), CLUBS("Трефы");

    private final String displayName;
    Suit(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
