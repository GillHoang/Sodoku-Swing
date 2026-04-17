package ui;

import helpers.Colors;
import helpers.Sudoku;
import helpers.Utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import static helpers.Colors.*;

public class SudokuPanel extends JPanel {
    public final int level;
    public final String username;

    public SudokuPanel(String username, int level) {
        super();

        this.level = level;
        this.username = username;

        setLayout(new BorderLayout());

        setBackground(Colors.clBe);

        add(new UpPanel(), BorderLayout.NORTH);
        add(new CenterPanel(), BorderLayout.CENTER);
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
        public CenterPanel() {
            super();

            setLayout(new BorderLayout());
            setBackground(clLam);

            add(new InformationPanel(), BorderLayout.NORTH);
            add(new BoardPanel(), BorderLayout.CENTER);
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
            private final JButton[][] bt = new JButton[9][9];
            private final int[][] grid;
            private final int[][] result;
            private int row1;
            private int col1;

            public BoardPanel() {
                super();

                setLayout(new GridLayout(9, 9));

                result = Sudoku.sudokuGenerator();
                grid = Sudoku.createPuzzle(result, Utils.convertNumberToRemove(level));

                for (int i = 0; i < 9; i++)
                    for (int j = 0; j < 9; j++) {
                        bt[i][j] = getJButton(grid, i, j);
                        this.add(bt[i][j]);
                    }
                for (int i = 0; i < 9; i += 3)
                    for (int j = 0; j < 9; j += 3) {
                        bt[i][j].setBorder(BorderFactory.createMatteBorder(3, 3, 1, 1, Color.black));
                        bt[i][j + 2].setBorder(BorderFactory.createMatteBorder(3, 1, 1, 3, Color.black));
                        bt[i + 2][j + 2].setBorder(BorderFactory.createMatteBorder(1, 1, 3, 3, Color.black));
                        bt[i + 2][j].setBorder(BorderFactory.createMatteBorder(1, 3, 3, 1, Color.black));
                        bt[i][j + 1].setBorder(BorderFactory.createMatteBorder(3, 1, 1, 1, Color.black));
                        bt[i + 1][j + 2].setBorder(BorderFactory.createMatteBorder(1, 1, 1, 3, Color.black));
                        bt[i + 2][j + 1].setBorder(BorderFactory.createMatteBorder(1, 1, 3, 1, Color.black));
                        bt[i + 1][j].setBorder(BorderFactory.createMatteBorder(1, 3, 1, 1, Color.black));
                        bt[i + 1][j + 1].setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.black));
                    }

                setBackground(clLam);
            }

            public JButton getJButton(int[][] grid, int row, int col) {
                JButton btn = new JButton();
                btn.setFont(Utils.createDefaultStyle(30));
                btn.setBackground(clTrang);

                if (grid[row][col] == 0) {
                    btn.setText(" ");
                    btn.setForeground(null);
                } else {
                    btn.setText(String.valueOf(grid[row][col]));
                    btn.setForeground(clDen);
                }
                btn.setActionCommand(row + " " + col);
                btn.addActionListener(e -> {
                    removeBackground();
                    String s = e.getActionCommand();
                    int k = s.indexOf(32);
                    int i = Integer.parseInt(s.substring(0, k));
                    int j = Integer.parseInt(s.substring(k + 1));
                    row1 = i;
                    col1 = j;

                    for (i = 0; i < 9; i++) {
                        bt[row1][i].setBackground(clXam);
                        bt[i][col1].setBackground(clXam);
                    }

                    if (this.grid[row1][col1] > 0) {
                        for (i = 0; i < 9; i++)
                            for (j = 0; j < 9; j++)
                                if (this.grid[i][j] == this.grid[row1][col1]) bt[i][j].setBackground(clXam);
                    }

                    bt[row1][col1].setBackground(clLam);
                });

                btn.addKeyListener(new KeyAdapter() {
                    @Override
                    public void keyPressed(KeyEvent e) {
                        int v = e.getKeyCode();
                        if ((v >= 49 && v <= 57) || (v >= 97 && v <= 105)) {
                            if (v <= 57) v -= 48;
                            if (v >= 97) v -= 96;

                            if (bt[row1][col1].getForeground() == clVang || bt[row1][col1].getForeground() == clDen)
                                return;

                            if (BoardPanel.this.grid[row1][col1] == 0) {
                                grid[row1][col1] = v;
                                bt[row1][col1].setText(String.valueOf(v));
                                if (v == result[row1][col1]) bt[row1][col1].setForeground(clVang);
                                else bt[row1][col1].setForeground(clDo);
                            } else if (BoardPanel.this.grid[row1][col1] != 0 && v != result[row1][col1]) {
                                grid[row1][col1] = v;
                                bt[row1][col1].setText(String.valueOf(v));
                                bt[row1][col1].setForeground(clDo);
                            } else if (BoardPanel.this.grid[row1][col1] != 0 && v == result[row1][col1]) {
                                grid[row1][col1] = v;
                                bt[row1][col1].setText(String.valueOf(v));
                                bt[row1][col1].setForeground(clVang);
                            }
                        }
                    }
                });

                return btn;
            }

            private void removeBackground() {
                for (JButton[] jButtons : bt) {
                    for (JButton jButton : jButtons) {
                        jButton.setBackground(clTrang);
                    }
                }
            }
        }
    }
}
