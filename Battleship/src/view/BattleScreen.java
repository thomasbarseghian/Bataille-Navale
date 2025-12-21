package view;

import controller.BattleController;
import model.game.GameObserver;
import model.game.GameState;
import model.placeableObject.Weapon.WeaponType;

import javax.swing.*;
import java.awt.*;

public class BattleScreen extends JPanel implements GameObserver {

    private BattleController m_controller;
    private GridPanel m_playerGridPanel;
    private GridPanel m_enemyGridPanel;

    private JLabel m_turnLabel;
    private JLabel m_playerStatsLabel;
    private JLabel m_enemyStatsLabel;
    private JTextArea m_gameLog;

    // NEW: UI for weapons
    private JLabel m_currentWeaponLabel;
    private JButton m_btnStandard;
    private JButton m_btnBomb;

    public BattleScreen(BattleController controller) {
        this.m_controller = controller;

        this.setLayout(new BorderLayout());

        // --- TOP: Turn + Weapons ---
        JPanel topPanel = new JPanel(new BorderLayout());

        // Turn in the center
        m_turnLabel = new JLabel("Tour actuel : 1");
        m_turnLabel.setFont(new Font("Arial", Font.BOLD, 18));
        m_turnLabel.setHorizontalAlignment(SwingConstants.CENTER);
        topPanel.add(m_turnLabel, BorderLayout.CENTER);

        // Weapon Panel on the right (NEW)
        JPanel weaponPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        weaponPanel.setBorder(BorderFactory.createTitledBorder("Arsenal"));

        m_currentWeaponLabel = new JLabel("Arme : STANDARD");
        m_currentWeaponLabel.setForeground(Color.BLUE);
        m_currentWeaponLabel.setFont(new Font("Arial", Font.BOLD, 14));
        weaponPanel.add(m_currentWeaponLabel);

        m_btnStandard = new JButton("Standard");
        m_btnStandard.addActionListener(e -> selectWeapon(WeaponType.DEFAULT));
        weaponPanel.add(m_btnStandard);

        m_btnBomb = new JButton("BOMBE");
        m_btnBomb.setBackground(Color.RED);
        m_btnBomb.setForeground(Color.WHITE);
        m_btnBomb.addActionListener(e -> selectWeapon(WeaponType.BOMB));
        weaponPanel.add(m_btnBomb);

        topPanel.add(weaponPanel, BorderLayout.EAST);

        this.add(topPanel, BorderLayout.NORTH);

        // --- CENTER (Grids) ---
        JPanel gridsContainer = new JPanel(new GridLayout(1, 2, 10, 0));

        // Left: Player
        JPanel leftPanel = new JPanel(new BorderLayout());
        m_playerGridPanel = new GridPanel(m_controller.getHumanGrid(), false);
        leftPanel.add(new JLabel("Votre Flotte"), BorderLayout.NORTH);
        leftPanel.add(m_playerGridPanel, BorderLayout.CENTER);

        // Right: Enemy
        JPanel rightPanel = new JPanel(new BorderLayout());
        m_enemyGridPanel = new GridPanel(m_controller.getAiGrid(), true);
        rightPanel.add(new JLabel("Radar Ennemi"), BorderLayout.NORTH);
        rightPanel.add(m_enemyGridPanel, BorderLayout.CENTER);

        // Interaction delegated to Controller
        m_enemyGridPanel.addInteractionListener(e -> {
            JButton btn = (JButton) e.getSource();
            int pos = (int) btn.getClientProperty("position");
            m_controller.onEnemyGridClicked(pos);

            // IMPORTANT: After a shot, reset the display to "Standard"
            // because special weapons (Bomb) are single-use.
            updateWeaponLabel(WeaponType.DEFAULT);
        });

        gridsContainer.add(leftPanel);
        gridsContainer.add(rightPanel);
        this.add(gridsContainer, BorderLayout.CENTER);

        // --- SOUTH (Logs & Stats) ---
        JPanel statsPanel = new JPanel(new GridLayout(1, 3));
        statsPanel.setPreferredSize(new Dimension(800, 150));
        statsPanel.setBorder(BorderFactory.createTitledBorder("Informations de la Bataille"));

        m_playerStatsLabel = new JLabel();
        statsPanel.add(m_playerStatsLabel);

        m_gameLog = new JTextArea();
        m_gameLog.setEditable(false);
        m_gameLog.setLineWrap(true);
        JScrollPane logScroll = new JScrollPane(m_gameLog);
        statsPanel.add(logScroll);

        m_enemyStatsLabel = new JLabel();
        statsPanel.add(m_enemyStatsLabel);

        this.add(statsPanel, BorderLayout.SOUTH);

        updateStats(); // Initial update
    }

    // NEW: Method to handle clicking on a weapon
    private void selectWeapon(WeaponType type) {
        // We ask the controller if we can equip the weapon
        boolean success = m_controller.onWeaponSelected(type);

        if (success) {
            updateWeaponLabel(type);
        }
    }

    // Visually changes the weapon label
    private void updateWeaponLabel(WeaponType type) {
        m_currentWeaponLabel.setText("Arme : " + type);
        if (type == WeaponType.BOMB) {
            m_currentWeaponLabel.setForeground(Color.RED);
        } else {
            m_currentWeaponLabel.setForeground(Color.BLUE);
        }
    }

    private void updateStats() {
        m_playerStatsLabel.setText(m_controller.getHumanStatsText());
        m_enemyStatsLabel.setText(m_controller.getAiStatsText());
    }

    @Override
    public void updateGameState(GameState state) {
        m_playerGridPanel.refreshGrid();
        m_enemyGridPanel.refreshGrid();
        updateStats();
    }

    @Override
    public void updateTurnNumber(int turnNumber) {
        m_turnLabel.setText("Tour actuel : " + turnNumber);
        updateStats();
    }

    @Override
    public void updateHistory(String logMessage) {
        m_gameLog.append(logMessage + "\n");
        m_gameLog.setCaretPosition(m_gameLog.getDocument().getLength());
    }
}