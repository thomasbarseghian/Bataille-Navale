package view;

import javax.swing.*;
import java.awt.*;

public class MainView extends JFrame {
    private CardLayout cardLayout;
    private JPanel root;

    public MainView() {
        this.cardLayout = new CardLayout();
        this.root = new JPanel(cardLayout);

        // Ajout des panneaux
        root.add(new ConfigurationScreen(), "CONFIG");
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
