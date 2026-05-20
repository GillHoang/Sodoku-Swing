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
    private final Timer timer;
    private Timer endingDelayTimer;

    public GameController(GameSession session, GameView view, Runnable onGameWon, Runnable onGameLost) {
        this.session = session;
        this.view = view;
        this.onGameWon = onGameWon;
        this.onGameLost = onGameLost;
        this.timer = new Timer(1000, e -> view.updateTime(session.getElapsedTimeSeconds()));
    }

    public void startGame() {
        session.startNewGame();
        GameSnapshot snapshot = session.snapshot();
        view.render(snapshot);

        view.setCellInputHandler(this::handleCellInput);
        view.setCellSelectionHandler(this::handleCellSelection);

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
            timer.stop();
            endingDelayTimer = new Timer(2000, e -> {
                ((Timer) e.getSource()).stop();
                endingDelayTimer = null;
                if (result.won()) {
                    onGameWon.run();
                } else {
                    onGameLost.run();
                }
            });
            endingDelayTimer.setRepeats(false);
            endingDelayTimer.start();
        }
    }
}
