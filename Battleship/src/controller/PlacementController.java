package controller;

import model.game.Game;
import model.grid.Grid;
import model.placeableObject.ship.*;
import model.player.Player;

public class PlacementController {
    private Game m_game;

    public PlacementController(Game game) {
        this.m_game = game;
    }

    /**
     * Place les bateaux automatiquement (Niveau 1)
     * Utilisation de la Factory pour créer les navires.
     */
    public void placeShipsFixed() {
        // 1. Retrieve the Human Player (Index 0)
        Player human = m_game.getPlayer(0);

        // 2. Delegate the placement logic to the Model (Human class)
        if (human != null) {
            human.placeShipFix();
        }
        Player aiPlayer = m_game.getPlayer(1);
        if (aiPlayer != null) {
            System.out.println("PlacementController : L'IA place ses bateaux...");
            aiPlayer.placeShipFix(); // Delegate to AI class
        }
    }

    public void startGame() {
        System.out.println("PlacementController : Lancement de la bataille...");
        m_game.startBattle();
    }
}