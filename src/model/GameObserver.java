package model;

public interface GameObserver {
    void onGameStateChanged(GameEvent event);
}
