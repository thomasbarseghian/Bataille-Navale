package model.placeableObject.Weapon;

import model.grid.Grid;

public class Default extends Weapon{
    
    public Default()
    {

    }

    @Override
    public void use(Grid grid, int pos)
    {
        // L'action pure (Gameplay)
        grid.hitTile(pos);

        // On signale que l'action est finie
        notifyFinished();
    }
}
