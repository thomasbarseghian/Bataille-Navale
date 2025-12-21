package model.placeableObject.ship;

import model.placeableObject.PlaceableObject;
import model.placeableObject.PlaceableObjectType;

import java.util.ArrayList;

public abstract class Ship extends PlaceableObject {
    private int m_size;
    private int m_hp;
    private ShipType m_shipType;
    private Direction m_direction;

    public Ship(int size, ShipType type) {
        // C'EST ICI QUE C'EST PROPRE :
        // On appelle le constructeur du parent en lui donnant le type SHIP
        super(PlaceableObjectType.SHIP);

        this.m_size = size;
        this.m_hp = size;
        this.m_shipType = type;
        this.m_direction = Direction.HORIZONTAL;
    }

    // ... Le reste de tes méthodes (isDestroyed, getters, etc.) ne change pas ...
    public Boolean isDestroyed(){ return this.m_hp==0; }
    public int getHp(){ return this.m_hp; }
    public int getSize(){ return this.m_size; }
    public Direction getDirection(){ return this.m_direction; }
    public ShipType getShipType(){ return this.m_shipType; }
    public void setHp(int newhp){ m_hp=newhp; }
    public void setDirection(Direction direction){ m_direction=direction; }
    public void hit(){ m_hp--; }

    public ArrayList<Integer> getAllPositions(){
        int pos = this.getPosition(); // On utilise le getter public du parent
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
    public void reset() {
        this.m_hp = this.m_size;
    }
}