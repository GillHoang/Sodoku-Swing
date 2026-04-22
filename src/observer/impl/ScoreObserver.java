package observer.impl;

import main.Main;
import observer.GameEvent;
import observer.GameObserver;

public class ScoreObserver implements GameObserver {
    @Override
    public void onCellChanged(int row, int colm, int value) {
        if (Main.STATE.isCompleted()) return;
        if (Main.STATE.getSolution()[row][colm] == value) {
            Main.STATE.addScore(20);
        } else {
            Main.STATE.addScore(-10);
        }

        Main.STATE.getLbScore().setText("Điểm: " + Main.STATE.getScore());
    }

    @Override
    public void onGameStateChanged(GameEvent event) {
    }
}
