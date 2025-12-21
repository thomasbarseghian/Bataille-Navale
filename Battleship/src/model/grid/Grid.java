package model.grid;

import model.placeableObject.PlaceableObject;
import model.placeableObject.PlaceableObjectType;
import model.placeableObject.Weapon.Weapon;
import model.placeableObject.ship.Ship;

import java.util.ArrayList;

public class Grid {
    private int  m_size;
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
        if (tile.getIsHit()) {
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
}
