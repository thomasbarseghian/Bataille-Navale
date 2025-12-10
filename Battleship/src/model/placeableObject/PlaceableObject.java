package model.placeableObject;

public abstract class PlaceableObject {
    int m_position; //-1 si pas de position
    PlaceableObjectType m_objectType;
    public PlaceableObject(){
        this.m_position=-1;
    }
    public int getPosition(){
        return this.m_position;
    }
    public void setPosition(int pos){
        this.m_position=pos;
    }
    public PlaceableObjectType getObjectType(){
        return this.m_objectType;
    }
}
