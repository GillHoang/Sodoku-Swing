package view;

import model.GameSnapshot;

public interface GameView {
    void render(GameSnapshot snapshot);

    void updateTime(long elapsedSeconds);

    void highlightSelection(int row, int col, int value);

    void setCellInputHandler(CellInputHandler handler);

    void setCellSelectionHandler(CellSelectionHandler handler);

    void setHintHandler(Runnable handler);

    void setExitHandler(Runnable handler);

    interface CellInputHandler {
        void onInput(int row, int col, int value);
    }

    interface CellSelectionHandler {
        void onSelected(int row, int col);
    }
}
