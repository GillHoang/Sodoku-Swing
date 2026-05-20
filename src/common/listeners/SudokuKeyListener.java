package common.listeners;

import view.GameView;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class SudokuKeyListener extends KeyAdapter {
    private final int row;
    private final int col;
    private final GameView.CellInputHandler inputHandler;

    public SudokuKeyListener(int row, int col, GameView.CellInputHandler inputHandler) {
        this.row = row;
        this.col = col;
        this.inputHandler = inputHandler;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();

        if (isDeleteKey(keyCode)) {
            inputHandler.onInput(row, col, 0);
            return;
        }

        int number = parseNumberInput(keyCode);
        if (number >= 1 && number <= 9) {
            inputHandler.onInput(row, col, number);
        }
    }

    private boolean isDeleteKey(int keyCode) {
        return keyCode == KeyEvent.VK_BACK_SPACE || keyCode == KeyEvent.VK_DELETE;
    }

    private int parseNumberInput(int keyCode) {
        if (keyCode >= KeyEvent.VK_1 && keyCode <= KeyEvent.VK_9) {
            return keyCode - KeyEvent.VK_0;
        }
        if (keyCode >= KeyEvent.VK_NUMPAD1 && keyCode <= KeyEvent.VK_NUMPAD9) {
            return keyCode - KeyEvent.VK_NUMPAD0;
        }
        return 0;
    }
}
