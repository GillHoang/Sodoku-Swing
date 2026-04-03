import javax.swing.*;
import java.awt.*;

public class LoginPanel extends JPanel {
    static Color clBe = new Color(242, 235, 225);
    static Color clLam = new Color(17, 100, 102);
    static Color clTrang = new Color(255, 255, 255);
    static Color clVang = new Color(224, 159, 62);

    public LoginPanel(JPanel pnCard, CardLayout lyCard) {
        setLayout(new BorderLayout());

        add(new UpPanel(), BorderLayout.NORTH);
        add(new CenterPanel(pnCard, lyCard), BorderLayout.CENTER);

        setBackground(clBe);
    }

    static class UpPanel extends JPanel {
        public UpPanel() {
            setLayout(new BorderLayout());

            JLabel lbTitle = new JLabel("Chơi Sodoku!", JLabel.CENTER);
            lbTitle.setFont(createDefaultStyle(34));
            lbTitle.setForeground(clTrang);

            add(lbTitle, BorderLayout.CENTER);

            setBackground(clLam);
            setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(clLam, 2),
                    BorderFactory.createEmptyBorder(10, 15, 10, 15)
            ));
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
            pnContent.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(clLam, 2),
                    BorderFactory.createEmptyBorder(10, 15, 10, 15)
            ));


            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.anchor = GridBagConstraints.CENTER;
            gbc.insets = new Insets(0, 0, 0, 0);

            add(pnContent, gbc);
        }

        private JButton getBtnDangNhap(JPanel pnCard, CardLayout lyCard, JTextField tfUserName) {
            JButton bthDangNhap = new JButton("Bắt đầu chơi");
            bthDangNhap.setFont(createDefaultStyle(20));
            bthDangNhap.setBackground(clVang);
            bthDangNhap.setForeground(clTrang);
            bthDangNhap.addActionListener(e -> {
                String input = tfUserName.getText();

                if (input.isEmpty()) {
                    return;
                }

                username = input;

                pnCard.add(new SodokuPanel(username), "sodoku");
                lyCard.show(pnCard, "sodoku");
            });

            return bthDangNhap;
        }

        private JTextField getTfUsername() {
            JTextField tfUsername = new JTextField(15);
            tfUsername.setFont(createDefaultStyle(20));
            tfUsername.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(clLam, 3),
                    BorderFactory.createEmptyBorder(10, 15, 10, 15)
            ));

            return tfUsername;
        }

        private JLabel getLbDangNhap() {
            JLabel lbDangNhapTitle = new JLabel("Nhập tên của bạn: ", JLabel.CENTER);
            lbDangNhapTitle.setFont(createDefaultStyle(20));

            return lbDangNhapTitle;
        }
    }

    public static Font createDefaultStyle(int size) {
        return new Font("Arial", Font.BOLD, size);
    }
}
