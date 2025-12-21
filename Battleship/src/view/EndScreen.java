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
    private JLabel m_statsLabel; // NOUVEAU : Label pour les stats
    private JButton m_restartButton;
    private Game m_game;

    public EndScreen(Game game) {
        this.m_game = game;
        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // 1. TITRE (Victoire)
        m_messageLabel = new JLabel("Partie Terminée");
        m_messageLabel.setFont(new Font("Arial", Font.BOLD, 30));
        m_messageLabel.setHorizontalAlignment(SwingConstants.CENTER);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 20, 0);
        this.add(m_messageLabel, gbc);

        // 2. STATISTIQUES (NOUVEAU)
        m_statsLabel = new JLabel("");
        m_statsLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        m_statsLabel.setHorizontalAlignment(SwingConstants.CENTER);

        gbc.gridy = 1; // En dessous du titre
        gbc.insets = new Insets(0, 0, 30, 0); // Marge avant le bouton
        this.add(m_statsLabel, gbc);

        // 3. BOUTON REJOUER
        m_restartButton = new JButton("Recommencer une partie");
        m_restartButton.setFont(new Font("Arial", Font.PLAIN, 18));
        m_restartButton.setFocusable(false);
        m_restartButton.setPreferredSize(new Dimension(250, 50));

        m_restartButton.addActionListener(e -> {
            m_game.restart();
        });

        gbc.gridy = 2; // Tout en bas
        gbc.insets = new Insets(0, 0, 0, 0);
        this.add(m_restartButton, gbc);
    }

    @Override
    public void updateGameState(GameState state) {
        if (state == GameState.END) {
            Player winner = m_game.getWinner();

            // Mise à jour du Titre
            String winnerName = (winner != null) ? winner.getName() : "Personne";
            m_messageLabel.setText("VICTOIRE DE : " + winnerName.toUpperCase() + " !");

            // Mise à jour des Stats
            if (winner != null) {
                m_statsLabel.setText(generateStatsHTML(winner));
            }

            this.revalidate();
            this.repaint();
        }
    }

    /**
     * Génère le texte des statistiques en HTML pour le JLabel.
     */
    private String generateStatsHTML(Player winner) {
        int turns = m_game.getTurnNumber();

        // Calcul des bateaux survivants du vainqueur
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

        // Construction du texte
        StringBuilder sb = new StringBuilder();
        sb.append("<html><center>");
        sb.append("<b>Résumé de la partie :</b><br/><br/>");
        sb.append("Durée de la bataille : ").append(turns).append(" tours<br/>");
        sb.append("Flotte restante (").append(winner.getName()).append(") : ")
                .append(shipsLeft).append(" / ").append(totalShips).append(" navires<br/>");

        // Petit message fun selon la performance
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