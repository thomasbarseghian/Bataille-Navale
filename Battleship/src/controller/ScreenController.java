package controller;

import model.game.Game;
import model.game.GameObserver;
import model.game.GameState;
import view.MainView;

public class ScreenController implements GameObserver {

    private MainView m_view;
    private Game m_game;

    public ScreenController(MainView view, Game game) {
        this.m_view = view;
        this.m_game = game;

        // Subscribe to game events to switch screens automatically
        this.m_game.addObserver(this);
    }

    @Override
    public void updateGameState(GameState state) {
        if (state == GameState.CONFIGURATION) {
            m_view.showScreen("CONFIG");
        }
        else if (state == GameState.PLACEMENT) {
            m_view.showScreen("PLACEMENT");
        }
        else if (state == GameState.PLAYING) {
            m_view.showScreen("PLAYING");
        }
        else if (state == GameState.END) {
            // Switch to the End screen when game is over
            m_view.showScreen("END");
        }
    }

    @Override
    public void updateTurnNumber(int turnNumber) {}

    @Override
    public void updateHistory(String logMessage) {}
}