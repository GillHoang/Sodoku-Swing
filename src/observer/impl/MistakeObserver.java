package observer.impl;

import main.Main;
import observer.GameEvent;
import observer.GameObserver;

public class MistakeObserver implements GameObserver {
    @Override
    public void onCellChanged(int row, int colm, int value) {
        if (Main.STATE.isCompleted()) return;
        if (Main.STATE.getSolution()[row][colm] != value) {
            Main.STATE.incrementMistakes();

            Main.STATE.getLbMistakes().setText("Lỗi: " + Main.STATE.getMistakes() + "/" + Main.STATE.getMaxMistakes());

            if (Main.STATE.getMistakes() == Main.STATE.getMaxMistakes()) {
                Main.STATE.setLost();
            }
        }
    }

    @Override
    public void onGameStateChanged(GameEvent event) throws UnsupportedOperationException {
        // hihi
    }
}
