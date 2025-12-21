package model.placeableObject.Weapon;

import model.grid.Grid;

public class Default extends Weapon{
    
    public Default()
    {
        super(WeaponType.DEFAULT);
    }

    @Override
    public void use(Grid grid, int pos)
    {
        // The ation (Gameplay)
        grid.hitTile(pos);

        // We signal that the action is finished
        notifyFinished();
    }
}
