package controller;

import model.game.WeaponCallback;
import model.placeableObject.Weapon.Weapon;
import model.placeableObject.Weapon.WeaponType;
import model.player.Player;

public class HumanController extends AbstractPlayerController {

    /**
     * Called by GameController to unlock the UI for the user.
     */
    @Override
    public void startTurn() {
        // Logic to unlock the UI (View)
        // e.g., view.enableInteraction(true);
    }

    /**
     * Specific method for Human: Triggered by UI click on Grid.
     */
    public void handleTileClick(Player target, int pos) {

        // 1. Get current weapon strategy
        Weapon currentWeapon = m_player.getWeaponStrategy();

        // 2. Set THIS controller as the callback listener
        // (The method onAttackFinished() from AbstractController will be called)
        currentWeapon.setCallback(this);

        // 3. Execute attack
        m_player.attack(target, pos);
    }

    /**
     * Specific method for Human: Triggered by UI click on Weapon Button.
     */
    public void handleWeaponEquip(WeaponType weaponType) {
        boolean equipped = m_player.equipWeapon(weaponType);

        if (equipped) {
            // met à jour vue
        } else {
            // met à jour vue
        }
    }
}