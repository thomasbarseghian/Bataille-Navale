package view;

import model.game.Game;
import model.game.GameObserver;
import model.game.GameState;

import javax.swing.*;
import java.awt.*;

public class EndScreen extends JPanel implements GameObserver {

    private JLabel m_messageLabel;
    private JButton m_restartButton;
    private Game m_game;

    public EndScreen(Game game) {
        this.m_game = game;
        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // Label Victoire
        m_messageLabel = new JLabel("Partie Terminée");
        m_messageLabel.setFont(new Font("Arial", Font.BOLD, 30));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 20, 0);
        this.add(m_messageLabel, gbc);

        // Bouton restart
        m_restartButton = new JButton("Recommencer le Jeu");
        m_restartButton.setFont(new Font("Arial", Font.PLAIN, 20));
        m_restartButton.setFocusable(false);
        m_restartButton.addActionListener(e -> {
            m_game.restart();
        });

        gbc.gridy = 1;
        this.add(m_restartButton, gbc);
    }

    @Override
    public void updateGameState(GameState state) {
        if (state == GameState.END) {
            String winnerName = (m_game.getWinner() != null) ? m_game.getWinner().getName() : "Personne";
            m_messageLabel.setText("VICTOIRE DE : " + winnerName + " !");

            // Repaint to ensure text is updated
            this.revalidate();
            this.repaint();
        }
    }

    @Override
    public void updateTurnNumber(int turnNumber) {}

    @Override
    public void updateHistory(String logMessage) {}
}