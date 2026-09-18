package org.example;

public class Dealer extends Participant {
    public Dealer() {
        super("Дилер");
    }

    public boolean shouldHit() {
        return getScore() < 17;
    }
}
