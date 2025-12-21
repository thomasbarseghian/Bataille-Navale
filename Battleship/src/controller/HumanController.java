package controller;

import model.game.WeaponCallback;
import model.placeableObject.Weapon.Weapon;
import model.placeableObject.Weapon.WeaponType;
import model.player.Player;

public class HumanController implements WeaponCallback {

    private GameController gameController;
    private Player m_player; // The Human Player Model

    // Setters
    public void setGameController(GameController gc) { this.gameController = gc; }
    public void setPlayer(Player p) { this.m_player = p; }

    public Player getPlayer() { return m_player; }

    /**
     * Called by GameController to unlock the UI for the user.
     */
    public void startTurn() {
        // Logic to unlock the UI (View)
        // e.g., view.enableInteraction(true);
        System.out.println("It's your turn, human!");
    }

    /**
     * Called when the Human clicks a tile on the View.
     * @param target The opponent player
     * @param pos The position index
     */
    public void handleTileClick(Player target, int pos) {

        // 1. Get current weapon
        Weapon currentWeapon = m_player.getWeaponStrategy(); // Need getter in Player

        // 2. Set callback to THIS controller to handle end of turn
        currentWeapon.setCallback(this);

        // 3. Attack
        m_player.attack(target, pos);
    }

    /**
     * Called when Human clicks a weapon button in UI.
     */
    public void handleWeaponEquip(WeaponType weaponType) {
        boolean equipped = m_player.equipWeapon(weaponType);
        if(equipped) {
            System.out.println("Weapon equipped: " + weaponType);
        } else {
            System.out.println("You don't have that weapon!");
        }
    }

    @Override
    public void onAttackFinished() {
        // Lock UI if necessary
        // view.enableInteraction(false);

        // Notify GameController that Human turn is over
        gameController.endHumanTurn();
    }

}