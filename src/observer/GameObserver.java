package observer;

public interface GameObserver {
    void onCellChanged(int row, int colm, int value);

    void onGameStateChanged(GameEvent event);
}
