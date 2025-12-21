package model.game;

import model.grid.Grid;
import model.placeableObject.ship.Ship;
import model.placeableObject.ship.ShipFactory;
import model.player.Human;
import model.player.Player;
import model.player.PlayerObserver;
import model.placeableObject.Weapon.WeaponType;

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
    private Player m_winner;

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
        m_winner = null;
    }

    /**
     * Records a move in history and notifies the view.
     * Called by GameController.
     */
    public void recordMove(Player player, int position, WeaponType weapon, boolean isHit) {
        // 1. Add to data structure
        // Note: converting boolean isHit to int because your MoveData uses int in addData signature
        m_moveHistory.addData(position, isHit ? 1 : 0, player, weapon);

        // 2. Create a string message for the log
        String hitString = isHit ? "TOUCHÉ" : "RATÉ";
        String message = player.getName() + " tire en case " + position + " avec " + weapon + " : " + hitString;

        // 3. Notify observers (BattleScreen)
        notifyHistory(message);
    }
    /**
     * Notify the game is state of CONFIGURATION.
     */
    public void startGame() {
        notifyGameState(GameState.CONFIGURATION);
    }

    /**
     * Called bu ConfigController "Valider" / "Commencer le placement" is clicked
     */
    public void startPlacement() {
        if (m_state == GameState.CONFIGURATION) {
            m_state = GameState.PLACEMENT;
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

    /**
     * Ends the game and notifies observers.
     * @param winner The player who won (to display in the view).
     */
    public void stopGame(Player winner) {
        this.m_winner = winner;
        m_state = GameState.END;
        notifyGameState(m_state);

        //NEED A NOTIFY HERE ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    }
    public Player getWinner() {
        return m_winner;
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
    public Player getPlayer(int index) {
        if (index >= 0 && index < m_players.length) {
            return m_players[index];
        }
        return null;
    }

    public void setPlayers(Player p1, Player p2) {
        if (m_players == null) {
            m_players = new Player[2];
        }
        this.m_players[0] = p1;
        this.m_players[1] = p2;
    }
    public void notifyHistory(String message)
    {
        for (GameObserver observer : m_gameObserver)
        {
            observer.updateHistory(message);
        }
    }
    public void restart() {
        if (m_players != null) {
            for (Player p : m_players) {
                p.reset();
            }
        }

        m_turnNumber = 0;
        m_winner = null;
        m_moveHistory = new MoveData();

        m_state = GameState.CONFIGURATION;
        notifyGameState(m_state);

        notifyTurnNumber(0);
    }
}
