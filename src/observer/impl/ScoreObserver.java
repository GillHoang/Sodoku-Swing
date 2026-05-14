package observer.impl;

import main.Main;
import observer.GameEvent;
import observer.GameObserver;

public class ScoreObserver implements GameObserver {
    private final boolean[][] wasCorrect = new boolean[9][9];

    @Override
    public void onCellChanged(int row, int colm, int value) {
        if (Main.STATE.isCompleted()) return;

        boolean isCorrectNow = value != 0 && Main.STATE.getSolutionCell(row, colm) == value;
        if (!wasCorrect[row][colm] && isCorrectNow) {
            Main.STATE.addScore(20);
        }

        wasCorrect[row][colm] = isCorrectNow;

        Main.STATE.getLbScore().setText("Điểm: " + Main.STATE.getScore());
    }

    @Override
    public void onGameStateChanged(GameEvent event) {
        if (event == GameEvent.GAME_RESET) {
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    int v = Main.STATE.getCell(i, j);
                    wasCorrect[i][j] = v != 0 && v == Main.STATE.getSolutionCell(i, j);
                }
            }
            Main.STATE.getLbScore().setText("Điểm: " + Main.STATE.getScore());
        }
    }
}
