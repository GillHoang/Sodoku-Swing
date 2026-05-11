package observer.impl;

import main.Main;
import observer.GameEvent;
import observer.GameObserver;
import state.GameState;
import ui.ending.GoodEnding;

import javax.swing.*;

public class GoodEndingObserver implements GameObserver {
    @Override
    public void onCellChanged(int row, int colm, int value) {
        if (Main.STATE.isCompleted()) return;
        if (value == 0) return;
        if (Main.STATE.hasEmptyCell()) return;
        if (Main.STATE.checkDone()) {
            onGameStateChanged(GameEvent.GAME_COMPLETED);
        }
    }

    @Override
    public void onGameStateChanged(GameEvent event) {
        if (event != GameEvent.GAME_COMPLETED) return;

        Main.STATE.setCompleted(true);

        new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                Thread.sleep(2000);
                return null;
            }

            @Override
            protected void done() {
                Main.STATE.removeEndingPanels();
                Main.STATE.getPnCard().add(new GoodEnding(), GameState.CARD_GOOD_ENDING);
                Main.STATE.getLyCard().show(Main.STATE.getPnCard(), GameState.CARD_GOOD_ENDING);
                Main.STATE.getPnCard().revalidate();
                Main.STATE.getPnCard().repaint();
            }
        }.execute();
    }
}
