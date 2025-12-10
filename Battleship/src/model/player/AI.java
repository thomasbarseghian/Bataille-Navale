package model.player;

import model.grid.Grid;
import model.placeableObject.ship.Ship;

import java.util.ArrayList;

import static model.placeableObject.ship.Direction.HORIZONTAL;
import static model.placeableObject.ship.Direction.VERTICAL;

public class AI extends Player
{
    public AI(String name, Grid grid, ArrayList<Ship> ships)
    {
        super(name, grid, ships);
    }

    @Override
    public void placeShipFix() {
        //Works if each Player has 5 ships
        Ship currentShip = m_ships.get(0);
        currentShip.setDirection(VERTICAL);
        currentShip.setPosition(12);
        m_grid.putPlaceObjectInTile(currentShip);

        currentShip = m_ships.get(1);
        currentShip.setDirection(HORIZONTAL);
        currentShip.setPosition(34);
        m_grid.putPlaceObjectInTile(currentShip);

        currentShip = m_ships.get(2);
        currentShip.setDirection(VERTICAL);
        currentShip.setPosition(59);
        m_grid.putPlaceObjectInTile(currentShip);

        currentShip = m_ships.get(3);
        currentShip.setDirection(HORIZONTAL);
        currentShip.setPosition(92);
        m_grid.putPlaceObjectInTile(currentShip);

        currentShip = m_ships.get(4);
        currentShip.setDirection(VERTICAL);
        currentShip.setPosition(49);
        m_grid.putPlaceObjectInTile(currentShip);
    }

//    @Override
//    public void placeTrapFix()
//    {
//
//    }

//    @Override
//    public void placeWeaponFix()
//    {
//        Weapon currentWeapon = m_weapons.get(0);
//        currentWeapon.setPosition(53);
//        m_grid.putPlaceObjectInTile(currentWeapon);
//    }
}
