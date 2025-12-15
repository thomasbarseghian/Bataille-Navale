import controller.ConfigController;
import controller.MainController;
import controller.PlacementController;
import controller.ScreenController;
import model.game.Game;
import view.MainView;

public class Main {
    public static void main(String[] args) {
        Game gameModel = new Game();

        ConfigController configController = new ConfigController(gameModel);
        PlacementController placementController = new PlacementController();
        MainController mainController = new MainController();

        MainView masterView = new MainView(configController,placementController,mainController);


        ScreenController screenController = new ScreenController(masterView);

    }
}