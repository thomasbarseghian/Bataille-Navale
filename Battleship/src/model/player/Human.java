package model.player;

import model.grid.Grid;
import model.placeableObject.ship.Ship;

import java.util.ArrayList;

import static model.placeableObject.ship.Direction.VERTICAL;

public class Human extends Player{
    public Human(String name, Grid grid, ArrayList<Ship> ships)
    {
        super(name, grid, ships);
    }

    @Override
    public void placeShipFix() {
        //Works if each Player has 5 ships
        Ship currentShip = m_ships.get(0);
        currentShip.setDirection(VERTICAL);
        currentShip.setPosition(0);
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
