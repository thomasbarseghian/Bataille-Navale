package model.game;

import model.grid.Grid;
import model.placeableObject.ship.Ship;
import model.placeableObject.ship.ShipFactory;
import model.player.Human;
import model.player.Player;
import model.player.PlayerObserver;

import java.util.ArrayList;

import static model.placeableObject.ship.ShipType.*;

public class Game
{
    private int m_turnNumber;
    private int m_currentPlayer;
    private GameState m_state;
    private Player m_players[];
    private ArrayList<GameObserver> m_gameObserver;
    private MoveData m_moveHistory;

    public Game()
    {
        m_turnNumber = 0;
        m_currentPlayer = 0;
        m_state = GameState.CONFIGURATION;
        m_players = new Player[2];

        ShipFactory fac = new ShipFactory();
        ArrayList<Ship> ships1 = new ArrayList<Ship>();
        ships1.add(fac.createShip(AIRCRAFTCARRIER));
        ships1.add(fac.createShip(CRUISER));
        ships1.add(fac.createShip(DESTROYER));
        ships1.add(fac.createShip(SUBMARINE));
        ships1.add(fac.createShip(TORPEDOBOAT));

        ArrayList<Ship> ships2 = new ArrayList<Ship>();
        ships2.add(fac.createShip(AIRCRAFTCARRIER));
        ships2.add(fac.createShip(CRUISER));
        ships2.add(fac.createShip(DESTROYER));
        ships2.add(fac.createShip(SUBMARINE));
        ships2.add(fac.createShip(TORPEDOBOAT));
        Grid grid1 = new Grid(10);
        Grid grid2 = new Grid(10);

        Player j1 = new Human("Joueur 1", grid1, ships1);
        Player j2 = new Human("Joueur 2", grid2, ships2);
        m_players[0] = j1;
        m_players[1] = j2;

        m_gameObserver = new ArrayList<GameObserver>();
        m_moveHistory = new MoveData();
    }

    /**
     * Lancement du placement
     * On appelle cette méthode pour commencer le placement
     */
    public void startGame()
    {
        // Check if the game is currently in the configuration phase
        if (m_state == GameState.CONFIGURATION)
        {
            // Transition to the PLACEMENT state so players can position their ships
            m_state = GameState.PLACEMENT;

            // Notify the view/observers that the state has changed
            notifyGameState(m_state);
        }
    }

    /**
     * Lancement du combat
     * Une fois les bateaux placés, on appelle cette méthode pour commencer le combat
     */
    public void startBattle()
    {
        if (m_state == GameState.PLACEMENT)
        {
            m_state = GameState.PLAYING;
            notifyGameState(m_state);
            notifyTurnNumber(m_turnNumber);
        }
    }

    public void nextTurn()
    {
        m_turnNumber++;
    }

    public int getTurnNumber()
    {
        return m_turnNumber;
    }

    public GameState getGameState()
    {
        return m_state;
    }

    public boolean CheckWin()
    {
        return m_players[m_currentPlayer].allShipsAreSunk();
    }

    public void addObserver(GameObserver observer)
    {
        m_gameObserver.add(observer);
     }

    public void notifyGameState(GameState gameState)
    {
        for (GameObserver observer : m_gameObserver)
        {
            observer.updateGameState(gameState);
        }
    }

    public void notifyTurnNumber(int turnNumber)
    {
        for (GameObserver observer : m_gameObserver)
        {
            observer.updateTurnNumber(turnNumber);
        }
    }
}
