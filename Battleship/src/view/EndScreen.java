package view;

import model.game.Game;
import model.game.GameObserver;
import model.game.GameState;
import model.player.Player;
import model.placeableObject.ship.Ship;

import javax.swing.*;
import java.awt.*;

public class EndScreen extends JPanel implements GameObserver {

    private JLabel m_messageLabel;
    private JLabel m_statsLabel; // Label for stats
    private JButton m_restartButton;
    private Game m_game;

    public EndScreen(Game game) {
        this.m_game = game;
        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // 1. TITLE (Victory)
        m_messageLabel = new JLabel("Partie Terminée");
        m_messageLabel.setFont(new Font("Arial", Font.BOLD, 30));
        m_messageLabel.setHorizontalAlignment(SwingConstants.CENTER);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 20, 0);
        this.add(m_messageLabel, gbc);

        // 2. STATISTICS (NEW)
        m_statsLabel = new JLabel("");
        m_statsLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        m_statsLabel.setHorizontalAlignment(SwingConstants.CENTER);

        gbc.gridy = 1; // Below the title
        gbc.insets = new Insets(0, 0, 30, 0); // Margin before the button
        this.add(m_statsLabel, gbc);

        // 3. REPLAY BUTTON
        m_restartButton = new JButton("Recommencer une partie");
        m_restartButton.setFont(new Font("Arial", Font.PLAIN, 18));
        m_restartButton.setFocusable(false);
        m_restartButton.setPreferredSize(new Dimension(350, 50));

        m_restartButton.addActionListener(e -> {
            m_game.restart();
        });

        gbc.gridy = 2; // At the very bottom
        gbc.insets = new Insets(0, 0, 0, 0);
        this.add(m_restartButton, gbc);
    }

    @Override
    public void updateGameState(GameState state) {
        if (state == GameState.END) {
            Player winner = m_game.getWinner();

            // Title update
            String winnerName = (winner != null) ? winner.getName() : "Personne";
            m_messageLabel.setText("VICTOIRE DE : " + winnerName.toUpperCase() + " !");

            // Stats update
            if (winner != null) {
                m_statsLabel.setText(generateStatsHTML(winner));
            }

            this.revalidate();
            this.repaint();
        }
    }

    /**
     * Generates the statistics text in HTML for the JLabel.
     */
    private String generateStatsHTML(Player winner) {
        int turns = m_game.getTurnNumber();

        // Calculation of the winner's surviving ships
        int shipsLeft = 0;
        int totalShips = 0;
        if (winner.getShips() != null) {
            totalShips = winner.getShips().size();
            for (Ship s : winner.getShips()) {
                if (s.getHp() > 0) {
                    shipsLeft++;
                }
            }
        }

        // Text construction
        StringBuilder sb = new StringBuilder();
        sb.append("<html><center>");
        sb.append("<b>Résumé de la partie :</b><br/><br/>");
        sb.append("Durée de la bataille : ").append(turns).append(" tours<br/>");
        sb.append("Flotte restante (").append(winner.getName()).append(") : ")
                .append(shipsLeft).append(" / ").append(totalShips).append(" navires<br/>");

        // Fun little message depending on performance
        if (shipsLeft == totalShips) {
            sb.append("<br/><i>Victoire parfaite ! (Aucun navire coulé)</i>");
        } else if (shipsLeft == 1) {
            sb.append("<br/><i>C'était tout juste !</i>");
        }

        sb.append("</center></html>");
        return sb.toString();
    }

    @Override
    public void updateTurnNumber(int turnNumber) {}

    @Override
    public void updateHistory(String logMessage) {}
}