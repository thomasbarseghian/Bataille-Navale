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
    private boolean m_hideShips; // True pour la grille ennemie
    private int m_size;

    /**
     * @param grid Le modèle de la grille à afficher
     * @param hideShips Si true, cache les bateaux non touchés (brouillard de guerre)
     */
    public GridPanel(Grid grid, boolean hideShips) {
        this.m_gridModel = grid;
        this.m_hideShips = hideShips;
        this.m_size = grid.getSize(); // Nécessite le getter ajouté dans Grid
        this.m_buttons = new ArrayList<>();

        // On s'abonne pour recevoir les mises à jour du modèle
        this.m_gridModel.addObserver(this);

        initLayout();
    }

    private void initLayout() {
        // Configuration du Layout (Grille carrée)
        this.setLayout(new GridLayout(m_size, m_size));

        // Création des boutons pour chaque case
        for (int i = 0; i < m_size * m_size; i++) {
            JButton btn = new JButton();
            btn.setPreferredSize(new Dimension(40, 40)); // Taille d'une case

            // On stocke la position (index) dans le bouton pour la retrouver au clic
            btn.putClientProperty("position", i);

            // Design de base
            btn.setOpaque(true);
            btn.setBorder(BorderFactory.createLineBorder(Color.GRAY));

            m_buttons.add(btn);
            this.add(btn);
        }

        // Premier affichage
        refreshGrid();
    }

    /**
     * Met à jour l'affichage de toute la grille en fonction des données du modèle
     */
    public void refreshGrid() {
        for (int i = 0; i < m_buttons.size(); i++) {
            updateSingleTile(i);
        }
    }

    /**
     * Met à jour une seule case (optimisation)
     */
    private void updateSingleTile(int pos) {
        Tile tile = m_gridModel.getTile(pos);
        JButton btn = m_buttons.get(pos);

        boolean isHit = tile.isHit();
        boolean hasObject = (tile.getObject() != null);
        TileType type = tile.getTileType(); // <--- ON RECUPÈRE LE TYPE ICI

        // --- Logique d'affichage (Couleurs) ---

        if (isHit) {
            // Cas 1 : La case a été touchée
            btn.setEnabled(false); // On ne peut plus cliquer dessus

            if (hasObject) {
                // TOUCHÉ !
                btn.setBackground(Color.RED);
                btn.setText("X");
            } else {
                // DANS LE VIDE
                btn.setText("O");
                // Si on a tiré dans le sable (Terre) ou dans l'eau
                if (type == TileType.LAND) {
                    btn.setBackground(new Color(139, 69, 19)); // Marron (Terre creusée)
                } else {
                    btn.setBackground(new Color(50, 50, 200)); // Bleu foncé (Plouf)
                }
            }
        } else {
            // Cas 2 : La case n'a pas encore été touchée
            btn.setEnabled(true);
            btn.setText("");

            if (hasObject && !m_hideShips) {
                // C'est un bateau ET on a le droit de le voir
                if(tile.getObject().getObjectType() == PlaceableObjectType.SHIP) {
                    btn.setBackground(Color.DARK_GRAY);
                } else {
                    btn.setBackground(Color.ORANGE);
                }
            } else {
                // C'est vide (ou caché) -> On affiche le TERRAIN
                if (type == TileType.LAND) {
                    btn.setBackground(new Color(238, 214, 175)); // Beige (Sable / ILE)
                } else {
                    btn.setBackground(new Color(135, 206, 235)); // Bleu ciel (EAU)
                }
            }
        }
    }

    /**
     * Permet au contrôleur d'ajouter une action quand on clique sur une case
     */
    public void addInteractionListener(ActionListener listener) {
        for (JButton btn : m_buttons) {
            btn.addActionListener(listener);
        }
    }

    @Override
    public void updateTileHit(int pos) {
        // Appelé automatiquement par Grid via notifyTileHit
        updateSingleTile(pos);
    }
}