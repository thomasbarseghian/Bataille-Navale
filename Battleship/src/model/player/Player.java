package model.player;

import model.grid.Grid;
import model.placableObject.Trap.PlaceableObject;
import model.placableObject.Trap.Trap;
import model.placableObject.Weapon.Default;
import model.placableObject.Weapon.Weapon;
import model.placableObject.ship.Ship;

import java.util.ArrayList;

public abstract class Player {

    private String m_name;
    private Boolean m_isTornadoed;
    private Grid m_grid;
    private ArrayList<Ship> m_ships;
    private ArrayList<Trap> m_traps;
    private ArrayList<Weapon> m_weapons;
    private Weapon m_weaponStrategy;


    public Player(String name, Grid grid)
    {
        m_name = name;
        m_isTornadoed = false;
        m_grid = grid;
        m_ships = new ArrayList<>();
        m_traps = new ArrayList<>();
        m_weapons = new ArrayList<>();
        m_weaponStrategy = new Default();
    }

    public void placeObject(PlaceableObject object)
    {
        m_grid.putPlaceObjectInTile(object);
    }

    public String getName()
    {
        return m_name;
    }
}
