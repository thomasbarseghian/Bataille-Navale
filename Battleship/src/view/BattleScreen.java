package view;

import controller.BattleController;
import model.game.GameObserver;
import model.game.GameState;

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

    // NO MORE PLAYER REFERENCES HERE! Only Controller.
    public BattleScreen(BattleController controller) {
        this.m_controller = controller;

        this.setLayout(new BorderLayout());

        // --- TOP ---
        JPanel topPanel = new JPanel();
        m_turnLabel = new JLabel("Tour actuel : 1");
        m_turnLabel.setFont(new Font("Arial", Font.BOLD, 18));
        topPanel.add(m_turnLabel);
        this.add(topPanel, BorderLayout.NORTH);

        // --- CENTER (Grids) ---
        JPanel gridsContainer = new JPanel(new GridLayout(1, 2, 10, 0));

        // Left: Player
        JPanel leftPanel = new JPanel(new BorderLayout());
        // We ask the Controller for the Grid
        m_playerGridPanel = new GridPanel(m_controller.getHumanGrid(), false);
        leftPanel.add(new JLabel("Votre Flotte"), BorderLayout.NORTH);
        leftPanel.add(m_playerGridPanel, BorderLayout.CENTER);

        // Right: Enemy
        JPanel rightPanel = new JPanel(new BorderLayout());
        // We ask the Controller for the Grid
        m_enemyGridPanel = new GridPanel(m_controller.getAiGrid(), true);
        rightPanel.add(new JLabel("Radar Ennemi"), BorderLayout.NORTH);
        rightPanel.add(m_enemyGridPanel, BorderLayout.CENTER);

        // Interaction delegated to Controller
        m_enemyGridPanel.addInteractionListener(e -> {
            JButton btn = (JButton) e.getSource();
            int pos = (int) btn.getClientProperty("position");
            m_controller.onEnemyGridClicked(pos);
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

    private void updateStats() {
        // We ask the Controller for the formatted text
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