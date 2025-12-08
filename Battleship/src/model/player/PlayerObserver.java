package model.player;

import model.placeableObject.Trap.Trap;
import model.placeableObject.Weapon.Weapon;
import model.placeableObject.ship.Ship;

public interface PlayerObserver {

    public void updateShips(Ship[] ships);
    public void updateTraps(Trap[] traps);
    public void updateWeapons(Weapon[] weapons);
    public void updateAttack(int pos, Weapon weapon);
}
