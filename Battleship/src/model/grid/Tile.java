package model.grid;

import model.placeableObject.PlaceableObject;

public class Tile {
    private boolean m_isHit;
    private TileType m_tileType;
    private PlaceableObject m_object;

    public Tile(TileType type) {
        this.m_tileType = type;
        this.m_isHit = false;
        this.m_object = null;
    }

    // --- ACTIONS ---

    public void hit() {
        this.m_isHit = true;
    }

    // --- GETTERS ---

    // Standard Java convention: boolean getters start with 'is'
    public boolean isHit() {
        return this.m_isHit;
    }

    // Helper method to check type directly
    public boolean isLand() {
        return this.m_tileType == TileType.LAND;
    }

    public TileType getTileType() {
        return this.m_tileType;
    }

    public PlaceableObject getObject() {
        return this.m_object;
    }

    public void setObject(PlaceableObject object) {
        this.m_object = object;
    }
    public boolean isEmpty() {
        if(m_object!=null){
            return false;
        }
        return true;
    }
}