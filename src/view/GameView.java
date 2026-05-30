package view;

import model.GameSnapshot;

public interface GameView {
    void render(GameSnapshot snapshot);

    void updateHud(GameSnapshot snapshot);

    void updateTime(long elapsedSeconds);

    void updateCell(int row, int col, int value, CellState cellState);

    void highlightSelection(int row, int col, int value);

    void updateHintsRemaining(int remaining);

    void setCellInputHandler(CellInputHandler handler);

    void setCellSelectionHandler(CellSelectionHandler handler);

    void setHintHandler(Runnable handler);

    void setExitHandler(Runnable handler);

    enum CellState {
        FIXED,
        CORRECT,
        WRONG,
        NORMAL
    }

    interface CellInputHandler {
        void onInput(int row, int col, int value);
    }

    interface CellSelectionHandler {
        void onSelected(int row, int col);
    }
}
