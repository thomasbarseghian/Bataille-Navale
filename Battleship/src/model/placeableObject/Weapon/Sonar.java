package model.placeableObject.Weapon;

import model.grid.Grid;

public class Sonar extends Weapon {

    public Sonar() {
        super(WeaponType.SONAR);
    }

    @Override
    public void use(Grid grid, int pos) {
        // TODO: Implement the sonar logic

        notifyFinished();
    }
}