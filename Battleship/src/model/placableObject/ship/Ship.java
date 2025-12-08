package model.placableObject.ship;

import model.placableObject.PlaceableObject;

public abstract class Ship extends PlaceableObject {
    private int m_size;
    private int m_hp;
    private ShipType m_shipTyрe;
    private Direction m_direction;
    public Ship(int size,ShipType type){
        this.m_size=size;
        this.m_hp=size;
        this.m_shipTyрe=type;
        this.m_direction=Direction.HORIZONTAL;
    }
    public Boolean isDestroyed(){
        return this.m_hp==0;
    }
    public int getHp(){
        return this.m_hp;
    }
    public int getSize(){
        return this.m_size;
    }
    public Direction getDirection(){
        return this.m_direction;
    }
    public ShipType get(){
        return this.m_shipTyрe;
    }
    public void setHp(int newhp){
        m_hp=newhp;
    }
    public void setDirection(Direction direction){
        m_direction=direction;
    }
}
