package view;

import model.game.GameObserver;

import javax.swing.*;
import java.awt.*;

public class ConfigurationScreen extends JPanel implements GameObserver {
    public ConfigurationScreen(){
        this.add(new JLabel("Configuration Screen"));
    }
}