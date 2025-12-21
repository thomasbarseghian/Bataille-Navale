package view;

import controller.ConfigController;
import controller.MainController;
import controller.PlacementController;
import model.game.Game;
import model.grid.Grid;

import javax.swing.*;
import java.awt.*;

public class MainView extends JFrame {
    private CardLayout cardLayout;
    private JPanel root;

    // J'ai ajouté Grid et Game aux paramètres pour pouvoir créer les écrans correctement
    public MainView(ConfigController configController, PlacementController placementController, MainController mainController, Grid humanGrid, Game game) {
        this.cardLayout = new CardLayout();
        this.root = new JPanel(cardLayout);

        // 1. Écran de Configuration
        root.add(new ConfigurationScreen(configController), "CONFIG");

        // 2. Écran de Placement
        // On crée l'écran
        PlacementScreen placementScreen = new PlacementScreen(placementController, humanGrid);
        // IMPORTANT : On abonne l'écran au jeu pour qu'il sache quand le placement commence
        game.addObserver(placementScreen);
        // On l'ajoute au panel
        root.add(placementScreen, "PLACEMENT");

        // 3. Écran de Jeu (Vide pour l'instant pour éviter les erreurs)
        root.add(new JPanel(), "PLAYING");

        // 4. Écran de Fin (Vide pour l'instant)
        root.add(new JPanel(), "END");

        // Configuration de la fenêtre
        this.setContentPane(root);
        this.setSize(900, 700);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null); // Centre la fenêtre
        this.setVisible(true);
    }

    public void showScreen(String name) {
        cardLayout.show(root, name);
    }
}