package model;

public record GameEvent(
        EventType type,
        GameSnapshot snapshot,
        int row,
        int col,
        int value,
        CellState cellState,
        int hintsRemaining
) {
    public enum EventType {
        GAME_STARTED,
        CELL_UPDATED,
        HINT_APPLIED,
        GAME_ENDED
    }

    public static GameEvent gameStarted(GameSnapshot snapshot, int hintsRemaining) {
        return new GameEvent(EventType.GAME_STARTED, snapshot, -1, -1, 0, null, hintsRemaining);
    }

    public static GameEvent cellUpdated(int row, int col, int value, CellState cellState, GameSnapshot snapshot, int hintsRemaining) {
        return new GameEvent(EventType.CELL_UPDATED, snapshot, row, col, value, cellState, hintsRemaining);
    }

    public static GameEvent hintApplied(int row, int col, int value, GameSnapshot snapshot, int hintsRemaining) {
        return new GameEvent(EventType.HINT_APPLIED, snapshot, row, col, value, CellState.CORRECT, hintsRemaining);
    }

    public static GameEvent gameEnded(GameSnapshot snapshot, int hintsRemaining) {
        return new GameEvent(EventType.GAME_ENDED, snapshot, -1, -1, 0, null, hintsRemaining);
    }
}
