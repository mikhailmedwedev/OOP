package org.example;

/**
 * Абстрактный класс, описывающий участника игры.
 */
public abstract class Participant {
    private final String name;
    private final Hand hand;

    /**
     * Конструктор участника.
     *
     * @param name имя участника
     */
    public Participant(String name) {
        this.name = name;
        this.hand = new Hand();
    }

    /**
     * Возвращает имя участника.
     *
     * @return имя
     */
    public String getName() {
        return name;
    }

    /**
     * Возвращает набор карт у участника.
     *
     * @return набор карт
     */
    public Hand getHand() {
        return hand;
    }

    /**
     * Добавление карты в набор карт участника.
     *
     * @param card карта для добавления
     */
    public void receiveCard(Card card) {
        hand.addCard(card);
    }

    /**
     * Возвращает текущее количество очков у участника.
     *
     * @return количество очков
     */
    public int getScore() {
        return hand.getScore();
    }

    /**
     * Проверяет, есть ли перебор у участника.
     *
     * @return true, если перебор; false, если перебора нет
     */
    public boolean isBust() {
        return hand.isBust();
    }

    /**
     * Сброс карт участника перед новым раундом.
     */
    public void resetHand() {
        hand.clear();
    }

    @Override
    public String toString() {
        return name + ": " + hand.toString();
    }
}

