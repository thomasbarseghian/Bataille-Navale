package model.game;

import model.player.Human;
import model.player.Player;

import java.util.ArrayList;

public class Game
{
    private int m_turnNumber;
    private int m_currentPlayer;
    private GameState m_state;
    private ArrayList<Player> m_players;
    private GameObserver m_observer;
    private MoveData m_moveHistory;

    public Game()
    {
        m_state = GameState.CONFIGURATION;
        m_currentPlayer = 0;
        Human j1 = new Human("j1", )
    }

    public void nextTurn()
    {
        m_turnNumber++;
    }

    public int getTurnNumber()
    {
        return m_turnNumber;
    }

    public GameState getState()
    {
        return m_state;
    }

    public boolean CheckWin()
    {

    }
}
