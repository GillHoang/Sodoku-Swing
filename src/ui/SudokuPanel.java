package ui;

import helpers.Colors;
import helpers.Sudoku;
import helpers.Utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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
            public final JButton[][] bt = new JButton[9][9];

            public BoardPanel() {
                super();

                setLayout(new GridLayout(9, 9));

                int numberToRemove = Utils.convertNumberToRemove(level);
                int[][] original = Sudoku.sudokuGenerator(false, numberToRemove);
                int[][] grid = Sudoku.sudokuGenerator(true, numberToRemove);


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

            private static JButton getJButton(int[][] grid, int row, int col) {
                JButton btn = new JButton();
                btn.setFont(Utils.createDefaultStyle(30));

                if (grid[row][col] == 0) {
                    btn.setText(" ");
                } else {
                    btn.setText(String.valueOf(grid[row][col]));
                }
                btn.setActionCommand(row + " " + col);


                btn.setBackground(clTrang);

                btn.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseEntered(MouseEvent e) {
                        e.getComponent().setBackground(clXam);
                    }

                    @Override
                    public void mouseExited(MouseEvent e) {
                        e.getComponent().setBackground(clTrang);
                    }

                    @Override
                    public void mouseClicked(MouseEvent e) {

                    }
                });
                return btn;
            }
        }
    }
}
