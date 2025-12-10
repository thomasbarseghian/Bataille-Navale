package model.grid;

import model.placeableObject.PlaceableObject;
import model.placeableObject.Weapon.Weapon;
import model.placeableObject.ship.Ship;

import java.util.ArrayList;

public class Grid {
    private int  m_size;
    private ArrayList<Tile> m_grid;
    private ArrayList<GridObserver> m_gridObservers;
    public Grid(int size){
        this.m_size = size;
        this.m_grid = new ArrayList<Tile>();
        this.m_gridObservers = new ArrayList<GridObserver>();
    }
    public void hitTile(int pos){
        m_grid.get(pos).hit();
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

}
