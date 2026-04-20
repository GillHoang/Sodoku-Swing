package observer;

import helpers.Sudoku;
import main.Main;
import ui.ending.GoodEnding;

import javax.swing.*;

public class GoodEndingObserver implements GameObserver {
    @Override
    public void onCellChanged(int row, int colm, int value) {
        if (Sudoku.checkDone(Main.STATE.getBoard())) {
            onGameStateChanged(GameEvent.GAME_COMPLETED);
        }
    }

    @Override
    public void onGameStateChanged(GameEvent event) {
        if (event != GameEvent.GAME_COMPLETED) return;

        new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                Thread.sleep(2000);
                return null;
            }

            @Override
            protected void done() {
                Main.STATE.getPnCard().add(new GoodEnding(), "GoodEnding");
                Main.STATE.getLyCard().show(Main.STATE.getPnCard(), "GoodEnding");

                Main.STATE.setCompleted(true);
            }
        }.execute();
    }
}
