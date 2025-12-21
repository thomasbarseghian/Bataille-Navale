import controller.*;
import model.game.Game;
import model.grid.Grid;
import model.placeableObject.ship.Ship;
import model.placeableObject.ship.ShipFactory;
import model.placeableObject.ship.ShipType;
import model.player.AI;
import model.player.Human;
import model.player.Player;
import view.MainView;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        System.out.println("Lancement de la Bataille Navale - Niveau 1...");

        // --- 1. INITIALISATION DU MODÈLE ---

        // A. Création des flottes (5 navires chacun)
        ShipFactory factory = new ShipFactory();
        ArrayList<Ship> humanShips = createStandardFleet(factory);
        ArrayList<Ship> aiShips = createStandardFleet(factory);

        // B. Création des Grilles
        Grid humanGrid = new Grid(10);
        Grid aiGrid = new Grid(10);

        // C. Création des Joueurs
        Human humanPlayer = new Human("Joueur 1", humanGrid, humanShips);
        AI aiPlayer = new AI("Ordinateur", aiGrid, aiShips);

        // D. Création du Jeu
        Game game = new Game();
        game.setPlayers(humanPlayer, aiPlayer); // On injecte nos joueurs configurés

        // --- 2. CONTRÔLEURS LOGIQUES (Cerveau) ---

        HumanController humanLogic = new HumanController();
        humanLogic.setPlayer(humanPlayer);

        AIController aiLogic = new AIController();
        aiLogic.setPlayers(aiPlayer, humanPlayer); // L'IA attaque l'Humain

        // Chef d'orchestre du jeu (Gère les tours)
        GameController gameLogic = new GameController(humanLogic, aiLogic);
        gameLogic.setGame(game);

        // On relie les logiques au chef d'orchestre
        humanLogic.setGameController(gameLogic);
        aiLogic.setGameController(gameLogic);

        // --- 3. CONTRÔLEURS D'INTERFACE (Glu) ---

        ConfigController configCtrl = new ConfigController(game);

        // Placement (Niveau 1 : Fixe)
        PlacementController placementCtrl = new PlacementController(game, humanGrid);

        // Bataille (Gère les clics sur la grille ennemie)
        BattleController battleCtrl = new BattleController(humanLogic, aiPlayer);


        // --- 4. VUE PRINCIPALE ---
        MainView view = new MainView(configCtrl, placementCtrl, battleCtrl, humanPlayer, aiPlayer, game);

        // --- 5. NAVIGATION ---
        ScreenController screenCtrl = new ScreenController(view, game);

        // --- 6. GO ! ---
        game.startGame(); // Démarre en CONFIGURATION
    }

    /**
     * Helper pour créer la flotte standard de 5 navires
     */
    private static ArrayList<Ship> createStandardFleet(ShipFactory factory) {
        ArrayList<Ship> fleet = new ArrayList<>();
        fleet.add(factory.createShip(ShipType.AIRCRAFTCARRIER));
        fleet.add(factory.createShip(ShipType.CRUISER));
        fleet.add(factory.createShip(ShipType.DESTROYER));
        fleet.add(factory.createShip(ShipType.SUBMARINE));
        fleet.add(factory.createShip(ShipType.TORPEDOBOAT));
        return fleet;
    }
}