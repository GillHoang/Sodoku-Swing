package ui;

import helpers.Utils;
import main.Main;
import state.GameState;

import javax.swing.*;
import java.awt.*;

import static helpers.Colors.*;

public class LoginPanel extends JPanel {
    public LoginPanel() {
        setLayout(new BorderLayout());

        add(new UpPanel(), BorderLayout.NORTH);
        add(new CenterPanel(), BorderLayout.CENTER);

        setBackground(clBe);
    }

    static class UpPanel extends JPanel {
        public UpPanel() {
            setLayout(new BorderLayout());

            JLabel lbTitle = new JLabel("Chơi Sudoku!", SwingConstants.CENTER);
            lbTitle.setFont(Utils.createDefaultStyle(34));
            lbTitle.setForeground(clTrang);

            add(lbTitle, BorderLayout.CENTER);

            setBackground(clLam);
            setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(clLam, 2), BorderFactory.createEmptyBorder(10, 15, 10, 15)));
        }
    }

    static class CenterPanel extends JPanel {
        public CenterPanel() {
            setLayout(new GridBagLayout());
            setBackground(clBe);

            JPanel pnContent = new JPanel();
            pnContent.setLayout(new BorderLayout(0, 20));
            pnContent.setBackground(clBe);

            JLabel lbDangNhap = getLbDangNhap();

            JTextField tfUserName = getTfUsername();

            JButton btnDangNhap = getBtnDangNhap(tfUserName);

            pnContent.add(lbDangNhap, BorderLayout.NORTH);
            pnContent.add(tfUserName, BorderLayout.CENTER);
            pnContent.add(btnDangNhap, BorderLayout.SOUTH);
            pnContent.setBackground(clBe);
            pnContent.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(clLam, 2), BorderFactory.createEmptyBorder(10, 15, 10, 15)));

            add(pnContent, new GridBagConstraints());
        }

        private JButton getBtnDangNhap(JTextField tfUserName) {
            JButton btnDangNhap = new JButton("Bắt đầu chơi");
            btnDangNhap.setFont(Utils.createDefaultStyle(20));
            btnDangNhap.setBackground(clVang);
            btnDangNhap.setForeground(clTrang);
            btnDangNhap.addActionListener(e -> {
                String input = tfUserName.getText();

                if (input.isEmpty()) {
                    return;
                }

                Main.STATE.setUsername(input);
                if (!Main.STATE.hasCard(GameState.CARD_CHOOSE_LEVEL)) {
                    ChooseLevelPanel chooseLevelPanel = new ChooseLevelPanel();
                    chooseLevelPanel.setName(GameState.CARD_CHOOSE_LEVEL);
                    Main.STATE.getPnCard().add(chooseLevelPanel, GameState.CARD_CHOOSE_LEVEL);
                }
                Main.STATE.getLyCard().show(Main.STATE.getPnCard(), GameState.CARD_CHOOSE_LEVEL);
            });

            return btnDangNhap;
        }

        private JTextField getTfUsername() {
            JTextField tfUsername = new JTextField(15);
            tfUsername.setFont(Utils.createDefaultStyle(20));
            tfUsername.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(clLam, 3), BorderFactory.createEmptyBorder(10, 15, 10, 15)));

            return tfUsername;
        }

        private JLabel getLbDangNhap() {
            JLabel lbDangNhapTitle = new JLabel("Nhập tên của bạn: ", SwingConstants.CENTER);
            lbDangNhapTitle.setFont(Utils.createDefaultStyle(20));

            return lbDangNhapTitle;
        }
    }

}
