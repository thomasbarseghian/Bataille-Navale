package model.game;

public interface GameObserver {
    public void updateGameState(GameState gameState);
    public void updateTurnNumber(int turnNumber);
    public void updateHistory(String logMessage);
}