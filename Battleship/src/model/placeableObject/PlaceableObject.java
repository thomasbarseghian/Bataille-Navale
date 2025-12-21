package model.placeableObject;

public abstract class PlaceableObject {
    private int m_position;
    private PlaceableObjectType m_objectType;

    // The conqstructor takes the type
    public PlaceableObject(PlaceableObjectType type) {
        this.m_position = -1; // default
        this.m_objectType = type;
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