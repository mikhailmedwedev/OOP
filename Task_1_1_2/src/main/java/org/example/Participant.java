package org.example;

public abstract class Participant {
    private final String name;
    private final Hand hand;

    public Participant(String name) {
        this.name = name;
        this.hand = new Hand();
    }

    public String getName() {
        return name;
    }

    public Hand getHand() {
        return hand;
    }

    public void receiveCard(Card card) {
        hand.addCard(card);
    }

    public int getScore() {
        return hand.getScore();
    }

    public boolean isBust() {
        return hand.isBust();
    }

    public void resetHand() {
        hand.clear();
    }

    @Override
    public String toString() {
        return name + ": " + hand.toString();
    }
}

