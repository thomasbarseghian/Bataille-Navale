package controller;

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

        // 1. Logic to choose weapon
        // Try to equip the BOMB from inventory
        boolean hasBomb = m_player.equipWeapon(WeaponType.BOMB);

        if (!hasBomb) {
            // If equip returned false, it means the AI doesn't have a bomb.
            // We ensure we are using the default weapon.
            m_player.equipWeapon(WeaponType.DEFAULT);
        }

        // 2. Logic to pick a target (Random for now)
        int gridWidth = 10;
        int targetPos = (int)(Math.random() * (gridWidth * gridWidth));

        // 3. Get the currently equipped weapon (Strategy pattern)
        Weapon currentWeapon = m_player.getWeaponStrategy();

        // 4. IMPORTANT: Set THIS controller as the callback listener
        // This ensures onAttackFinished() (in AbstractController) is called later
        currentWeapon.setCallback(this);

        // 5. Perform the attack
        m_player.attack(m_opponent, targetPos);
    }
}