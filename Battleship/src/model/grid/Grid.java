package model.grid;

import model.placableObject.Trap.PlaceableObject;

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
    public void placeObject(PlaceableObject object){
        //m_grid.get()
    }
    public void addObserver(GridObserver obs){
        m_gridObservers.add(obs);
    }
    public void removeObserver(GridObserver obs){
        m_gridObservers.remove(obs);
    }
    public void notifyTileHit(int pos){
        m_gridObservers.forEach((obs)->{
            obs.updateTileHit(pos);
                }
        );
    }

}
