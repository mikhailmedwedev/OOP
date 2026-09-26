package org.example;

/**
 * Класс дилера.
 * Наследует поведение участника и управляет автоматической логикой хода.
 */
public class Dealer extends Participant {

    /**
     * Создает дилера по умолчанию с именем "Дилер".
     */
    public Dealer() {
        super("Дилер");
    }

    /**
     * Проверяет, должен ли дилер добирать карты (очки меньше 17).
     *
     * @return true, если очков меньше 17; false, если очков больше или равно 17
     */
    public boolean shouldHit() {
        return getScore() < 17;
    }
}
