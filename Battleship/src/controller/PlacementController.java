package controller;

import model.game.Game;
import model.grid.Grid;
import model.placeableObject.Weapon.Bomb;
import model.player.Player;

import java.util.ArrayList;

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
        Player human = m_game.getHumanPlayer();

        // 2. Delegate the placement logic to the Model (Human class)
        if (human != null) {
            human.placeShipFix();
        }
        Player aiPlayer = m_game.getAIPlayer();
        if (aiPlayer != null) {
            System.out.println("PlacementController : L'IA place ses bateaux...");
            aiPlayer.placeShipFix(); // Delegate to AI class
        }
    }
    private void placeBonusItems(Grid grid) {
        // 1. Récupérer toutes les cases de l'île
        ArrayList<Integer> landTiles = grid.getLandTiles();

        if (!landTiles.isEmpty()) {
            // 2. Choisir une case au hasard
            int randomIndex = (int) (Math.random() * landTiles.size());
            int position = landTiles.get(randomIndex);

            // 3. Créer la bombe et la poser
            Bomb hiddenBomb = new Bomb();
            hiddenBomb.setPosition(position);

            grid.putPlaceObjectInTile(hiddenBomb);

            System.out.println("DEBUG: Bombe cachée en position " + position);
        }
    }

    public void startGame() {
        System.out.println("PlacementController : Lancement de la bataille...");
        m_game.startBattle();
    }
}