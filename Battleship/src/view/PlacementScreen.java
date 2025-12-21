package view;

import controller.PlacementController;
import model.game.GameObserver;
import model.game.GameState;
import model.grid.Grid;

import javax.swing.*;
import java.awt.*;

public class PlacementScreen extends JPanel implements GameObserver {

    private PlacementController m_controller;
    private GridPanel m_gridPanel; // Reusable panel
    private JButton m_startButton;

    public PlacementScreen(PlacementController controller, Grid playerGrid) {
        this.m_controller = controller;

        // Main Layout
        this.setLayout(new BorderLayout());

        // 1. Title (Text in French)
        JLabel title = new JLabel("Placement de votre flotte");
        title.setFont(new Font("Arial", Font.BOLD, 22));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        this.add(title, BorderLayout.NORTH);

        // 2. The Grid in the center
        // FALSE because it is the player's grid: we want to SEE the ships (hideShips = false)
        m_gridPanel = new GridPanel(playerGrid, false);

        // Swing Trick: put the grid in a FlowLayout panel to keep it square and centered
        JPanel centerPanel = new JPanel(new FlowLayout());
        centerPanel.add(m_gridPanel);
        this.add(centerPanel, BorderLayout.CENTER);

        // 3. Bottom Button (Text in French)
        m_startButton = new JButton("LANCER LA BATAILLE !");
        m_startButton.setFont(new Font("Arial", Font.BOLD, 18));

        // Action: Call the controller to start the game
        m_startButton.addActionListener(e -> m_controller.startGame());

        JPanel bottomPanel = new JPanel();
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));
        bottomPanel.add(m_startButton);
        this.add(bottomPanel, BorderLayout.SOUTH);
    }

    @Override
    public void updateGameState(GameState state) {
        // As soon as we arrive on this screen, we ask the controller to place the ships
        if (state == GameState.PLACEMENT) {
            System.out.println("Vue Placement : Placement automatique des bateaux...");

            // 1. Update the Model (Data)
            m_controller.placeShipsFixed();

            // 2. Update the View (Visuals) <-- CRITICAL FIX
            // We must manually refresh the grid because 'putPlaceObjectInTile'
            // usually does not trigger a notification in the Grid class (only hits do).
            m_gridPanel.refreshGrid();
        }
    }

    @Override
    public void updateTurnNumber(int turnNumber) {
        // Not needed here
    }
}