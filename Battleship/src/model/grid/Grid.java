package model.grid;

import model.placeableObject.PlaceableObject;
import model.placeableObject.PlaceableObjectType;
import model.placeableObject.Weapon.Weapon;
import model.placeableObject.ship.Ship;

import java.util.ArrayList;

public class Grid {
    private int m_size;
    private ArrayList<Tile> m_grid;
    private ArrayList<GridObserver> m_gridObservers;
    public Grid(int size) {
        this.m_size = size;
        this.m_grid = new ArrayList<Tile>();
        this.m_gridObservers = new ArrayList<GridObserver>();

        int islandStart = (size - 4) / 2;
        int islandEnd = islandStart + 4;

        for (int i = 0; i < size * size; i++) {
            int row = i / size;
            int col = i % size;

            TileType type = TileType.WATER;

            if (row >= islandStart && row < islandEnd &&
                    col >= islandStart && col < islandEnd) {
                type = TileType.LAND;
            }

            m_grid.add(new Tile(type));
        }
    }
    public void hitTile(int pos){
        Tile tile = m_grid.get(pos);

        // Prevent hitting the same spot twice (optional safety)
        if (tile.isHit()) {
            return;
        }

        // 1. Mark the tile visually as hit
        tile.hit();

        // 2. LOGIC FIX: Damage the ship!
        if (!tile.isEmpty()) {
            PlaceableObject obj = tile.getObject();

            // Check if it is a Ship and decrease HP
            if (obj.getObjectType() == PlaceableObjectType.SHIP) {
                ((Ship) obj).hit();
            }
        }

        // 3. Notify View
        notifyTileHit(pos);
    }
    public void putPlaceObjectInTile(PlaceableObject object){
        int pos = object.getPosition();
        switch(object.getObjectType()) {
            case SHIP:
                ArrayList<Integer> allpositions = ((Ship)object).getAllPositions();
                allpositions.forEach((position)->{
                            m_grid.get(position).setObject(object);
                        }
                );
                break;
            case TRAP:
                // no trap for the moment : m_grid.get(object.getPosition()).setObject(object);
            case WEAPON:
                m_grid.get(pos).setObject(object);
            default:
                break;
        }
    }

    public PlaceableObject attackAt(int pos, Weapon weapon) {
        weapon.use(this, pos);        // Grid applique l’effet
        return m_grid.get(pos).getObject(); // Retourne ce qui est sur la case
    }

    public void addObserver(GridObserver obs){
        m_gridObservers.add(obs);
    }
    public void notifyTileHit(int pos){
        m_gridObservers.forEach((obs)->{
            obs.updateTileHit(pos);
                }
        );
    }

    public Tile getTile(int pos)
    {
        return m_grid.get(pos);
    }

    public int getSize() {
        return m_size;
    }

    /**
     * Checks if the tile at the given position has already been hit.
     */
    public boolean isTileHit(int pos) {
        if (isValidPos(pos)) {
            return m_grid.get(pos).isHit(); // Appelle la méthode de Tile
        }
        return false; // Ou throw exception
    }

    /**
     * Checks if the tile is LAND.
     */
    public boolean isLand(int pos) {
        return m_grid.get(pos).getTileType() == TileType.LAND;
    }

    /**
     * Retrieves the object on the tile (Ship, Weapon, etc.).
     */
    public PlaceableObject getObjectAt(int pos) {
        if (isValidPos(pos)) {
            return m_grid.get(pos).getObject();
        }
        return null;
    }

    /**
     * Marks a tile as hit and notifies observers.
     */
    public void setTileHit(int pos) {
        if (isValidPos(pos)) {
            Tile tile = m_grid.get(pos);

            // Only notify if the state actually changes or if it's a fresh hit
            if (!tile.isHit()) {
                tile.hit(); // Change l'état interne de la Tile
                notifyTileHit(pos); // La Grid gère la notification (MVC)
            }
        }
    }

    // --- HELPER ---

    // Sécurité pour éviter les IndexOutOfBoundsException
    private boolean isValidPos(int pos) {
        return pos >= 0 && pos < m_size * m_size;
    }
    public void reset() {
        for (Tile tile : m_grid) {
            tile.reset();
        }
        // Important : On prévient la vue que tout est vide (pour effacer les couleurs)
        for (int i = 0; i < m_size * m_size; i++) {
            notifyTileHit(i); //do this in order to refresh the view
        }
    }
}
