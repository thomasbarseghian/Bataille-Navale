package controller;

import model.game.Game;

public class ConfigController {
    private Game gameModel;

    public ConfigController(Game gameModel) {
        this.gameModel = gameModel;
    }
    public void applyConfiguration() {
        gameModel.startPlacement();
    }
}
