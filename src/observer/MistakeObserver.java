package observer;

import helpers.Utils;
import main.Main;

public class MistakeObserver implements GameObserver {
    @Override
    public void onCellChanged(int row, int colm, int value) {
        if (Main.STATE.isCompleted()) return;
        if (Main.STATE.getSolution()[row][colm] != value) {
            Main.STATE.incrementMistakes();

            Main.STATE.getLbMistakes().setText("Lỗi: " + Main.STATE.getMistakes() + "/" + Utils.MAX_MISTAKES);

            if (Main.STATE.getMistakes() == Utils.MAX_MISTAKES) {
                Main.STATE.setLost();
            }
        }
    }

    @Override
    public void onGameStateChanged(GameEvent event) throws UnsupportedOperationException {
        // hihi
    }
}
