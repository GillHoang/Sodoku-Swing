package ui;

import helpers.Utils;

import javax.swing.*;
import java.awt.*;

import static helpers.Colors.*;

public class BadEnding extends JPanel {
    public BadEnding() {
        super();

        setLayout(new BorderLayout());

        add(new UpPanel(), BorderLayout.NORTH);
        add(new CenterPanel(), BorderLayout.CENTER);
    }

    static class UpPanel extends JPanel {
        public UpPanel() {
            super();

            setLayout(new BorderLayout());

            JLabel lbTitle = new JLabel("Bạn đã quá số lần thử cho phép!", SwingConstants.CENTER);
            lbTitle.setFont(Utils.createDefaultStyle(34));
            lbTitle.setForeground(clTrang);

            add(lbTitle, BorderLayout.CENTER);

            setBackground(clLam);
            setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(clLam, 2), BorderFactory.createEmptyBorder(10, 15, 10, 15)));
        }
    }

    static class CenterPanel extends JPanel {
        public CenterPanel() {
            super();

            setLayout(new GridBagLayout());

            JPanel pnOptions = new JPanel();
            pnOptions.setLayout(new GridLayout(2, 1, 0, 10));
            pnOptions.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(clLam, 2), BorderFactory.createEmptyBorder(10, 15, 10, 15)));
            pnOptions.setBackground(clBe);

            JButton tryAgain = new JButton("Chơi lại");
            tryAgain.setFont(Utils.createDefaultStyle(34));
            tryAgain.setBackground(clVang);
            tryAgain.setForeground(clTrang);

            JButton chooseLevel = new JButton("Chọn level khác");
            chooseLevel.setFont(Utils.createDefaultStyle(34));
            chooseLevel.setBackground(clVang);
            chooseLevel.setForeground(clTrang);

            pnOptions.add(tryAgain);
            pnOptions.add(chooseLevel);

            GridBagConstraints gbc = new GridBagConstraints();
            add(pnOptions, gbc);

            setBackground(clBe);
        }
    }
}
