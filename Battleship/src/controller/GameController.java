package controller;

import controller.PlayerController;
import controller.AIController;

public class GameController {

    private PlayerController m_player;
    private AIController m_ai;

    public GameController(PlayerController player, AIController ai) {
        this.m_player = player;
        this.m_ai = ai;
    }

    /**
     * Called by the PlayerController (via the WeaponCallback) when the player's action is fully complete.
     */
    public void endPlayerTurn() {
        // TODO: Check for victory/defeat conditions here
        // Triggers the AI logic
        m_ai.startTurn();
    }

    /**
     * Called by the AIController (via the WeaponCallback) when the AI's action is fully complete.
     */
    public void endAiTurn() {

        // Unlocks the UI or notifies the player it's their turn
        m_player.startTurn();
    }
}