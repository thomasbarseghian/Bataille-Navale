package controller;

import model.placeableObject.Weapon.WeaponType;
import model.player.Player;
import model.game.Game;

public class GameController {

    private HumanController m_humanController;
    private AIController m_aiController;
    private Player m_currentPlayer;
    private Game m_game;

    public GameController(HumanController player, AIController ai) {
        this.m_humanController = player;
        this.m_aiController = ai;
        // Au début, c'est le tour du joueur humain
        this.m_currentPlayer = m_humanController.getPlayer();
    }
    public void setGame(Game game) {
        this.m_game = game;
    }
    /**
     * Called by the HumanController (via the WeaponCallback) when the player's action is fully complete.
     */
    public void endHumanTurn() {
        // TODO: Check for victory/defeat conditions here

        // We change the current Player to the AI
        m_currentPlayer = m_aiController.getPlayer();

        // Triggers the AI logic
        m_aiController.startTurn();
    }

    /**
     * Called by the AIController (via the WeaponCallback) when the AI's action is fully complete.
     */
    public void endAiTurn() {
        // TODO: Check for victory/defeat conditions here

        // We change the current Player to the Human
        m_currentPlayer = m_humanController.getPlayer();

        // Unlocks the UI or notifies the player it's their turn
        m_humanController.startTurn();
    }

    /**
     * Appelé quand le joueur clique sur le bouton d'une arme spéciale.
     * @param type Le type d'arme demandé (ex: WeaponType.BOMB)
     */
    public void onSpecialWeaponClicked(WeaponType type) {
        // We ignore the click if it is the AI's turn
        if (m_currentPlayer != m_humanController.getPlayer()) {
            return;
        }

        boolean success = m_currentPlayer.equipWeapon(type);

        if (!success) {
            // view.showError("Vous ne possédez pas cette arme !");
        } else {
            // view.highlightWeapon(type);
        }
    }

    // Exemple d'utilisation spécifique pour un bouton "Missile"
    public void onBombButtonClicked() {
        onSpecialWeaponClicked(WeaponType.BOMB); // Utilise l'Enum défini
    }

    // Called when the player clicks on an enemy tile
    public void onTileClick(int pos, Player targetPlayer) {

        // The Player attacks with the equipped weapon
        m_currentPlayer.attack(targetPlayer, pos);
    }
    public void notifyAttackResult(Player attacker, int pos, WeaponType weapon, boolean isHit) {
        if (m_game != null) {
            m_game.recordMove(attacker, pos, weapon, isHit);
        }
    }
}