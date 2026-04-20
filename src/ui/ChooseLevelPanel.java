package ui;

import helpers.Levels;
import helpers.Utils;
import main.Main;

import javax.swing.*;
import java.awt.*;

import static helpers.Colors.*;

public class ChooseLevelPanel extends JPanel {
    public final String username = Main.STATE.getUsername();

    public ChooseLevelPanel() {
        setLayout(new BorderLayout());

        add(new UpPanel(), BorderLayout.NORTH);
        add(new CenterPanel(), BorderLayout.CENTER);

        setBackground(clBe);
    }

    static class CenterPanel extends JPanel {
        public CenterPanel() {
            setLayout(new GridBagLayout());
            setBackground(clBe);

            JPanel pnLevel = new JPanel();
            pnLevel.setLayout(new GridLayout(4, 1, 0, 10));
            pnLevel.setBackground(clBe);
            pnLevel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(clLam, 2), BorderFactory.createEmptyBorder(10, 15, 10, 15)));

            JLabel lbTitle = new JLabel("Chọn Level: ", SwingConstants.CENTER);
            lbTitle.setFont(Utils.createDefaultStyle(20));
            pnLevel.add(lbTitle);

            Levels.LEVEL_LIST.forEach(level -> pnLevel.add(createLevelButton(level.name())));

            add(pnLevel, new GridBagConstraints());

        }

        private JButton createLevelButton(String level) {
            JButton btnLevel = new JButton(level);
            btnLevel.setFont(Utils.createDefaultStyle(20));
            btnLevel.setBackground(clVang);
            btnLevel.setForeground(clTrang);

            btnLevel.addActionListener(e -> {
                Main.STATE.setLevel(Utils.convertLevelToNumber(level));

                Main.STATE.getPnCard().add(new SudokuPanel(), "SodokuPanel");
                Main.STATE.getLyCard().show(Main.STATE.getPnCard(), "SodokuPanel");
            });

            return btnLevel;
        }
    }

    class UpPanel extends JPanel {
        public UpPanel() {
            setLayout(new BorderLayout());

            JLabel lbTitle = new JLabel("Xin chào " + username + "! Vui lòng chọn level phù hợp", SwingConstants.CENTER);
            lbTitle.setFont(Utils.createDefaultStyle(20));
            lbTitle.setForeground(clTrang);

            add(lbTitle, BorderLayout.CENTER);

            setBackground(clLam);
            setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(clLam, 2), BorderFactory.createEmptyBorder(10, 15, 10, 15)));
        }
    }
}
