package model.placeableObject.Trap;

import model.placeableObject.ship.*;

public class TrapFactory {
    public TrapFactory(){

    }
    public Trap createTrap(TrapType trapType){
        switch(trapType) {
            case BLACKHOLE:
                //return new Blackhole();
            case TORNADO:
                //return new Tornado();
            default:
                throw new IllegalArgumentException("Unsupported trap type: " + trapType);
        }
    }
}
