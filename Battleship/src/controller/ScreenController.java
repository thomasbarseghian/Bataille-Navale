package controller;

import model.game.GameObserver;
import model.game.GameState;
import view.MainView;

public class ScreenController implements GameObserver {
    private MainView mainView;
    public ScreenController(MainView mainView) {
        this.mainView = mainView;
    }
    @Override
    public void updateGameState(GameState state) {
        System.out.println("Changement d'état détecté : " + state);

        switch (state) {
            case CONFIGURATION:
                mainView.showScreen("CONFIG");
                break;
            case PLACEMENT:
                mainView.showScreen("PLACEMENT");
                break;
            case PLAYING:
                mainView.showScreen("PLAYING");
                break;
            case END:
                mainView.showScreen("END");
                break;
            default:
                break;
        }
    }


}
