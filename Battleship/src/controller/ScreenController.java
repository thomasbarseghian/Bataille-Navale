package controller;

import model.game.Game; // N'oublie pas cet import !
import model.game.GameObserver;
import model.game.GameState;
import view.MainView;

public class ScreenController implements GameObserver {
    private MainView mainView;

    // Modification ici : On ajoute Game en paramètre
    public ScreenController(MainView mainView, Game game) {
        this.mainView = mainView;

        // INDISPENSABLE : On s'abonne pour recevoir les alertes quand l'état change
        game.addObserver(this);
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

    @Override
    public void updateTurnNumber(int turnNumber) {
        // Pas nécessaire pour changer d'écran, mais obligatoire par l'interface
    }

    @Override
    public void updateHistory(String logMessage) {
        // Not needed here
    }
}