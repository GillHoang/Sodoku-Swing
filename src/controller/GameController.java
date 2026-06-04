package controller;

import model.GameObserver;
import model.GameSession;
import view.GameView;

import javax.swing.*;

public class GameController {
    private final GameSession session;
    private final GameView view;
    private final Runnable onGameWon;
    private final Runnable onGameLost;
    private final Runnable onExit;
    private final Timer timer;
    private Timer endingDelayTimer;

    public GameController(GameSession session, GameView view, Runnable onGameWon, Runnable onGameLost, Runnable onExit) {
        this.session = session;
        this.view = view;
        this.onGameWon = onGameWon;
        this.onGameLost = onGameLost;
        this.onExit = onExit;
        this.timer = new Timer(1000, e -> view.updateTime(session.getElapsedTimeSeconds()));
    }

    public void startGame() {
        session.addObserver((GameObserver) view);
        session.startNewGame();

        view.setCellInputHandler(this::handleCellInput);
        view.setCellSelectionHandler(this::handleCellSelection);
        view.setHintHandler(this::handleHint);
        view.setExitHandler(onExit);

        timer.stop();
        timer.start();
    }

    public void stop() {
        session.removeObserver((GameObserver) view);
        timer.stop();
        if (endingDelayTimer != null) {
            endingDelayTimer.stop();
            endingDelayTimer = null;
        }
    }

    private void handleCellSelection(int row, int col) {
        view.highlightSelection(row, col, session.getCell(row, col));
    }

    private void handleCellInput(int row, int col, int value) {
        GameSession.CellInputResult result = session.inputCell(row, col, value);
        if (!result.accepted()) {
            return;
        }
        if (result.completed()) {
            scheduleEnding(result.won());
        }
    }

    private void handleHint() {
        GameSession.HintResult result = session.useHint();
        if (result.completed()) {
            scheduleEnding(result.won());
        }
    }

    private void scheduleEnding(boolean won) {
        timer.stop();
        endingDelayTimer = new Timer(2000, e -> {
            ((Timer) e.getSource()).stop();
            endingDelayTimer = null;
            if (won) {
                onGameWon.run();
            } else {
                onGameLost.run();
            }
        });
        endingDelayTimer.setRepeats(false);
        endingDelayTimer.start();
    }
}
