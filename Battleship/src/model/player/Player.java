package model.player;

import model.grid.Grid;
import model.placeableObject.PlaceableObject;
import model.placeableObject.Trap.Trap;
import model.placeableObject.Weapon.Default;
import model.placeableObject.Weapon.Weapon;
import model.placeableObject.Weapon.WeaponType;
import model.placeableObject.ship.Ship;

import java.util.ArrayList;


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

    public void setIsTornadoed(boolean isAffected)
    {
        m_isTornadoed = isAffected;
    }

    public void attack(Player opponent, int pos)
    {
        Grid opponentGrid = opponent.getGrid();

        // 1. Notifier la vue (MVC + Observer)
        notifyAttack(pos, m_weaponStrategy);

        // 2. Si le joueur est affecté par une tornade, scramble la position
        //if (m_isTornadoed)
        //{
            //IL FAUT UNE METHODE
        //}

        // 3. Appeler la stratégie de l’arme
        m_weaponStrategy.use(opponentGrid, pos);

        // 4. Vérifier si un trap a été touché
        //PlaceableObject obj = opponentGrid.getObjectAt(pos);
        //notifyAttack(pos, weapon, obj.getType());
        //checkIfTrapTriggered(opponent, pos);

        // 5. Verifications pour armes spéciale
        // Si l'arme n'est pas l'arme par défaut, on la retire de l'inventaire
        if (m_weaponStrategy.getType() != WeaponType.DEFAULT) {
            m_weapons.remove(m_weaponStrategy); // On l'enlève de l'inventaire
            m_weaponStrategy = new Default(); // Arme par défaut
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
            if (ship.getHp() > 0)
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

    public ArrayList<Ship> getShips() { return m_ships;
    }
}
