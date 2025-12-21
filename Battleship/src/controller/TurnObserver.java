package controller;

import model.player.Player;

public interface TurnObserver {
    /**
     * Triggered when a controller finishes its turn logic.
     * @param player The player who just finished playing.
     */
    void onTurnEnded(Player player);
}