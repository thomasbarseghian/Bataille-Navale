package model.player;

import model.grid.Grid;
import model.placeableObject.PlaceableObject;
import model.placeableObject.Trap.Trap;
import model.placeableObject.Weapon.Default;
import model.placeableObject.Weapon.Weapon;
import model.placeableObject.ship.Ship;

import java.util.ArrayList;
import static model.placeableObject.ship.Direction.*;


public abstract class Player {

    protected String m_name;
    protected Boolean m_isTornadoed;
    protected Grid m_grid;
    protected ArrayList<Ship> m_ships;
    protected ArrayList<Trap> m_traps;
    protected ArrayList<Weapon> m_weapons;
    protected Weapon m_weaponStrategy;
    protected ArrayList<PlayerObserver> m_observers;

    public Player(String name, Grid grid, ArrayList<Ship> ships)
    {
        m_name = name;
        m_isTornadoed = false;
        m_grid = grid;
        m_ships = ships;
        m_traps = new ArrayList<>();
        m_weapons = new ArrayList<>();
        m_weaponStrategy = new Default();
    }

    public void placeObject(PlaceableObject object)
    {
        m_grid.putPlaceObjectInTile(object);
    }



    public abstract void placeShipFix();

    public abstract void placeTrapFix();

    public abstract void placeWeaponFix();

    public void setWeaponStrategy(Weapon weapon)
    {
        m_weaponStrategy = weapon;
    }

    public void addWeapon(Weapon weapon)
    {
        m_weapons.add(weapon);
    }

    public void addTrap(Trap trap)
    {
        m_traps.add(trap);
    }

    public void setIsTornadoed(boolean isAffected)
    {
        m_isTornadoed = isAffected;
    }

    //A FAIRE /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void attack(Player oppenent, int pos, Weapon weapon)
    {
        Grid oponentGrid = oppenent.getGrid();
        // A FINIR
    }

    public String getName()
    {
        return m_name;
    }

    public Grid getGrid() {
        return m_grid;
    }
}
