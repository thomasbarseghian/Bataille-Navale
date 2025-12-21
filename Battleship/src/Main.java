import controller.ConfigController;
import controller.BattleController;
import controller.PlacementController;
import controller.ScreenController;
import model.game.Game;
import view.MainView;

public class Main {
    public static void main(String[] args) {
        Game gameModel = new Game();

        ConfigController configController = new ConfigController(gameModel);
        PlacementController placementController = new PlacementController();
        BattleController battleController = new BattleController();

        MainView masterView = new MainView(configController,placementController, battleController);


        ScreenController screenController = new ScreenController(masterView);

    }
}