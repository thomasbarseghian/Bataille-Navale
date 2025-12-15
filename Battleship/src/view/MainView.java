package view;

import controller.ConfigController;
import controller.MainController;
import controller.PlacementController;

import javax.swing.*;
import java.awt.*;

public class MainView extends JFrame {
    private CardLayout cardLayout;
    private JPanel root;

    public MainView(ConfigController configController, PlacementController placementController, MainController mainController) {
        this.cardLayout = new CardLayout();
        this.root = new JPanel(cardLayout);

        // Ajout des panneaux
        root.add(new ConfigurationScreen(configController), "CONFIG");
        root.add(new PlacementScreen(), "PLACEMENT");
        root.add(new MainScreen(), "PLAYING");
        root.add(new EndScreen(), "END");

        this.setContentPane(root);
        this.setSize(800, 600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    public void showScreen(String name) {
        cardLayout.show(root, name);
    }
}
