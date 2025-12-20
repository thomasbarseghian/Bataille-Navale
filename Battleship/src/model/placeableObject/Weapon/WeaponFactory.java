package model.placeableObject.Weapon;

public class WeaponFactory {
    public WeaponFactory(){

    }
    public Weapon createWeapon(WeaponType weaponType){
        switch(weaponType) {
            case BOMB:
                //return new Bomb();
            case SONAR:
                //return new Sonar();
            case DEFAULT:
                return new Default();
            default:
                throw new IllegalArgumentException("Unsupported weapon type: " + weaponType);
        }
    }
}
