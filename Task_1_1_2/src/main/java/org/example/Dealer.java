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
     * Проверяет, должен ли дилер добирать карты (очки < 17).
     *
     * @return true, если очков < 17; false, если очков >= 17
     */
    public boolean shouldHit() {
        return getScore() < 17;
    }
}
