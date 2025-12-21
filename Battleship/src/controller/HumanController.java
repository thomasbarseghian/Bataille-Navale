package controller;

import model.game.WeaponCallback;
import model.placeableObject.Weapon.Weapon;
import model.placeableObject.Weapon.WeaponType;
import model.player.Player;

public class HumanController extends AbstractPlayerController {

    private GameController m_gameController;

    public void setGameController(GameController gameController) {
        this.m_gameController = gameController;
    }
    /**
     * Called by GameController to unlock the UI for the user.
     */
    @Override
    public void startTurn() {
        // Logic to unlock the UI (View) can be added here if needed
    }

    /**
     * Specific method for Human: Triggered by UI click on Grid.
     */
    public void handleTileClick(Player target, int pos) {

        // 1. Get current weapon strategy
        Weapon currentWeapon = m_player.getWeaponStrategy();

        boolean isHit = (target.getGrid().getObjectAt(pos) != null);
        // 2. Set THIS controller as the callback listener
        // (The method onAttackFinished() from AbstractController will be called)
        currentWeapon.setCallback(this);

        // 3. Execute attack
        m_player.attack(target, pos);
        if (m_gameController != null) {
            m_gameController.notifyAttackResult(m_player, pos, currentWeapon.getType(), isHit);
        }
    }

    /**
     * Optional wrapper if you want to encapsulate logic,
     * currently BattleController calls m_player.equipWeapon() directly via getPlayer().
     */
    public void handleWeaponEquip(WeaponType weaponType) {
        // Not used directly in the new logic, but kept for compatibility
        m_player.equipWeapon(weaponType);
    }
}