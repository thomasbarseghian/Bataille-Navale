package view;

import model.game.Game;
import model.game.GameObserver;
import model.game.GameState;

import javax.swing.*;
import java.awt.*;

public class EndScreen extends JPanel implements GameObserver {

    private JLabel m_messageLabel;
    private Game m_game;

    public EndScreen(Game game) {
        this.m_game = game;
        this.setLayout(new GridBagLayout()); // Center everything

        m_messageLabel = new JLabel("Partie Terminée");
        m_messageLabel.setFont(new Font("Arial", Font.BOLD, 30));
        this.add(m_messageLabel);
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