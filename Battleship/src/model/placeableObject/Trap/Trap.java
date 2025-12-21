package model.placeableObject.Trap;

import model.placeableObject.PlaceableObject;
import model.placeableObject.PlaceableObjectType;

public abstract class Trap extends PlaceableObject {
    private TrapType m_trapType;

    public Trap(TrapType type) {
        super(PlaceableObjectType.TRAP);
        this.m_trapType = type;
    }

    public TrapType getTrapType() {
        return m_trapType;
    }
}