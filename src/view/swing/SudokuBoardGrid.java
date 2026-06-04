package view.swing;

import common.helpers.Utils;
import common.listeners.SudokuKeyListener;
import model.CellState;
import model.GameSnapshot;
import view.GameView;

import javax.swing.*;
import java.awt.*;
import java.util.Set;
import java.util.TreeSet;

import static common.helpers.Colors.*;

public class SudokuBoardGrid extends JPanel {
    private static final int SIZE = 9;
    private static final Font NOTE_FONT = new Font("Arial", Font.PLAIN, 10);
    private static final Color NOTE_COLOR = new Color(0x77, 0x77, 0x77);
    private final CellButton[][] buttons = new CellButton[SIZE][SIZE];
    private final CellState[][] cellStates = new CellState[SIZE][SIZE];
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
                cellStates[i][j] = CellState.NORMAL;
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
        if (cellStates[row][col] == CellState.FIXED) {
            return false;
        }
        return !hasValue[row][col] || cellStates[row][col] == CellState.WRONG;
    }

    private void renderNotes(int row, int col) {
        CellButton button = buttons[row][col];
        button.setText(" ");
        button.setForeground(null);
        button.setShowNotes(!cellNotes[row][col].isEmpty());
    }

    public void applySnapshot(GameSnapshot snapshot) {
        selectedRow = -1;
        selectedCol = -1;
        notesMode = false;
        int[][] board = snapshot.board();
        boolean[][] fixed = snapshot.fixedCells();
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                CellButton button = buttons[i][j];
                int value = board[i][j];
                cellNotes[i][j].clear();
                button.setShowNotes(false);
                hasValue[i][j] = value != 0;
                if (value == 0) {
                    button.setText(" ");
                    button.setForeground(null);
                    cellStates[i][j] = CellState.NORMAL;
                } else {
                    button.setText(String.valueOf(value));
                    if (fixed[i][j]) {
                        button.setForeground(clDen);
                        cellStates[i][j] = CellState.FIXED;
                    } else {
                        button.setForeground(clDen);
                        cellStates[i][j] = CellState.NORMAL;
                    }
                }
            }
        }
    }

    public void updateCell(int row, int col, int value, CellState cellState) {
        CellButton btn = buttons[row][col];
        if (value == 0) {
            hasValue[row][col] = false;
            cellStates[row][col] = CellState.NORMAL;
            if (cellNotes[row][col].isEmpty()) {
                btn.setShowNotes(false);
                btn.setText(" ");
                btn.setForeground(null);
            } else {
                renderNotes(row, col);
            }
            return;
        }
        cellNotes[row][col].clear();
        btn.setShowNotes(false);
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

    private CellButton createCellButton(int row, int col) {
        CellButton btn = new CellButton(cellNotes[row][col]);
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

    private static class CellButton extends JButton {
        private final transient Set<Integer> notes;
        private boolean showNotes;

        CellButton(Set<Integer> notes) {
            super(" ");
            this.notes = notes;
        }

        void setShowNotes(boolean showNotes) {
            this.showNotes = showNotes;
            repaint();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (!showNotes || notes.isEmpty()) {
                return;
            }
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(NOTE_COLOR);
            g2.setFont(NOTE_FONT);
            FontMetrics fm = g2.getFontMetrics();
            int cellWidth = getWidth() / 3;
            int cellHeight = getHeight() / 3;
            for (int n = 1; n <= 9; n++) {
                if (!notes.contains(n)) {
                    continue;
                }
                int index = n - 1;
                int gridRow = index / 3;
                int gridCol = index % 3;
                String text = String.valueOf(n);
                int x = gridCol * cellWidth + (cellWidth - fm.stringWidth(text)) / 2;
                int y = gridRow * cellHeight + (cellHeight - fm.getHeight()) / 2 + fm.getAscent();
                g2.drawString(text, x, y);
            }
            g2.dispose();
        }
    }
}
