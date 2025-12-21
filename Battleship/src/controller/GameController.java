package controller;

import model.placeableObject.Weapon.WeaponType;
import model.player.Player;

public class GameController implements TurnObserver {

    private HumanController m_humanController;
    private AIController m_aiController;
    private Player m_currentPlayer;

    public GameController(HumanController player, AIController ai) {
        this.m_humanController = player;
        this.m_aiController = ai;
        // Au début, c'est le tour du joueur humain
        // GameController subscribes to both sub-controllers
        this.m_humanController.addObserver(this);
        this.m_aiController.addObserver(this);
        this.m_currentPlayer = m_humanController.getPlayer();
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
        // TODO: Check victory...
        m_currentPlayer = m_aiController.getPlayer();
        m_aiController.startTurn();
    }

    private void endAiTurn() {
        // TODO: Check defeat...
        m_currentPlayer = m_humanController.getPlayer();
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
}