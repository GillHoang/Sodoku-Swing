package view.swing;

import common.helpers.Colors;
import common.helpers.Utils;
import model.GameSnapshot;
import view.GameView;

import javax.swing.*;
import java.awt.*;

import static common.helpers.Colors.clLam;

public class SudokuPanel extends JPanel implements GameView {
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
    public void render(GameSnapshot snapshot) {
        updateHud(snapshot);
        boardGrid.applySnapshot(snapshot);
        controlBar.resetNotes();
    }

    @Override
    public void updateHud(GameSnapshot snapshot) {
        hudBar.updateHud(snapshot);
        headerPanel.setTitle(snapshot.username(), Utils.convertNumberToLevel(snapshot.level()));
    }

    @Override
    public void updateTime(long elapsedSeconds) {
        hudBar.updateTime(elapsedSeconds);
    }

    @Override
    public void updateCell(int row, int col, int value, CellState cellState) {
        boardGrid.updateCell(row, col, value, cellState);
    }

    @Override
    public void highlightSelection(int row, int col, int value) {
        boardGrid.highlightSelection(row, col, value);
    }

    @Override
    public void updateHintsRemaining(int remaining) {
        controlBar.setHintsRemaining(remaining);
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
