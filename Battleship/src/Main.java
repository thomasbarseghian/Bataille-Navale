import controller.*;
import model.game.Game;
import model.grid.Grid;
import model.placeableObject.ship.Ship;
import model.placeableObject.ship.ShipFactory;
import model.placeableObject.ship.ShipType;
import model.player.AI;
import model.player.Human;
import view.MainView;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        System.out.println("Lancement de la Bataille Navale - Nouvelle Architecture...");

        // --- 1. INITIALISATION DU MODÈLE ---

        ShipFactory factory = new ShipFactory();

        // Création des Grilles
        Grid humanGrid = new Grid(10);
        Grid aiGrid = new Grid(10);

        // Création des listes de navires
        ArrayList<Ship> humanShips = createStandardFleet(factory);
        ArrayList<Ship> aiShips = createStandardFleet(factory);

        // Création des Joueurs
        Human humanPlayer = new Human("Joueur 1", humanGrid, humanShips);
        AI aiPlayer = new AI("Ordinateur", aiGrid, aiShips);

        // IMPORTANT : Placement initial des bateaux (pour que le jeu ne soit pas vide)
        humanPlayer.placeShipFix();
        aiPlayer.placeShipFix();

        // Création du Jeu
        Game game = new Game();
        game.setPlayers(humanPlayer, aiPlayer);

        // --- 2. CONTRÔLEURS LOGIQUES ---

        // A. Human Controller
        HumanController humanLogic = new HumanController();
        humanLogic.setPlayer(humanPlayer);
        // Note: On ne set plus GameController ici avec la nouvelle structure

        // B. AI Controller
        AIController aiLogic = new AIController();
        aiLogic.setPlayer(aiPlayer);
        aiLogic.setOpponent(humanPlayer); // <--- NOUVEAU : L'IA doit connaître sa cible

        // C. Game Controller (Chef d'orchestre)
        // Le constructeur a changé : il prend (Game, HumanController, AIController)
        GameController gameLogic = new GameController(game, humanLogic, aiLogic);

        // --- 3. CONTRÔLEURS D'INTERFACE ---

        ConfigController configCtrl = new ConfigController(game);

        // Placement Controller
        PlacementController placementCtrl = new PlacementController(game);

        // Battle Controller
        // Il a besoin du humanLogic pour relayer les clics
        BattleController battleCtrl = new BattleController(humanLogic, aiPlayer);


        // --- 4. VUE PRINCIPALE ---
        MainView view = new MainView(configCtrl, placementCtrl, battleCtrl, humanPlayer, aiPlayer, game);

        // --- 5. NAVIGATION ---
        ScreenController screenCtrl = new ScreenController(view, game);

        // --- 6. GO ! ---
        game.startGame();
    }

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