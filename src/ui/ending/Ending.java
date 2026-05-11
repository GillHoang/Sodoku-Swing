package ui.ending;

import helpers.Utils;

import javax.swing.*;
import java.awt.*;
import java.util.List;

import static helpers.Colors.*;

public class Ending extends JPanel {

    private final transient List<EndingAction> actions;

    private final String title;
    private final int titleSize;

    public Ending(String title, int titleSize, List<EndingAction> actions) {
        super();

        this.title = title;
        this.titleSize = titleSize;
        this.actions = actions;

        setLayout(new BorderLayout());

        add(new UpPanel(), BorderLayout.NORTH);
        add(new CenterPanel(), BorderLayout.CENTER);
    }

    public record EndingAction(String label, Runnable action) {
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
            pnOptions.setLayout(new GridLayout(actions.size(), 1, 0, 10));
            pnOptions.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(clLam, 2), BorderFactory.createEmptyBorder(10, 15, 10, 15)));
            pnOptions.setBackground(clBe);

            for (EndingAction endingAction : actions) {
                JButton btn = new JButton(endingAction.label());
                btn.setFont(Utils.createDefaultStyle(34));
                btn.setBackground(clVang);
                btn.setForeground(clTrang);
                btn.addActionListener(e -> endingAction.action().run());
                pnOptions.add(btn);
            }

            GridBagConstraints gbc = new GridBagConstraints();
            add(pnOptions, gbc);

            setBackground(clBe);
        }
    }
}
