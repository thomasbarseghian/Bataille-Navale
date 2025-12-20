package view;

import controller.ConfigController;
import javax.swing.*;
import java.awt.event.ActionEvent;

public class ConfigurationScreen extends JPanel {
    private ConfigController controller;
    private JButton startPlacementButton;
    public ConfigurationScreen(ConfigController controller) {
        this.controller = controller;
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        initComponents();
        startPlacementButton.addActionListener(this::handleStartPlacement);
    }

    private void initComponents() {
        // Bouton pour démarrer
        startPlacementButton = new JButton("Passer au placement");
        this.add(startPlacementButton);

        this.add(new JLabel("Configuration de la partie"));
    }

    private void handleStartPlacement(ActionEvent e) {
        controller.applyConfiguration();
    }
}