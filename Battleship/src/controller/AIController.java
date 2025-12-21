package controller;

import model.grid.Grid;
import model.placeableObject.Weapon.Weapon;
import model.placeableObject.Weapon.WeaponType;
import model.player.Player;

public class AIController extends AbstractPlayerController {

    private Player m_opponent; // The AI needs to know who to attack

    public void setOpponent(Player opponent) {
        this.m_opponent = opponent;
    }

    /**
     * Triggered by GameController.
     * Logic: Try to use BOMB if available, otherwise DEFAULT.
     */
    @Override
    public void startTurn() {

        Grid opponentGrid = m_opponent.getGrid();
        int targetPos;
        int maxAttempts = 1000; // Safety break to avoid infinite loops if board is full

        // --- INTELLIGENT TARGETING LOGIC ---
        // We look for a valid target.
        // The loop continues as long as the picked tile is already hit.
        do {
            // Pick a random position between 0 and 99
            targetPos = (int)(Math.random() * (opponentGrid.getSize() * opponentGrid.getSize()));
            maxAttempts--;
        } while (opponentGrid.isTileHit(targetPos) && maxAttempts > 0);

        //

        // --- WEAPON SELECTION ---
        // Try to use BOMB if available, otherwise DEFAULT.
        boolean hasBomb = m_player.equipWeapon(WeaponType.BOMB);

        if (hasBomb) {
            m_player.equipWeapon(WeaponType.BOMB);
        } else {
            m_player.equipWeapon(WeaponType.DEFAULT);
        }

        // --- EXECUTION ---
        Weapon currentWeapon = m_player.getWeaponStrategy();
        currentWeapon.setCallback(this);

        // Perform the attack on the valid targetPos found above
        m_player.attack(m_opponent, targetPos);
    }
}