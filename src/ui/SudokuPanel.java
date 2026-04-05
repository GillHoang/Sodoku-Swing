package ui;

import helpers.Colors;
import helpers.Sudoku;
import helpers.Utils;

import javax.swing.*;
import java.awt.*;

import static helpers.Colors.clLam;
import static helpers.Colors.clTrang;

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
            public BoardPanel() {
                super();

                setLayout(new GridLayout(3, 3));

                int[][] generated = Sudoku.sudokuGenerator(Utils.convertNumberToRemove(level));

                for (int i = 0; i < 9; i++) {
                    JPanel pnCon = new JPanel();
                    pnCon.setLayout(new GridLayout(3, 3));

                    pnCon.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(clLam, 2), BorderFactory.createEmptyBorder(2, 2, 2, 2)));

                    for (int j = 0; j < 9; j++) {
                        JButton btn = new JButton();
                        btn.setFont(Utils.createDefaultStyle(30));

                        int num = generated[i][j];

                        if (num != 0) {
                            btn.setText(String.valueOf(generated[i][j]));
                        }

                        btn.setBackground(clTrang);

                        pnCon.add(btn);
                    }

                    add(pnCon);
                }

                setBackground(clLam);
            }
        }
    }
}
