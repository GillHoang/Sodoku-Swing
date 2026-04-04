package ui;

import helpers.Levels;
import helpers.Utils;

import javax.swing.*;
import java.awt.*;

import static helpers.Colors.*;

public class ChooseLevelPanel extends JPanel {
    public static String username;
    public static int chosenLevel = 0;

    public ChooseLevelPanel(JPanel pnCard, CardLayout lyCard, String username) {
        setLayout(new BorderLayout());

        ChooseLevelPanel.username = username;

        add(new UpPanel(), BorderLayout.NORTH);
        add(new CenterPanel(pnCard, lyCard), BorderLayout.CENTER);

        setBackground(clBe);
    }

    static class UpPanel extends JPanel {
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
        private final JPanel pnCard;
        private final CardLayout lyCard;
        public CenterPanel(JPanel pnCard, CardLayout lyCard) {
            this.pnCard = pnCard;
            this.lyCard = lyCard;

            setLayout(new GridBagLayout());
            setBackground(clBe);

            JPanel pnLevel = new JPanel();
            pnLevel.setLayout(new GridLayout(4, 1, 0, 10));
            pnLevel.setBackground(clBe);
            pnLevel.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(clLam, 2),
                    BorderFactory.createEmptyBorder(10, 15, 10, 15)
            ));

            JLabel lbTitle = new JLabel("Chọn Level: ", JLabel.CENTER);
            lbTitle.setFont(Utils.createDefaultStyle(20));
            pnLevel.add(lbTitle);

            pnLevel.add(createLevelButton(Levels.EASY));
            pnLevel.add(createLevelButton(Levels.MEDIUM));
            pnLevel.add(createLevelButton(Levels.HARD));

            add(pnLevel, new GridBagConstraints());

        }

        private JButton createLevelButton(String level) {
            JButton btnLevel = new JButton(level);
            btnLevel.setFont(Utils.createDefaultStyle(20));
            btnLevel.setBackground(clVang);
            btnLevel.setForeground(clTrang);

            btnLevel.addActionListener(e -> {
                chosenLevel = Utils.convertLevelToNumber(level);

                pnCard.add(new SudokuPanel(username, chosenLevel), "SodokuPanel");
                lyCard.show(pnCard, "SodokuPanel");
            });

            return btnLevel;
        }
    }
}
