import javax.swing.*;
import java.awt.*;

public class LoginPanel extends JPanel {
    static Color beColor = new  Color(242, 235, 225);
    static Color lamColor = new Color(17, 100, 102);
    static Color trangColor = new Color(255, 255, 255);
    static Color vangColor = new Color(224, 159, 62);

    public LoginPanel(JPanel cardPanel, CardLayout cardLayout) {
        setLayout(new BorderLayout());

        add(new UpPanel(), BorderLayout.NORTH);
        add(new CenterPanel(cardPanel, cardLayout), BorderLayout.CENTER);

        setBackground(beColor);
    }

    static class UpPanel extends JPanel {
        public UpPanel() {
            setLayout(new BorderLayout());

            JLabel title = new JLabel("Chơi Sodoku!", JLabel.CENTER);
            title.setFont(new Font("Arial", Font.BOLD, 34));
            title.setForeground(trangColor);

            add(title, BorderLayout.CENTER);

            setBackground(lamColor);
            setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(lamColor, 2),
                    BorderFactory.createEmptyBorder(10, 15, 10, 15)
            ));
        }
    }

    static class CenterPanel extends JPanel {
        private String username;

        public CenterPanel(JPanel cardPanel, CardLayout cardLayout) {
            setLayout(new GridBagLayout());
            setBackground(beColor);

            JPanel contentPanel = new JPanel();
            contentPanel.setLayout(new BorderLayout(0, 20));
            contentPanel.setBackground(beColor);

            JLabel dangNhapTitle = new JLabel("Nhập tên của bạn: ", JLabel.CENTER);
            dangNhapTitle.setFont(new Font("Arial", Font.BOLD, 25));

            JTextField usernameTf = new JTextField(15);
            usernameTf.setFont(new Font("Arial", Font.PLAIN, 20));
            usernameTf.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(lamColor, 3),
                    BorderFactory.createEmptyBorder(10, 15, 10, 15)
            ));

            JButton dangNhapButton = getDangNhapButton(cardPanel, cardLayout, usernameTf);

            contentPanel.add(dangNhapTitle, BorderLayout.NORTH);
            contentPanel.add(usernameTf, BorderLayout.CENTER);
            contentPanel.add(dangNhapButton, BorderLayout.SOUTH);
            contentPanel.setBackground(beColor);
            contentPanel.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(lamColor, 2),
                    BorderFactory.createEmptyBorder(10, 15, 10, 15)
            ));


            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.anchor = GridBagConstraints.CENTER;
            gbc.insets = new Insets(0, 0, 0, 0);

            add(contentPanel, gbc);
        }

        private JButton getDangNhapButton(JPanel cardPanel, CardLayout cardLayout, JTextField usernameTf) {
            JButton dangNhapButton = new JButton("Bắt đầu chơi");
            dangNhapButton.setFont(new Font("Arial", Font.BOLD, 20));
            dangNhapButton.setBackground(vangColor);
            dangNhapButton.setForeground(trangColor);
            dangNhapButton.addActionListener(e -> {
                username = usernameTf.getText();

                cardPanel.add(new SodokuPanel(username), "sodoku");
                cardLayout.show(cardPanel, "sodoku");
            });
            return dangNhapButton;
        }
    }
}
