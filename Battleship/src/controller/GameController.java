package controller;

import model.game.Game;
import model.placeableObject.Weapon.WeaponType;
import model.player.Player;

public class GameController implements TurnObserver {

    private HumanController m_humanController;
    private AIController m_aiController;
    private Player m_currentPlayer;
    private Game m_game;

    public GameController(Game game, HumanController player, AIController ai) {
        this.m_game = game;
        this.m_humanController = player;
        this.m_aiController = ai;
        // Au début, c'est le tour du joueur humain
        // GameController subscribes to both sub-controllers
        this.m_humanController.addObserver(this);
        this.m_aiController.addObserver(this);
        this.m_currentPlayer = m_humanController.getPlayer();
    }
    public void setGame(Game game) {
        this.m_game = game;
    }
    /**
     * The unified reaction to any turn ending.
     */
    @Override
    public void onTurnEnded(Player playerWhoFinished) {

        // We check who finished to switch to the other
        if (playerWhoFinished == m_humanController.getPlayer()) {
            endHumanTurn();
        } else {
            endAiTurn();
        }
    }

    private void endHumanTurn() {
        // 1. Check if the opponent (AI) has lost
        Player ai = m_aiController.getPlayer();

        if (ai.allShipsAreSunk()) {
            // VICTORY!
            // Maybe update view here ?
            m_game.stopGame(m_humanController.getPlayer());
            return;
        }

        // 2. If game continues, switch turn
        m_currentPlayer = ai;
        m_game.nextTurn(); // Update turn number in Model
        m_game.notifyTurnNumber(m_game.getTurnNumber()); // Notify View

        m_aiController.startTurn();
    }

    private void endAiTurn() {
        // 1. Check if the opponent (Human) has lost
        Player human = m_humanController.getPlayer();

        if (human.allShipsAreSunk()) {
            // DEFEAT!
            // Maybe update view here ?
            m_game.stopGame(m_aiController.getPlayer());
            // Don't start next turn
            return;
        }

        // 2. If game continues, switch turn
        m_currentPlayer = human;
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

        // Security check
        if (m_currentPlayer != m_humanController.getPlayer()) return;

        // Delegate to HumanController to handle the setup
        m_humanController.handleTileClick(targetPlayer, pos);
    }
    public void notifyAttackResult(Player attacker, int pos, WeaponType weapon, boolean isHit) {
        if (m_game != null) {
            m_game.recordMove(attacker, pos, weapon, isHit);
        }
    }
}