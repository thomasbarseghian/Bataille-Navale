package controller;

import model.game.TurnObserver;
import model.game.WeaponCallback;
import model.player.Player;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractPlayerController implements WeaponCallback {

    // Common attribute: The player controlled by this controller
    protected Player m_player;

    // List of observers waiting for the turn to end (GameController)
    protected List<TurnObserver> m_observers;

    public AbstractPlayerController() {
        this.m_observers = new ArrayList<>();
    }

    // --- GETTERS & SETTERS ---

    public void setPlayer(Player player) {
        this.m_player = player;
    }

    public Player getPlayer() {
        return m_player;
    }

    // --- OBSERVER PATTERN LOGIC ---

    public void addObserver(TurnObserver observer) {
        if (!m_observers.contains(observer)) {
            m_observers.add(observer);
        }
    }

    public void removeObserver(TurnObserver observer) {
        m_observers.remove(observer);
    }

    /**
     * Notifies all observers that the turn is finished.
     */
    protected void notifyTurnEnded() {
        for (TurnObserver observer : m_observers) {
            observer.onTurnEnded(m_player);
        }
    }

    // --- WEAPON CALLBACK IMPLEMENTATION ---

    /**
     * This method is common to both AI and Human.
     * When the weapon animation/logic finishes, we notify the GameController.
     */
    @Override
    public void onAttackFinished() {
        notifyTurnEnded();
    }

    // --- ABSTRACT METHODS ---

    /**
     * Abstract method that must be implemented by children.
     * Defines what happens when the turn starts.
     */
    public abstract void startTurn();
}