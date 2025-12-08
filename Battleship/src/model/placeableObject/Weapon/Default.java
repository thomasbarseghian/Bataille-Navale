package model.placeableObject.Weapon;

import model.grid.Grid;

public class Default extends Weapon{
    
    public Default()
    {

    }

    @Override
    public void use(Grid grid, int pos)
    {
        grid.hitTile(pos);
    }
}
