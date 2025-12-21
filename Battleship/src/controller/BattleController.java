package controller;

import model.grid.Grid;
import model.placeableObject.Weapon.WeaponType;
import model.placeableObject.ship.Ship;
import model.player.Player;

/**
 * Controller specifically for the BattleScreen UI interactions.
 * It bridges the View clicks to the HumanController logic and provides data to the View.
 */
public class BattleController {

    private HumanController m_humanLogic;
    private Player m_aiPlayer; // The target (AI)

    public BattleController(HumanController humanLogic, Player aiPlayer) {
        this.m_humanLogic = humanLogic;
        this.m_aiPlayer = aiPlayer;
    }

    /**
     * Called when the user clicks on a cell in the Enemy Grid.
     * @param position The index of the tile (0-99).
     */
    public void onEnemyGridClicked(int position) {
        m_humanLogic.handleTileClick(m_aiPlayer, position);
    }

    /**
     * NOUVEAU : Tente d'équiper une arme via le joueur.
     * @param type Le type d'arme (BOMB, DEFAULT...)
     * @return true si l'arme a été équipée, false sinon (ex: pas dans l'inventaire)
     */
    public boolean onWeaponSelected(WeaponType type) {
        return m_humanLogic.getPlayer().equipWeapon(type);
    }

    /**
     * Provides the Human Grid to the View (via the HumanController).
     */
    public Grid getHumanGrid() {
        return m_humanLogic.getPlayer().getGrid();
    }

    /**
     * Provides the AI Grid to the View.
     */
    public Grid getAiGrid() {
        return m_aiPlayer.getGrid();
    }

    /**
     * Generates the HTML Stats string for the Human Player.
     */
    public String getHumanStatsText() {
        return generateStatsHTML(m_humanLogic.getPlayer(), "Vous");
    }

    /**
     * Generates the HTML Stats string for the AI Player.
     */
    public String getAiStatsText() {
        return generateStatsHTML(m_aiPlayer, "Ordinateur");
    }

    /**
     * Helper logic to count ships and format text.
     */
    private String generateStatsHTML(Player p, String title) {
        int alive = 0;
        int sunk = 0;

        if (p.getShips() != null) {
            for (Ship s : p.getShips()) {
                if (s.isDestroyed()) {
                    sunk++;
                } else {
                    alive++;
                }
            }
        }

        return "<html><b>" + title + "</b><br/>" +
                "Navires intacts: " + alive + "<br/>" +
                "Navires coulés: " + sunk + "</html>";
    }
}