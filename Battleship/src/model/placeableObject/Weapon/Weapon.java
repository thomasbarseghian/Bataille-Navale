package model.placeableObject.Weapon;

import model.grid.Grid;
import model.placeableObject.PlaceableObject;

public abstract class Weapon extends PlaceableObject
{
    public abstract void use(Grid grid, int pos);
}
