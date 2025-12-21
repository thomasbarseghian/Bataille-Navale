package view;

import model.grid.Grid;
import model.grid.GridObserver;
import model.grid.Tile;
import model.grid.TileType;
import model.placeableObject.PlaceableObjectType;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class GridPanel extends JPanel implements GridObserver {

    private Grid m_gridModel;
    private ArrayList<JButton> m_buttons;
    private boolean m_hideShips; // True for the enemy grid
    private int m_size;

    /**
     * @param grid The model of the grid to display
     * @param hideShips If true, hides untouched ships (fog of war)
     */
    public GridPanel(Grid grid, boolean hideShips) {
        this.m_gridModel = grid;
        this.m_hideShips = hideShips;
        this.m_size = grid.getSize(); // Requires the getter added in Grid
        this.m_buttons = new ArrayList<>();

        // We subscribe to receive updates from the model
        this.m_gridModel.addObserver(this);

        initLayout();
    }

    private void initLayout() {
        // Layout Configuration (Square Grid)
        this.setLayout(new GridLayout(m_size, m_size));

        // Creating buttons for each tile
        for (int i = 0; i < m_size * m_size; i++) {
            JButton btn = new JButton();
            btn.setPreferredSize(new Dimension(40, 40)); // Tile size

            // Storing the position (index) in the button to retrieve it on click
            btn.putClientProperty("position", i);

            // Basic Design
            btn.setOpaque(true);
            btn.setBorder(BorderFactory.createLineBorder(Color.GRAY));

            m_buttons.add(btn);
            this.add(btn);
        }

        // First display
        refreshGrid();
    }

    /**
     * Updates the display of the entire grid based on the model data
     */
    public void refreshGrid() {
        for (int i = 0; i < m_buttons.size(); i++) {
            updateSingleTile(i);
        }
    }

    /**
     * Updates a single tile (optimization)
     */
    private void updateSingleTile(int pos) {
        Tile tile = m_gridModel.getTile(pos);
        JButton btn = m_buttons.get(pos);

        boolean isHit = tile.isHit();
        boolean hasObject = (tile.getObject() != null);
        TileType type = tile.getTileType(); // <--- WE RETRIEVE THE TYPE HERE

        // --- Display Logic (Colors) ---

        if (isHit) {
            // Case 1: The tile has been hit
            btn.setEnabled(false); // We can no longer click on it

            if (hasObject) {
                // HIT!
                btn.setBackground(Color.RED);
                btn.setText("X");
            } else {
                // MISS
                btn.setText("O");
                // If we shot in the sand (Land) or in the water
                if (type == TileType.LAND) {
                    btn.setBackground(new Color(139, 69, 19)); // Brown (Dug earth)
                } else {
                    btn.setBackground(new Color(50, 50, 200)); // Dark blue (Splash)
                }
            }
        } else {
            // Case 2: The tile has not been hit yet
            btn.setEnabled(true);
            btn.setText("");

            if (hasObject && !m_hideShips) {
                // It is a ship AND we are allowed to see it
                if(tile.getObject().getObjectType() == PlaceableObjectType.SHIP) {
                    btn.setBackground(Color.DARK_GRAY);
                } else {
                    btn.setBackground(Color.ORANGE);
                }
            } else {
                // It is empty (or hidden) -> We display the TERRAIN
                if (type == TileType.LAND) {
                    btn.setBackground(new Color(238, 214, 175)); // Beige (Sand / ISLAND)
                } else {
                    btn.setBackground(new Color(135, 206, 235)); // Sky blue (WATER)
                }
            }
        }
    }

    /**
     * Allows the controller to add an action when a tile is clicked
     */
    public void addInteractionListener(ActionListener listener) {
        for (JButton btn : m_buttons) {
            btn.addActionListener(listener);
        }
    }

    @Override
    public void updateTileHit(int pos) {
        // Called automatically by Grid via notifyTileHit
        updateSingleTile(pos);
    }
}