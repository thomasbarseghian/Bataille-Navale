package model.placeableObject.ship;

public class ShipFactory {
    public ShipFactory(){

    }
    public Ship createShip(ShipType shipType){
        switch(shipType) {
            case AIRCRAFTCARRIER:
                return new AircraftCarrier(5,ShipType.AIRCRAFTCARRIER);
            case CRUISER:
                return new Cruiser(4,ShipType.CRUISER);
            case DESTROYER:
                return new Destroyer(3,ShipType.DESTROYER);
            case SUBMARINE:
                return new Submarine(3,ShipType.SUBMARINE);
            case TORPEDOBOAT:
                return new TorpedoBoat(2,ShipType.TORPEDOBOAT);
            default:
                throw new IllegalArgumentException("Unsupported ship type: " + shipType);
        }
    }
}
