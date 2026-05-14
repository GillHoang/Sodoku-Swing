package observer.impl;

import main.Main;
import observer.GameEvent;
import observer.GameObserver;
import state.GameState;
import ui.ending.BadEnding;

import javax.swing.*;

public class BadEndingObserver implements GameObserver {
    @Override
    public void onCellChanged(int row, int colm, int value) {
        // yes
    }

    @Override
    public void onGameStateChanged(GameEvent event) {
        if (event != GameEvent.GAME_LOST) return;

        Main.STATE.setCompleted(true);
        Main.STATE.stopTimer();

        new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                Thread.sleep(2000);
                return null;
            }

            @Override
            protected void done() {
                Main.STATE.removeEndingPanels();
                Main.STATE.getPnCard().add(new BadEnding(), GameState.CARD_BAD_ENDING);
                Main.STATE.getLyCard().show(Main.STATE.getPnCard(), GameState.CARD_BAD_ENDING);
                Main.STATE.getPnCard().revalidate();
                Main.STATE.getPnCard().repaint();
            }
        }.execute();
    }
}
