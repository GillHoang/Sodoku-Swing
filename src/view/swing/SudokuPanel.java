package view.swing;

import common.helpers.Colors;
import common.helpers.Utils;
import common.listeners.SudokuKeyListener;
import model.GameSnapshot;
import view.GameView;

import javax.swing.*;
import java.awt.*;

import static common.helpers.Colors.*;

public class SudokuPanel extends JPanel implements GameView {
    private final JLabel lbScore = new JLabel("Điểm: 0");
    private final JLabel lbTime = new JLabel("Thời gian: 00:00");
    private final JLabel lbMistake = new JLabel("Lỗi: 0/3");
    private final JLabel lbTitle = new JLabel("", SwingConstants.CENTER);
    private final JButton[][] buttons = new JButton[9][9];
    private final CellState[][] cellStates = new CellState[9][9];
    private final CenterPanel centerPanel;
    private transient CellInputHandler cellInputHandler;
    private transient CellSelectionHandler cellSelectionHandler;

    public SudokuPanel() {
        super();

        setLayout(new BorderLayout());
        setBackground(Colors.clBe);
        add(new UpPanel(), BorderLayout.NORTH);
        centerPanel = new CenterPanel();
        add(centerPanel, BorderLayout.CENTER);

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                buttons[i][j] = createCellButton(i, j);
                cellStates[i][j] = CellState.NORMAL;
            }
        }
        centerPanel.boardPanel.mountButtons();
    }

    @Override
    public void render(GameSnapshot snapshot) {
        updateHud(snapshot);

        int[][] board = snapshot.board();
        boolean[][] fixed = snapshot.fixedCells();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                JButton button = buttons[i][j];
                int value = board[i][j];
                if (value == 0) {
                    button.setText(" ");
                    button.setForeground(null);
                    cellStates[i][j] = CellState.NORMAL;
                } else {
                    button.setText(String.valueOf(value));
                    if (fixed[i][j]) {
                        button.setForeground(clDen);
                        cellStates[i][j] = CellState.FIXED;
                    } else if (cellStates[i][j] == CellState.CORRECT) {
                        button.setForeground(clVang);
                    } else if (cellStates[i][j] == CellState.WRONG) {
                        button.setForeground(clDo);
                    } else {
                        button.setForeground(clDen);
                        cellStates[i][j] = CellState.NORMAL;
                    }
                }
            }
        }
    }

    @Override
    public void updateHud(GameSnapshot snapshot) {
        lbScore.setText("Điểm: " + snapshot.score());
        lbTime.setText("Thời gian: " + Utils.formatTime(snapshot.elapsedTimeSeconds()));
        lbMistake.setText("Lỗi: " + snapshot.mistakes() + "/" + snapshot.maxMistakes());
        lbTitle.setText("Xin chào " + snapshot.username() + ", bạn đã chọn level " + Utils.convertNumberToLevel(snapshot.level()));
    }

    @Override
    public void updateTime(long elapsedSeconds) {
        lbTime.setText("Thời gian: " + Utils.formatTime(elapsedSeconds));
    }

    @Override
    public void updateCell(int row, int col, int value, CellState cellState) {
        JButton btn = buttons[row][col];
        if (value == 0) {
            btn.setText(" ");
            btn.setForeground(null);
            cellStates[row][col] = CellState.NORMAL;
            return;
        }
        btn.setText(String.valueOf(value));
        cellStates[row][col] = cellState;
        btn.setForeground(switch (cellState) {
            case CORRECT -> clVang;
            case WRONG -> clDo;
            default -> clDen;
        });
    }

    @Override
    public void highlightSelection(int row, int col, int value) {
        removeBackground();
        for (int i = 0; i < 9; i++) {
            buttons[row][i].setBackground(clXam);
            buttons[i][col].setBackground(clXam);
        }

        if (value > 0) {
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    if (String.valueOf(value).equals(buttons[i][j].getText())) {
                        buttons[i][j].setBackground(clXam);
                    }
                }
            }
        }
        buttons[row][col].setBackground(clLam);
    }

    @Override
    public void setCellInputHandler(CellInputHandler handler) {
        this.cellInputHandler = handler;
    }

    @Override
    public void setCellSelectionHandler(CellSelectionHandler handler) {
        this.cellSelectionHandler = handler;
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
            if (cellSelectionHandler != null) {
                cellSelectionHandler.onSelected(r, c);
            }
        });
        btn.addKeyListener(new SudokuKeyListener(row, col, (r, c, value) -> {
            if (cellInputHandler != null) {
                cellInputHandler.onInput(r, c, value);
            }
        }));
        return btn;
    }

    private void removeBackground() {
        for (JButton[] rowButtons : buttons) {
            for (JButton button : rowButtons) {
                button.setBackground(clTrang);
            }
        }
    }

    class CenterPanel extends JPanel {
        private final BoardPanel boardPanel;

        public CenterPanel() {
            super();

            setLayout(new BorderLayout());
            setBackground(clLam);

            add(new InformationPanel(), BorderLayout.NORTH);
            boardPanel = new BoardPanel();
            add(boardPanel, BorderLayout.CENTER);
        }

        class InformationPanel extends JPanel {
            public InformationPanel() {
                super();
                setBackground(clLam);
                setLayout(new BorderLayout());

                JPanel pnScore = new JPanel();
                lbScore.setFont(Utils.createDefaultStyle(20));
                lbScore.setForeground(clTrang);
                pnScore.add(lbScore);
                pnScore.setBackground(clLam);

                JPanel pnTime = new JPanel();
                lbTime.setFont(Utils.createDefaultStyle(20));
                lbTime.setForeground(clTrang);
                pnTime.add(lbTime);
                pnTime.setBackground(clLam);

                JPanel pnMistake = new JPanel();
                lbMistake.setFont(Utils.createDefaultStyle(20));
                lbMistake.setForeground(clTrang);
                pnMistake.add(lbMistake);
                pnMistake.setBackground(clLam);


                add(pnScore, BorderLayout.WEST);
                add(pnTime, BorderLayout.CENTER);
                add(pnMistake, BorderLayout.EAST);
            }
        }

        class BoardPanel extends JPanel {
            public BoardPanel() {
                super();
                setLayout(new GridLayout(9, 9));
                setBackground(clLam);
            }

            public void mountButtons() {
                removeAll();
                for (int i = 0; i < 9; i++) {
                    for (int j = 0; j < 9; j++) {
                        add(buttons[i][j]);
                    }
                }
                decorateBorders();
            }

            private void decorateBorders() {
                for (int i = 0; i < 9; i += 3) {
                    for (int j = 0; j < 9; j += 3) {
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
        }
    }

    class UpPanel extends JPanel {
        public UpPanel() {
            super();

            setLayout(new BorderLayout());

            lbTitle.setFont(Utils.createDefaultStyle(20));
            lbTitle.setForeground(clTrang);
            add(lbTitle, BorderLayout.CENTER);

            setBackground(clLam);
            setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(clLam, 2), BorderFactory.createEmptyBorder(10, 15, 10, 15)));
        }
    }
}
