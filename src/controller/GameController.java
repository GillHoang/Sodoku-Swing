package controller;

import model.GameSession;
import model.GameSnapshot;
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
        session.startNewGame();
        GameSnapshot snapshot = session.snapshot();
        view.render(snapshot);
        view.updateHintsRemaining(session.getHintsRemaining());

        view.setCellInputHandler(this::handleCellInput);
        view.setCellSelectionHandler(this::handleCellSelection);
        view.setHintHandler(this::handleHint);
        view.setExitHandler(onExit);

        timer.stop();
        timer.start();
    }

    public void stop() {
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

        GameView.CellState state = GameView.CellState.NORMAL;
        if (value != 0 && result.correctInput()) {
            state = GameView.CellState.CORRECT;
        } else if (result.incorrectInput()) {
            state = GameView.CellState.WRONG;
        }

        view.updateCell(row, col, value, state);
        view.updateHud(session.snapshot());
        view.highlightSelection(row, col, session.getCell(row, col));

        if (result.completed()) {
            scheduleEnding(result.won());
        }
    }

    private void handleHint() {
        GameSession.HintResult result = session.useHint();
        if (!result.applied()) {
            return;
        }

        view.updateCell(result.row(), result.col(), result.value(), GameView.CellState.CORRECT);
        view.updateHud(session.snapshot());
        view.highlightSelection(result.row(), result.col(), result.value());
        view.updateHintsRemaining(session.getHintsRemaining());

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
