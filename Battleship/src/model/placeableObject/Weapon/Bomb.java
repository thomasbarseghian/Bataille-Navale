package model.placeableObject.Weapon;

import model.grid.Grid;

public class Bomb extends Weapon {

    public Bomb() {
        super(WeaponType.BOMB);
    }

    @Override
    public void use(Grid grid, int pos) {
        // TODO: Implémenter la logique de zone de la bombe (C1)
        // grid.hitTile(pos);
        // grid.hitTile(pos + 1); ...

        notifyFinished();
    }

    @Override
    public WeaponType getType()
    {
        return WeaponType.BOMB;
    }
}