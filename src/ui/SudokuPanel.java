package ui;

import helpers.Colors;
import helpers.Utils;
import listeners.SudokuKeyListener;
import main.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

import static helpers.Colors.*;

public class SudokuPanel extends JPanel {
    public SudokuPanel() {
        super();

        Main.STATE.startTimer();

        setLayout(new BorderLayout());

        setBackground(Colors.clBe);

        add(new UpPanel(), BorderLayout.NORTH);
        add(new CenterPanel(), BorderLayout.CENTER);
    }

    static class CenterPanel extends JPanel {
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
                setLayout(new BorderLayout());

                JPanel pnScore = new JPanel();
                int score = Main.STATE.getScore();
                JLabel lbScore = Main.STATE.getLbScore();
                lbScore.setText("Điểm: " + score);
                pnScore.add(lbScore);
                pnScore.setBackground(clLam);

                JPanel pnTime = new JPanel();
                long time = Main.STATE.getElapsedTime();
                JLabel lbTime = Main.STATE.getLbTime();
                lbTime.setText("Thời gian: " + Utils.formatTime(time));
                pnTime.add(lbTime);
                pnTime.setBackground(clLam);

                Timer timer = Main.STATE.getTimer();
                timer.start();

                JPanel pnMistake = new JPanel();
                int mistakes = Main.STATE.getMistakes();
                JLabel lbMistake = Main.STATE.getLbMistakes();
                lbMistake.setText("Lỗi: " + mistakes + "/" + Main.STATE.getMaxMistakes());
                pnMistake.add(lbMistake);
                pnMistake.setBackground(clLam);


                add(pnScore, BorderLayout.WEST);
                add(pnTime, BorderLayout.CENTER);
                add(pnMistake, BorderLayout.EAST);
            }
        }

        // TODO: sẽ thực hiện này sau
        // Cơ chế là ấn vào button số thì sẽ làm button đậm màu hơn
        // sau đó chọn vào các ô trống thì sẽ hiên lên số vừa chọn.
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

        static class BoardPanel extends JPanel {
            private final JButton[][] buttons = new JButton[9][9];

            public BoardPanel() {
                super();

                Main.STATE.init();

                setLayout(new GridLayout(9, 9));

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

                btn.addKeyListener(new SudokuKeyListener(row, col, buttons));

                return btn;
            }

            private void initializeButtonAppearance(JButton btn, int row, int col) {
                if (Main.STATE.getBoard()[row][col] == 0) {
                    btn.setText(" ");
                    btn.setForeground(null);
                } else {
                    btn.setText(String.valueOf(Main.STATE.getBoard()[row][col]));
                    btn.setForeground(clDen);
                }
            }

            private void handleCellSelection(ActionEvent e) {
                removeBackground();

                String[] coords = e.getActionCommand().split(" ");
                int r = Integer.parseInt(coords[0]);
                int c = Integer.parseInt(coords[1]);

                highlightSelection(r, c);
            }

            private void highlightSelection(int row, int col) {
                for (int i = 0; i < 9; i++) {
                    buttons[row][i].setBackground(clXam);
                    buttons[i][col].setBackground(clXam);
                }

                int value = Main.STATE.getBoard()[row][col];
                if (value > 0) {
                    for (int i = 0; i < 9; i++) {
                        for (int j = 0; j < 9; j++) {
                            if (Main.STATE.getBoard()[i][j] == value) {
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
        }
    }

    static class UpPanel extends JPanel {
        public UpPanel() {
            super();

            setLayout(new BorderLayout());

            JLabel lbTitle = new JLabel("Xin chào " + Main.STATE.getUsername() + ", bạn đã chọn level " + Utils.convertNumberToLevel(Main.STATE.getLevel()), SwingConstants.CENTER);
            lbTitle.setFont(Utils.createDefaultStyle(20));
            lbTitle.setForeground(clTrang);

            add(lbTitle, BorderLayout.CENTER);

            setBackground(clLam);
            setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(clLam, 2), BorderFactory.createEmptyBorder(10, 15, 10, 15)));
        }
    }
}
