package controller;

import model.game.WeaponCallback;
import model.grid.Grid;
import model.placeableObject.Weapon.Weapon;

public class AIController implements WeaponCallback {

    private Weapon myWeapon;
    private GameController gameController;
    private Grid grid; // The AI needs to know the grid to attack it

    // Setters to initialize data
    public void setGameController(GameController gc) { this.gameController = gc; }
    public void setGrid(Grid grid) { this.grid = grid; }
    public void setWeapon(Weapon weapon) { this.myWeapon = weapon; }

    /**
     * Triggered by GameController when it's time for the AI to play.
     * Renamed from 'playTurn' to 'startTurn' to match the call in GameController.
     */
    public void startTurn() {

        // AI sets itself as the listener to know when the attack finishes
        myWeapon.setCallback(this);

        // Simple logic: pick a random target
        int targetPos = (int)(Math.random() * 100);

        // Perform the attack
        myWeapon.use(this.grid, targetPos);
    }

    @Override
    public void onAttackFinished() {
        // The weapon animation/logic is done.

        // CORRECTION: We call endAiTurn(), NOT startPlayerTurn().
        // The AI simply reports "I am done". The GameController decides what happens next.
        gameController.endAiTurn();
    }
}