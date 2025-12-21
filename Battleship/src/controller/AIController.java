package controller;

import model.game.WeaponCallback;
import model.player.Player; // Import Player
import model.placeableObject.Weapon.Weapon;

public class AIController implements WeaponCallback {

    private GameController gameController;
    private Player m_ai; // The AI Player Model
    private Player m_opponent; // The Human Player Model (Target)

    // Setters to initialize data
    public void setGameController(GameController gc) { this.gameController = gc; }

    // We inject the models here
    public void setPlayers(Player aiPlayer, Player humanPlayer) {
        this.m_ai = aiPlayer;
        this.m_opponent = humanPlayer;
    }

    // Helper for GameController to retrieve the model
    public Player getPlayer() { return m_ai; }

    /**
     * Triggered by GameController when it's time for the AI to play.
     */
    public void startTurn() {

        // 1. Logic to pick a target (Random for now)
        // Ensure we pick a valid spot on the opponent's grid
        int gridWidth = 10; // The grid always has a width of 10
        int targetPos = (int)(Math.random() * (gridWidth * gridWidth));

        // 2. Logic to choose a weapon (AI logic)
        // For now, AI uses whatever is currently equipped (Default)
        Weapon currentWeapon = m_ai.getWeaponStrategy(); // You might need a getter in Player

        // 3. IMPORTANT: The AI sets itself as the listener for the weapon
        // This ensures onAttackFinished() is called here when the weapon is done
        currentWeapon.setCallback(this);

        // 4. Perform the attack using the Player Model
        // The Player model handles inventory removal, notifications, etc.
        m_ai.attack(m_opponent, targetPos);
    }

    @Override
    public void onAttackFinished() {
        // The weapon animation/logic is done.
        // Notify GameController that AI turn is over.
        gameController.endAiTurn();
    }
}