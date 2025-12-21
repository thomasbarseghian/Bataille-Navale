package model.placeableObject;

public abstract class PlaceableObject {
    // 1. On met tout en private !
    private int m_position;
    private PlaceableObjectType m_objectType;

    // 2. Le constructeur demande le type
    public PlaceableObject(PlaceableObjectType type) {
        this.m_position = -1; // Par défaut
        this.m_objectType = type; // On initialise l'attribut privé ici
    }

    public int getPosition() {
        return this.m_position;
    }

    public void setPosition(int pos) {
        this.m_position = pos;
    }

    public PlaceableObjectType getObjectType() {
        return this.m_objectType;
    }
}