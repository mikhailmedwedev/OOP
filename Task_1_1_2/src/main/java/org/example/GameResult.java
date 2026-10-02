package org.example;

/**
 * Возможные исходы раунда.
 */
public enum GameResult {
    PLAYER_BLACKJACK,
    DEALER_BLACKJACK,
    BOTH_BLACKJACK,
    PLAYER_BUST,
    DEALER_BUST,
    PLAYER_WIN,
    DEALER_WIN,
    DRAW
}
