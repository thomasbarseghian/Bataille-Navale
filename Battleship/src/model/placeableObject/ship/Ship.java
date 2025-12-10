package model.placeableObject.ship;

import model.placeableObject.PlaceableObject;

import java.util.ArrayList;

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
    public ShipType getShipType(){
        return this.m_shipTyрe;
    }
    public void setHp(int newhp){
        m_hp=newhp;
    }
    public void setDirection(Direction direction){
        m_direction=direction;
    }
    public void hit(){
        m_hp--;
    }
    public ArrayList<Integer> getAllPositions(){
        int pos = this.getPosition();
        ArrayList<Integer> AllPositions = new ArrayList<>();
        if(m_direction==Direction.HORIZONTAL){
            for (int i = 0; i < m_size; i++) {
                AllPositions.add(pos+i);
            }
        }
        else{
            for (int i = 0; i < m_size; i++) {
                AllPositions.add(pos+i*10);
            }
        }
        return AllPositions;
    }
}
