package model.player;

import model.placeableObject.Trap.Trap;
import model.placeableObject.Weapon.Weapon;
import model.placeableObject.ship.Ship;

public interface PlayerObserver {

    public void updateShips(Ship ship);
//    public void updateTraps(Trap trap);
//    public void updateWeapons(Weapon weapon);
    public void updateAttack(int pos, Weapon weapon);
}
