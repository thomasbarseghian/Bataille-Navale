package model.grid;

import model.placeableObject.PlaceableObject;

public class Tile {
    private Boolean m_isHit;
    private TileType m_tileType;
    private PlaceableObject m_object;
    public Tile(TileType type){
        this.m_tileType = type;
        this.m_isHit=false;
        this.m_object=null;
    }
    public void hit(){
        this.m_isHit=true;
    }
    public TileType getTileType(){
        return this.m_tileType;
    }
    public PlaceableObject getObject(){
        return this.m_object;
    }
    public void setObject(PlaceableObject object){
        this.m_object=object;
    }
    public boolean getIsHit(){
        return this.m_isHit;
    }
}
