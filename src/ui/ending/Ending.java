package ui.ending;

import helpers.Utils;

import javax.swing.*;

import java.awt.*;
import java.util.List;

import static helpers.Colors.*;

public class Ending extends JPanel {
    private final String title;
    private final int titleSize;
    private final List<String> buttonTexts;

    public Ending(String title, int titleSize, List<String> buttonTexts) {
        super();

        this.title = title;
        this.titleSize = titleSize;
        this.buttonTexts = buttonTexts;

        setLayout(new BorderLayout());

        add(new UpPanel(), BorderLayout.NORTH);
        add(new CenterPanel(), BorderLayout.CENTER);
    }

    class UpPanel extends JPanel {
        public UpPanel() {
            super();

            setLayout(new BorderLayout());

            JLabel lbTitle = new JLabel(title, SwingConstants.CENTER);
            lbTitle.setFont(Utils.createDefaultStyle(titleSize));
            lbTitle.setForeground(clTrang);

            add(lbTitle, BorderLayout.CENTER);

            setBackground(clLam);
            setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(clLam, 2), BorderFactory.createEmptyBorder(10, 15, 10, 15)));
        }
    }

    class CenterPanel extends JPanel {
        public CenterPanel() {
            super();

            setLayout(new GridBagLayout());

            JPanel pnOptions = new JPanel();
            pnOptions.setLayout(new GridLayout(buttonTexts.size(), 1, 0, 10));
            pnOptions.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(clLam, 2), BorderFactory.createEmptyBorder(10, 15, 10, 15)));
            pnOptions.setBackground(clBe);

            buttonTexts.forEach(text -> {
                JButton btn = new JButton(text);
                btn.setFont(Utils.createDefaultStyle(34));
                btn.setBackground(clVang);
                btn.setForeground(clTrang);
                pnOptions.add(btn);
            });

            GridBagConstraints gbc = new GridBagConstraints();
            add(pnOptions, gbc);

            setBackground(clBe);
        }
    }
}
