package controller;

import model.game.Game;
import model.grid.Grid;
import model.placeableObject.ship.*;

public class PlacementController {
    private Game m_game;
    private Grid m_playerGrid;
    private ShipFactory m_factory; // Ajout de la factory

    public PlacementController(Game game, Grid playerGrid) {
        this.m_game = game;
        this.m_playerGrid = playerGrid;
        this.m_factory = new ShipFactory(); // On l'initialise ici
    }

    /**
     * Place les bateaux automatiquement (Niveau 1)
     * Utilisation de la Factory pour créer les navires.
     */
    public void placeShipsFixed() {
        // 1. Porte-Avion (5 cases)
        Ship carrier = m_factory.createShip(ShipType.AIRCRAFTCARRIER);
        carrier.setDirection(Direction.HORIZONTAL);
        carrier.setPosition(0);
        m_playerGrid.putPlaceObjectInTile(carrier);

        // 2. Croiseur (4 cases)
        Ship cruiser = m_factory.createShip(ShipType.CRUISER);
        cruiser.setDirection(Direction.HORIZONTAL);
        cruiser.setPosition(10);
        m_playerGrid.putPlaceObjectInTile(cruiser);

        // 3. Contre-Torpilleur (3 cases)
        Ship destroyer = m_factory.createShip(ShipType.DESTROYER);
        destroyer.setDirection(Direction.HORIZONTAL);
        destroyer.setPosition(90);
        m_playerGrid.putPlaceObjectInTile(destroyer);

        // 4. Sous-Marin (3 cases)
        Ship submarine = m_factory.createShip(ShipType.SUBMARINE);
        submarine.setDirection(Direction.HORIZONTAL);
        submarine.setPosition(97);
        m_playerGrid.putPlaceObjectInTile(submarine);

        // 5. Torpilleur (2 cases)
        Ship torpedo = m_factory.createShip(ShipType.TORPEDOBOAT);
        torpedo.setDirection(Direction.VERTICAL);
        torpedo.setPosition(19);
        m_playerGrid.putPlaceObjectInTile(torpedo);
    }

    public void startGame() {
        m_game.startBattle();
    }
}