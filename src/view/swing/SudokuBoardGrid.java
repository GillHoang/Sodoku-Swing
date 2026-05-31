package view.swing;

import common.helpers.Utils;
import common.listeners.SudokuKeyListener;
import model.GameSnapshot;
import view.GameView;

import javax.swing.*;
import java.awt.*;
import java.util.Set;
import java.util.TreeSet;

import static common.helpers.Colors.*;

public class SudokuBoardGrid extends JPanel {
    private static final int SIZE = 9;
    private final JButton[][] buttons = new JButton[SIZE][SIZE];
    private final GameView.CellState[][] cellStates = new GameView.CellState[SIZE][SIZE];
    private final boolean[][] hasValue = new boolean[SIZE][SIZE];
    private final transient Set<Integer>[][] cellNotes = createNotesGrid();
    private boolean notesMode;
    private transient GameView.CellInputHandler cellInputHandler;
    private transient GameView.CellSelectionHandler cellSelectionHandler;
    private int selectedRow = -1;
    private int selectedCol = -1;

    public SudokuBoardGrid() {
        super(new GridLayout(SIZE, SIZE));
        setBackground(clLam);
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                buttons[i][j] = createCellButton(i, j);
                cellStates[i][j] = GameView.CellState.NORMAL;
            }
        }
        remountButtons();
        decorateBorders();
    }

    @SuppressWarnings("unchecked")
    private static Set<Integer>[][] createNotesGrid() {
        Set<Integer>[][] grid = new Set[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                grid[i][j] = new TreeSet<>();
            }
        }
        return grid;
    }

    public void setCellInputHandler(GameView.CellInputHandler handler) {
        this.cellInputHandler = handler;
    }

    public void setCellSelectionHandler(GameView.CellSelectionHandler handler) {
        this.cellSelectionHandler = handler;
    }

    public void setNotesMode(boolean enabled) {
        this.notesMode = enabled;
    }

    public void inputAtSelection(int value) {
        if (selectedRow < 0 || selectedCol < 0) {
            return;
        }
        handleLocalInput(selectedRow, selectedCol, value);
    }

    private void handleLocalInput(int row, int col, int value) {
        if (value < 0 || value > 9) {
            return;
        }
        if (notesMode && isNoteEditable(row, col)) {
            if (value == 0) {
                cellNotes[row][col].clear();
            } else {
                Set<Integer> notes = cellNotes[row][col];
                if (!notes.add(value)) {
                    notes.remove(value);
                }
            }
            renderNotes(row, col);
            return;
        }
        if (cellInputHandler != null) {
            cellInputHandler.onInput(row, col, value);
        }
    }

    private boolean isNoteEditable(int row, int col) {
        GameView.CellState state = cellStates[row][col];
        return state != GameView.CellState.FIXED
                && state != GameView.CellState.CORRECT
                && !hasValue[row][col];
    }

    private void renderNotes(int row, int col) {
        Set<Integer> notes = cellNotes[row][col];
        JButton button = buttons[row][col];
        if (notes.isEmpty()) {
            button.setText(" ");
            button.setForeground(null);
            return;
        }
        StringBuilder sb = new StringBuilder(
                "<html><table cellspacing='0' cellpadding='0' style='width:34px;'>");
        for (int r = 0; r < 3; r++) {
            sb.append("<tr>");
            for (int c = 0; c < 3; c++) {
                int n = r * 3 + c + 1;
                sb.append("<td align='center' style='width:11px;font-size:9px;color:#777777;'>");
                sb.append(notes.contains(n) ? n : "&nbsp;");
                sb.append("</td>");
            }
            sb.append("</tr>");
        }
        sb.append("</table></html>");
        button.setForeground(clDen);
        button.setText(sb.toString());
    }

    public void applySnapshot(GameSnapshot snapshot) {
        selectedRow = -1;
        selectedCol = -1;
        notesMode = false;
        int[][] board = snapshot.board();
        boolean[][] fixed = snapshot.fixedCells();
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                JButton button = buttons[i][j];
                int value = board[i][j];
                cellNotes[i][j].clear();
                hasValue[i][j] = value != 0;
                if (value == 0) {
                    button.setText(" ");
                    button.setForeground(null);
                    cellStates[i][j] = GameView.CellState.NORMAL;
                } else {
                    button.setText(String.valueOf(value));
                    if (fixed[i][j]) {
                        button.setForeground(clDen);
                        cellStates[i][j] = GameView.CellState.FIXED;
                    } else if (cellStates[i][j] == GameView.CellState.CORRECT) {
                        button.setForeground(clVang);
                    } else if (cellStates[i][j] == GameView.CellState.WRONG) {
                        button.setForeground(clDo);
                    } else {
                        button.setForeground(clDen);
                        cellStates[i][j] = GameView.CellState.NORMAL;
                    }
                }
            }
        }
    }

    public void updateCell(int row, int col, int value, GameView.CellState cellState) {
        JButton btn = buttons[row][col];
        if (value == 0) {
            hasValue[row][col] = false;
            cellStates[row][col] = GameView.CellState.NORMAL;
            if (cellNotes[row][col].isEmpty()) {
                btn.setText(" ");
                btn.setForeground(null);
            } else {
                renderNotes(row, col);
            }
            return;
        }
        cellNotes[row][col].clear();
        hasValue[row][col] = true;
        btn.setText(String.valueOf(value));
        cellStates[row][col] = cellState;
        btn.setForeground(switch (cellState) {
            case CORRECT -> clVang;
            case WRONG -> clDo;
            default -> clDen;
        });
    }

    public void highlightSelection(int row, int col, int value) {
        selectedRow = row;
        selectedCol = col;
        clearCellBackgrounds();
        for (int i = 0; i < SIZE; i++) {
            buttons[row][i].setBackground(clXam);
            buttons[i][col].setBackground(clXam);
        }
        int boxRowStart = row - row % 3;
        int boxColStart = col - col % 3;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[boxRowStart + i][boxColStart + j].setBackground(clXam);
            }
        }
        if (value > 0) {
            for (int i = 0; i < SIZE; i++) {
                for (int j = 0; j < SIZE; j++) {
                    if (String.valueOf(value).equals(buttons[i][j].getText())) {
                        buttons[i][j].setBackground(clXam);
                    }
                }
            }
        }
        buttons[row][col].setBackground(clLam);
    }

    private void remountButtons() {
        removeAll();
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                add(buttons[i][j]);
            }
        }
    }

    private void decorateBorders() {
        for (int i = 0; i < SIZE; i += 3) {
            for (int j = 0; j < SIZE; j += 3) {
                buttons[i][j].setBorder(BorderFactory.createMatteBorder(3, 3, 1, 1, Color.black));
                buttons[i][j + 2].setBorder(BorderFactory.createMatteBorder(3, 1, 1, 3, Color.black));
                buttons[i + 2][j + 2].setBorder(BorderFactory.createMatteBorder(1, 1, 3, 3, Color.black));
                buttons[i + 2][j].setBorder(BorderFactory.createMatteBorder(1, 3, 3, 1, Color.black));
                buttons[i][j + 1].setBorder(BorderFactory.createMatteBorder(3, 1, 1, 1, Color.black));
                buttons[i + 1][j + 2].setBorder(BorderFactory.createMatteBorder(1, 1, 1, 3, Color.black));
                buttons[i + 2][j + 1].setBorder(BorderFactory.createMatteBorder(1, 1, 3, 1, Color.black));
                buttons[i + 1][j].setBorder(BorderFactory.createMatteBorder(1, 3, 1, 1, Color.black));
                buttons[i + 1][j + 1].setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.black));
            }
        }
    }

    private void clearCellBackgrounds() {
        for (JButton[] rowButtons : buttons) {
            for (JButton button : rowButtons) {
                button.setBackground(clTrang);
            }
        }
    }

    private JButton createCellButton(int row, int col) {
        JButton btn = new JButton(" ");
        btn.setFont(Utils.createDefaultStyle(30));
        btn.setBackground(clTrang);
        btn.setActionCommand(row + " " + col);
        btn.addActionListener(e -> {
            String[] coords = e.getActionCommand().split(" ");
            int r = Integer.parseInt(coords[0]);
            int c = Integer.parseInt(coords[1]);
            selectedRow = r;
            selectedCol = c;
            if (cellSelectionHandler != null) {
                cellSelectionHandler.onSelected(r, c);
            }
        });
        btn.addKeyListener(new SudokuKeyListener(row, col, this::handleLocalInput));
        return btn;
    }
}
