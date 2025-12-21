import controller.ConfigController;
import controller.BattleController;
import controller.PlacementController;
import controller.ScreenController;
import model.game.Game;
import model.grid.Grid;
import view.MainView;

public class Main {
    public static void main(String[] args) {
        // 1. Modèle
        Game game = new Game();

        // On récupère la grille du joueur 0 (Humain) via la méthode getPlayer(0)
        Grid humanGrid = game.getPlayer(0).getGrid();

        // 2. Contrôleurs
        ConfigController configCtrl = new ConfigController(game);
        PlacementController placementCtrl = new PlacementController(game, humanGrid);
        BattleController battleCtrl = new BattleController(); // Création vide pour l'instant

        // 3. Vue
        // On passe TOUS les contrôleurs + la grille + le jeu à la vue principale
        MainView view = new MainView(configCtrl, placementCtrl, battleCtrl, humanGrid, game);

        // 4. Contrôleur d'écran (Gestion des changements d'écran)
        ScreenController screenCtrl = new ScreenController(view, game);

        // 5. Lancement
        game.startGame();
    }
}