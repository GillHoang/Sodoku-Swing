package observer.impl;

import main.Main;
import observer.GameEvent;
import observer.GameObserver;
import ui.ending.BadEnding;

import javax.swing.*;

public class BadEndingObserver implements GameObserver {
    @Override
    public void onCellChanged(int row, int colm, int value) {
        // hihi
    }

    @Override
    public void onGameStateChanged(GameEvent event) {
        if (event != GameEvent.GAME_LOST) return;

        new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                Thread.sleep(2000);
                return null;
            }

            @Override
            protected void done() {
                Main.STATE.getPnCard().add(new BadEnding(), "BadEnding");
                Main.STATE.getLyCard().show(Main.STATE.getPnCard(), "BadEnding");
            }
        }.execute();
    }
}
