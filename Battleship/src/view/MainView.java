package view;

import controller.*;
import model.game.Game;
import model.player.Player;

import javax.swing.*;
import java.awt.*;

public class MainView extends JFrame {
    private CardLayout cardLayout;
    private JPanel root;

    public MainView(ConfigController configCtrl,
                    PlacementController placementCtrl,
                    BattleController battleCtrl, // We still need this
                    Player humanPlayer,
                    Player aiPlayer,
                    Game game) {

        this.cardLayout = new CardLayout();
        this.root = new JPanel(cardLayout);

        root.add(new ConfigurationScreen(configCtrl), "CONFIG");

        PlacementScreen placementScreen = new PlacementScreen(placementCtrl, humanPlayer.getGrid());
        game.addObserver(placementScreen);
        root.add(placementScreen, "PLACEMENT");

        // --- CHANGE HERE ---
        // We no longer pass the players, just the controller!
        BattleScreen battleScreen = new BattleScreen(battleCtrl);
        game.addObserver(battleScreen);
        root.add(battleScreen, "PLAYING");
        // ----------------------

        EndScreen endScreen = new EndScreen(game);
        game.addObserver(endScreen); // It needs to know when game ends to update text
        root.add(endScreen, "END");

        this.setContentPane(root);
        this.setSize(1000, 750);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    public void showScreen(String name) {
        cardLayout.show(root, name);
    }
}