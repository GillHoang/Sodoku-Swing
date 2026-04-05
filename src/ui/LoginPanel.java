package ui;

import helpers.Utils;

import javax.swing.*;
import java.awt.*;

import static helpers.Colors.*;

public class LoginPanel extends JPanel {
    public LoginPanel(JPanel pnCard, CardLayout lyCard) {
        setLayout(new BorderLayout());

        add(new UpPanel(), BorderLayout.NORTH);
        add(new CenterPanel(pnCard, lyCard), BorderLayout.CENTER);

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
        private String username;

        public CenterPanel(JPanel pnCard, CardLayout lyCard) {
            setLayout(new GridBagLayout());
            setBackground(clBe);

            JPanel pnContent = new JPanel();
            pnContent.setLayout(new BorderLayout(0, 20));
            pnContent.setBackground(clBe);

            JLabel lbDangNhap = getLbDangNhap();

            JTextField tfUserName = getTfUsername();

            JButton btnDangNhap = getBtnDangNhap(pnCard, lyCard, tfUserName);

            pnContent.add(lbDangNhap, BorderLayout.NORTH);
            pnContent.add(tfUserName, BorderLayout.CENTER);
            pnContent.add(btnDangNhap, BorderLayout.SOUTH);
            pnContent.setBackground(clBe);
            pnContent.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(clLam, 2), BorderFactory.createEmptyBorder(10, 15, 10, 15)));

            add(pnContent, new GridBagConstraints());
        }

        private JButton getBtnDangNhap(JPanel pnCard, CardLayout lyCard, JTextField tfUserName) {
            JButton bthDangNhap = new JButton("Bắt đầu chơi");
            bthDangNhap.setFont(Utils.createDefaultStyle(20));
            bthDangNhap.setBackground(clVang);
            bthDangNhap.setForeground(clTrang);
            bthDangNhap.addActionListener(e -> {
                String input = tfUserName.getText();

                if (input.isEmpty()) {
                    return;
                }

                username = input;

                pnCard.add(new ChooseLevelPanel(pnCard, lyCard, username), "chooseLevel");
                lyCard.show(pnCard, "chooseLevel");
            });

            return bthDangNhap;
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
