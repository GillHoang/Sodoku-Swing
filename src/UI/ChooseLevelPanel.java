package UI;

import helpers.Utils;

import javax.swing.*;
import java.awt.*;

import static helpers.Colors.*;

public class ChooseLevelPanel extends JPanel {
    public String username;

    public ChooseLevelPanel(JPanel pnCard, CardLayout lyCard, String username) {
        setLayout(new BorderLayout());

        this.username = username;

        add(new UpPanel(), BorderLayout.NORTH);
        add(new CenterPanel(pnCard, lyCard), BorderLayout.CENTER);

        setBackground(clBe);
    }

    class UpPanel extends JPanel {
        public UpPanel() {
            setLayout(new BorderLayout());

            JLabel lbTitle = new JLabel("Xin chào " + username + "! Vui lòng chọn level phù hợp", JLabel.CENTER);
            lbTitle.setFont(Utils.createDefaultStyle(20));
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
        public CenterPanel(JPanel pnCard, CardLayout lyCard) {
            setLayout(new GridBagLayout());
            setBackground(clBe);

            JPanel pnLevel = new JPanel();
            pnLevel.setLayout(new GridLayout(4, 1));
            pnLevel.setBackground(clBe);
            pnLevel.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(clLam, 2),
                    BorderFactory.createEmptyBorder(10, 15, 10, 15)
            ));

            JLabel lbTitle = new JLabel("Chọn Level: ", JLabel.CENTER);
            lbTitle.setFont(Utils.createDefaultStyle(20));
            pnLevel.add(lbTitle);

            pnLevel.add(createLevelButton("Easy"));
            pnLevel.add(createLevelButton("Medium"));
            pnLevel.add(createLevelButton("Hard"));

            add(pnLevel, new GridBagConstraints());

        }

        private JButton createLevelButton(String level) {
            JButton btnLevel = new JButton(level);
            btnLevel.setFont(Utils.createDefaultStyle(20));
            btnLevel.setBackground(clVang);
            btnLevel.setForeground(clTrang);
            return btnLevel;
        }
    }
}
