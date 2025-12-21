package model.player;

import model.grid.Grid;
import model.placeableObject.PlaceableObject;
import model.placeableObject.Trap.Trap;
import model.placeableObject.Weapon.Default;
import model.placeableObject.Weapon.Weapon;
import model.placeableObject.Weapon.WeaponType;
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
        m_observers = new ArrayList<>();
        m_weaponStrategy = new Default();
    }

    public void placeObject(PlaceableObject object)
    {
        m_grid.putPlaceObjectInTile(object);
    }



    public abstract void placeShipFix();

    //public abstract void placeTrapFix();

    //public abstract void placeWeaponFix();


    public void setWeaponStrategy(Weapon weapon)
    {
        m_weaponStrategy = weapon;
        //NotifyInventoryChanged();
    }

    public boolean equipWeapon(WeaponType targetType) {

        // Cas spécial : Si on veut remettre l'arme par défaut
        if (targetType == WeaponType.DEFAULT) {
            m_weaponStrategy = new Default(); // Arme par défaut
            return true;
        }

        // Recherche dans l'inventaire pour les armes spéciales
        for (Weapon w : m_weapons) {
            // Comparaison d'Enum
            if (w.getType() == targetType) {
                setWeaponStrategy(w);
                return true; // Arme trouvée et équipée
            }
        }
        return false; // Le joueur ne possède pas cette arme
    }

    public void addWeapon(Weapon weapon)
    {
        m_weapons.add(weapon);
    }

    public void addTrap(Trap trap)
    {
        m_traps.add(trap);
    }

    //public void setIsTornadoed(boolean isAffected)
    //{
    //    m_isTornadoed = isAffected;
    //}

    public void attack(Player opponent, int pos) {
        Grid opponentGrid = opponent.getGrid();

        // 0. VALIDATION: Check if the tile has already been hit/explored.
        // We cannot attack the same spot twice (assuming standard rules).
        if (opponentGrid.isTileHit(pos)) {
            return; // Exit immediately, do not count this as a turn.
        }

        // 1. Notify the view that an action is happening at this position
        notifyAttack(pos, m_weaponStrategy);

        // 2. Tornado Logic (Scramble position if needed)
        // if (m_isTornadoed) {
        //     pos = scramblePosition(pos);
        // }

        // 3. LOGIC BRANCH: Check the Terrain Type (Land vs Water)
        // Assuming TileType is an Enum accessible via the Grid.
        if (opponentGrid.isLand(pos)) {

            // --- LAND LOGIC: LOOTING ---

            // Mark the tile as explored/hit so we don't loot it again
            opponentGrid.setTileHit(pos);

            // Retrieve the object on this land tile (if any)
            PlaceableObject foundObject = opponentGrid.getObjectAt(pos);

            // Check if we found a weapon to add to inventory
            if (foundObject != null) {
                Weapon foundWeapon = (Weapon) foundObject;
                addWeapon(foundWeapon);
            }
            // (Optional) Handle Traps on land here if necessary

        } else {

            // --- WATER LOGIC: SHOOTING ---

            // Execute the weapon strategy (Bomb, Missile, Default, etc.)
            // The weapon.use() method handles marking the grid as hit and checking for ships.
            m_weaponStrategy.use(opponentGrid, pos);

            // 4. Trap Logic (Triggered only on Water)
            // checkIfTrapTriggered(opponent, pos);
        }

        // 5. INVENTORY MANAGEMENT (Special Weapons)
        // If the current weapon is strictly a one-time use special weapon, remove it.
        if (m_weaponStrategy.getType() != WeaponType.DEFAULT) {
            m_weapons.remove(m_weaponStrategy); // Remove from inventory
            m_weaponStrategy = new Default(); // Switch back to Default weapon
        }
    }

    public void notifyAttack(int pos, Weapon weapon)
    {
        for (PlayerObserver observer : m_observers)
        {
            observer.updateAttack(pos, weapon);
        }
    }

    public void notifyShipPlaced(Ship ship)
    {
        for (PlayerObserver observer : m_observers)
        {
            observer.updateShips(ship);
        }
    }

    public String getName()
    {
        return m_name;
    }

    public Grid getGrid() {
        return m_grid;
    }

    public void addObserver(PlayerObserver observer)
    {
        m_observers.add(observer);
    }

    public boolean allShipsAreSunk()
    {
        for (Ship ship : m_ships)
        {
            if (ship.getHp() <= 0)
            {
                return false;
            }
        }
        return true;
    }

    // In class Player.java
    public Weapon getWeaponStrategy() {
        return m_weaponStrategy;
    }
}
