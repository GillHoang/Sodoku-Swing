package ui;

import helpers.Colors;
import helpers.Sudoku;
import helpers.Utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import static helpers.Colors.*;

public class SudokuPanel extends JPanel {
    public final int level;
    public final String username;

    public SudokuPanel(JPanel pnCard, CardLayout lyCard, String username, int level) {
        super();

        this.level = level;
        this.username = username;

        setLayout(new BorderLayout());

        setBackground(Colors.clBe);

        add(new UpPanel(), BorderLayout.NORTH);
        add(new CenterPanel(pnCard, lyCard), BorderLayout.CENTER);
    }

    class UpPanel extends JPanel {
        public UpPanel() {
            super();

            setLayout(new BorderLayout());


            JLabel lbTitle = new JLabel("Xin chào " + username + ", bạn đã chọn level " + Utils.convertNumberToLevel(level), SwingConstants.CENTER);
            lbTitle.setFont(Utils.createDefaultStyle(20));
            lbTitle.setForeground(clTrang);

            add(lbTitle, BorderLayout.CENTER);

            setBackground(clLam);
            setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(clLam, 2), BorderFactory.createEmptyBorder(10, 15, 10, 15)));
        }
    }

    class CenterPanel extends JPanel {
        public CenterPanel(JPanel pnCard, CardLayout lyCard) {
            super();

            setLayout(new BorderLayout());
            setBackground(clLam);

            add(new InformationPanel(), BorderLayout.NORTH);
            add(new BoardPanel(pnCard, lyCard), BorderLayout.CENTER);
            add(new NumberButtonPanel(), BorderLayout.SOUTH);
        }

        static class InformationPanel extends JPanel {
            public InformationPanel() {
                super();
                setBackground(clLam);
            }
        }

        static class NumberButtonPanel extends JPanel {
            public NumberButtonPanel() {
                super();
                setLayout(new GridBagLayout());
                setBackground(clLam);
                setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(clLam, 2), BorderFactory.createEmptyBorder(2, 2, 2, 2)));

                JPanel pnNumber = new JPanel();
                pnNumber.setLayout(new GridLayout(1, 9, 5, 5));
                pnNumber.setBackground(clLam);

                for (int i = 1; i <= 9; i++) {
                    JButton btn = new JButton(String.valueOf(i));
                    btn.setFont(Utils.createDefaultStyle(30));
                    btn.setForeground(clTrang);
                    btn.setBackground(clLam);
                    btn.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
                    pnNumber.add(btn);
                }

                add(pnNumber, new GridBagConstraints());
            }
        }

        class BoardPanel extends JPanel {
            private final JButton[][] buttons = new JButton[9][9];
            private final int[][] grid;
            private final int[][] result;
            private final JPanel pnCard;
            private final CardLayout lyCard;
            private int row1;
            private int col1;
            private int wrongTimes = 0;

            public BoardPanel(JPanel pnCard, CardLayout lyCard) {
                super();

                this.pnCard = pnCard;
                this.lyCard = lyCard;

                setLayout(new GridLayout(9, 9));

                result = Sudoku.sudokuGenerator();
                grid = Sudoku.createPuzzle(result, Utils.convertNumberToRemove(level));

                for (int i = 0; i < 9; i++)
                    for (int j = 0; j < 9; j++) {
                        buttons[i][j] = getJButton(i, j);
                        this.add(buttons[i][j]);
                    }

                for (int i = 0; i < 9; i += 3)
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

                setBackground(clLam);
            }

            public JButton getJButton(int row, int col) {
                JButton btn = new JButton();
                btn.setFont(Utils.createDefaultStyle(30));
                btn.setBackground(clTrang);

                initializeButtonAppearance(btn, row, col);

                btn.setActionCommand(row + " " + col);
                btn.addActionListener(this::handleCellSelection);

                btn.addKeyListener(new SudokuKeyListener());

                return btn;
            }

            private void initializeButtonAppearance(JButton btn, int row, int col) {
                if (grid[row][col] == 0) {
                    btn.setText(" ");
                    btn.setForeground(null);
                } else {
                    btn.setText(String.valueOf(grid[row][col]));
                    btn.setForeground(clDen);
                }
            }

            private void handleCellSelection(ActionEvent e) {
                removeBackground();

                String[] coords = e.getActionCommand().split(" ");
                int r = Integer.parseInt(coords[0]);
                int c = Integer.parseInt(coords[1]);

                row1 = r;
                col1 = c;

                highlightSelection(r, c);
            }

            private void highlightSelection(int row, int col) {
                for (int i = 0; i < 9; i++) {
                    buttons[row][i].setBackground(clXam);
                    buttons[i][col].setBackground(clXam);
                }

                int value = grid[row][col];
                if (value > 0) {
                    for (int i = 0; i < 9; i++) {
                        for (int j = 0; j < 9; j++) {
                            if (grid[i][j] == value) {
                                buttons[i][j].setBackground(clXam);
                            }
                        }
                    }
                }

                buttons[row][col].setBackground(clLam);
            }

            private void removeBackground() {
                for (JButton[] jButtons : buttons) {
                    for (JButton jButton : jButtons) {
                        jButton.setBackground(clTrang);
                    }
                }
            }

            private class SudokuKeyListener extends KeyAdapter {
                @Override
                public void keyPressed(KeyEvent e) {
                    if (!isCellEditable(row1, col1)) return;

                    int keyCode = e.getKeyCode();

                    if (isDeleteKey(keyCode)) {
                        handleDelete();
                        return;
                    }

                    int number = parseNumberInput(keyCode);
                    if (number >= 1 && number <= 9) {
                        updateCell(row1, col1, number);
                    }
                }

                private boolean isCellEditable(int row, int col) {
                    Color fg = buttons[row][col].getForeground();
                    return fg != clVang && fg != clDen;
                }

                private boolean isDeleteKey(int keyCode) {
                    return keyCode == KeyEvent.VK_BACK_SPACE || keyCode == KeyEvent.VK_DELETE;
                }

                private void handleDelete() {
                    if (!buttons[row1][col1].getText().equals(" ")) {
                        updateCell(row1, col1, 0);
                    }
                }

                private int parseNumberInput(int keyCode) {
                    if (keyCode >= KeyEvent.VK_1 && keyCode <= KeyEvent.VK_9) {
                        return keyCode - KeyEvent.VK_0;
                    }
                    if (keyCode >= KeyEvent.VK_NUMPAD1 && keyCode <= KeyEvent.VK_NUMPAD9) {
                        return keyCode - KeyEvent.VK_NUMPAD0;
                    }
                    return 0;
                }

                private void updateCell(int row, int col, int value) {
                    grid[row][col] = value;
                    JButton btn = buttons[row][col];

                    if (value == 0) {
                        btn.setText(" ");
                        btn.setForeground(null);
                    } else {
                        btn.setText(String.valueOf(value));

                        if (Sudoku.checkDone(grid)) {
                            pnCard.add(new GoodEnding(), "GoodEnding");
                            lyCard.show(pnCard, "GoodEnding");
                            return;
                        }

                        if (wrongTimes == Utils.MAX_TIMES_WRONG) {
                            pnCard.add(new BadEnding(), "BadEnding");
                            lyCard.show(pnCard, "BadEnding");
                            return;
                        }

                        if (value != result[row][col]) {
                            wrongTimes += 1;
                        }

                        btn.setForeground(value == result[row][col] ? clVang : clDo);
                    }
                }
            }
        }
    }
}
