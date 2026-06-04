package view.swing;

import common.helpers.Colors;
import common.helpers.Utils;
import model.CellState;
import model.GameEvent;
import model.GameObserver;
import model.GameSnapshot;
import view.GameView;

import javax.swing.*;
import java.awt.*;

import static common.helpers.Colors.clLam;

public class SudokuPanel extends JPanel implements GameView, GameObserver {
    private final SudokuHeaderPanel headerPanel;
    private final SudokuHudBar hudBar;
    private final SudokuBoardGrid boardGrid;
    private final SudokuControlBar controlBar;

    public SudokuPanel() {
        super(new BorderLayout());
        setBackground(Colors.clBe);

        headerPanel = new SudokuHeaderPanel();
        hudBar = new SudokuHudBar();
        boardGrid = new SudokuBoardGrid();
        controlBar = new SudokuControlBar();
        controlBar.setOnToggleNotes(boardGrid::setNotesMode);
        SudokuNumberPad numberPad = new SudokuNumberPad();
        numberPad.setOnDigit(boardGrid::inputAtSelection);

        JPanel south = new JPanel(new BorderLayout());
        south.setBackground(clLam);
        south.add(controlBar, BorderLayout.NORTH);
        south.add(numberPad, BorderLayout.SOUTH);

        JPanel center = new JPanel(new BorderLayout());
        center.setBackground(clLam);
        center.add(hudBar, BorderLayout.NORTH);
        center.add(boardGrid, BorderLayout.CENTER);
        center.add(south, BorderLayout.SOUTH);

        add(headerPanel, BorderLayout.NORTH);
        add(center, BorderLayout.CENTER);
    }

    @Override
    public void onGameStateChanged(GameEvent event) {
        switch (event.type()) {
            case GAME_STARTED -> {
                updateHud(event.snapshot());
                boardGrid.applySnapshot(event.snapshot());
                controlBar.resetNotes();
            }
            case CELL_UPDATED -> {
                boardGrid.updateCell(event.row(), event.col(), event.value(), event.cellState());
                updateHud(event.snapshot());
                boardGrid.highlightSelection(event.row(), event.col(), event.value());
                controlBar.setHintsRemaining(event.hintsRemaining());
            }
            case HINT_APPLIED -> {
                boardGrid.updateCell(event.row(), event.col(), event.value(), CellState.CORRECT);
                updateHud(event.snapshot());
                boardGrid.highlightSelection(event.row(), event.col(), event.value());
                controlBar.setHintsRemaining(event.hintsRemaining());
            }
            case GAME_ENDED -> updateHud(event.snapshot());
        }
    }

    private void updateHud(GameSnapshot snapshot) {
        hudBar.updateHud(snapshot);
        headerPanel.setTitle(snapshot.username(), Utils.convertNumberToLevel(snapshot.level()));
    }

    @Override
    public void render(GameSnapshot snapshot) {
        updateHud(snapshot);
        boardGrid.applySnapshot(snapshot);
        controlBar.resetNotes();
    }

    @Override
    public void updateTime(long elapsedSeconds) {
        hudBar.updateTime(elapsedSeconds);
    }

    @Override
    public void highlightSelection(int row, int col, int value) {
        boardGrid.highlightSelection(row, col, value);
    }

    @Override
    public void setCellInputHandler(CellInputHandler handler) {
        boardGrid.setCellInputHandler(handler);
    }

    @Override
    public void setCellSelectionHandler(CellSelectionHandler handler) {
        boardGrid.setCellSelectionHandler(handler);
    }

    @Override
    public void setHintHandler(Runnable handler) {
        controlBar.setOnHint(handler);
    }

    @Override
    public void setExitHandler(Runnable handler) {
        controlBar.setOnExit(handler);
    }
}
