package controller;

import model.game.WeaponCallback;
import model.grid.Grid;
import model.placeableObject.Weapon.Weapon;

public class PlayerController implements WeaponCallback {

    private Weapon currentWeapon;
    private GameController gameController;

    // State to check if the player is allowed to play
    private boolean isTurnActive = false;

    // Setter for dependencies
    public void setGameController(GameController gameController) {
        this.gameController = gameController;
    }

    public void setCurrentWeapon(Weapon weapon) {
        this.currentWeapon = weapon;
    }

    /**
     * Triggered by GameController when the player's turn begins.
     * Unlike AI, we don't fire immediately. We just unlock the controls.
     */
    public void startTurn() {
        System.out.println(">> PLAYER TURN START: Input unlocked.");

        // 1. Allow the player to click
        this.isTurnActive = true;

        // TODO: Update UI here (e.g., enable buttons, show "YOUR TURN" text)
    }

    /**
     * This method is called by the UI (e.g., MouseListener on the Grid).
     */
    public void playerAction(Grid grid, int targetPos) {
        // 1. Security Check: Ignore clicks if it's not my turn
        if (!isTurnActive) {
            System.out.println("Ignored action: It is not your turn.");
            return;
        }

        // 2. Lock the turn immediately to prevent double-clicking or spamming
        this.isTurnActive = false;

        // TODO: Update UI here (e.g., disable buttons to show processing)

        // 3. Launch the attack logic
        if (currentWeapon != null) {
            currentWeapon.setCallback(this); // "Notify me when done"
            currentWeapon.use(grid, targetPos);
        }
    }

    @Override
    public void onAttackFinished() {
        System.out.println(">> PLAYER ACTION FINISHED.");

        // The animation is done, we hand over control to the GameController
        if (gameController != null) {
            gameController.endPlayerTurn();
        }
    }
}