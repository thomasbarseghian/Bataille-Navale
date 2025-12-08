package model.player;

import model.grid.Grid;
import model.placeableObject.ship.Ship;

import java.util.ArrayList;

import static model.placeableObject.ship.Direction.VERTICAL;

public class Humain extends Player{
    public Humain(String name, Grid grid, ArrayList<Ship> ships)
    {
        super(name, grid, ships);
    }

    @Override
    public void placeShipFix()
    {
        //Works if each Player has 5 ships
        for (int i=0; i<5; i++)
        {
            Ship currentShip = m_ships.get(i);
            //currentShip.setDirection(VERTICAL);
            currentShip.setPosition(0);
        }
    }

    @Override
    public void placeTrapFix()
    {

    }

    @Override
    public void placeWeaponFix()
    {

    }
}
