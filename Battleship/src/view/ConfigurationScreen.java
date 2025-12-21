package view;

import controller.ConfigController;
import javax.swing.*;
import java.awt.*; // Import necessary for GridBagLayout, Font, etc.
import java.awt.event.ActionEvent;

public class ConfigurationScreen extends JPanel {
    private ConfigController controller;
    private JButton startPlacementButton;
    private JLabel titleLabel;

    public ConfigurationScreen(ConfigController controller) {
        this.controller = controller;
        this.setLayout(new GridBagLayout());

        initComponents();

        startPlacementButton.addActionListener(this::handleStartPlacement);
    }

    private void initComponents() {
        GridBagConstraints gbc = new GridBagConstraints();

        // --- 1. THE TITLE ---
        titleLabel = new JLabel("Bataille Navale");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 36));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Configuration of the title position
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 40, 0);
        gbc.anchor = GridBagConstraints.CENTER;
        this.add(titleLabel, gbc);

        // --- 2. THE BUTTON ---
        startPlacementButton = new JButton("Passer au placement des navires");
        startPlacementButton.setFont(new Font("Arial", Font.PLAIN, 18));
        startPlacementButton.setFocusable(false);
        startPlacementButton.setPreferredSize(new Dimension(450, 50));

        // Configuration of the button position
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 0, 0);
        this.add(startPlacementButton, gbc);
    }

    private void handleStartPlacement(ActionEvent e) {
        controller.applyConfiguration();
    }
}