package model.placeableObject.Weapon;

import model.game.WeaponCallback;
import model.grid.Grid;
import model.placeableObject.PlaceableObject;
import model.placeableObject.PlaceableObjectType;

public abstract class Weapon extends PlaceableObject
{
    /**
     * The listener waiting for the weapon execution to finish.
     * Usually the Controller (Player or AI).
     * It is a single reference (Callback pattern).
     */
    protected WeaponCallback callback;
    private WeaponType m_weaponType;
    public Weapon(WeaponType type) {
        super(PlaceableObjectType.WEAPON); // On dit au parent : "Je suis une WEAPON"
        this.m_weaponType = type;
    }

    /**
     * Abstract method to define the weapon's behavior on the grid.
     * @param grid The game grid.
     * @param pos The target position index.
     */
    public abstract void use(Grid grid, int pos);

    /**
     * Assigns the listener that will handle the end of the turn/action.
     * @param callback The controller implementing the interface.
     */
    public void setCallback(WeaponCallback callback) {
        this.callback = callback;
    }

    /**
     * Protected helper method to signal "Action Complete".
     * Must be called by the concrete Weapon implementation (e.g., Default)
     * when the logic (and potential animations) are done.
     */
    protected void notifyFinished() {
        if (this.callback != null) {
            this.callback.onAttackFinished();
        }
    }

    // Force chaque sous-classe à déclarer son type
    public abstract WeaponType getType();

}
