package view.swing.ending;

import common.helpers.Utils;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static common.helpers.Colors.*;

public class Ending extends JPanel {

    private final transient List<EndingAction> actions;

    private final String title;
    private final int titleSize;

    public Ending(String title, int titleSize, List<EndingAction> actions) {
        super();

        this.title = title;
        this.titleSize = titleSize;
        this.actions = new ArrayList<>(actions);

        setLayout(new BorderLayout());

        add(new UpPanel(), BorderLayout.NORTH);
        add(new CenterPanel(), BorderLayout.CENTER);
    }

    public record EndingAction(String label, Runnable action) {
    }

    protected void setAction(int index, Runnable action) {
        if (index < 0 || index >= actions.size()) {
            return;
        }
        actions.set(index, new EndingAction(actions.get(index).label(), action));
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

            for (int i = 0; i < actions.size(); i++) {
                EndingAction endingAction = actions.get(i);
                int actionIndex = i;
                JButton btn = new JButton(endingAction.label());
                btn.setFont(Utils.createDefaultStyle(34));
                btn.setBackground(clVang);
                btn.setForeground(clTrang);
                btn.addActionListener(e -> {
                    Runnable action = actions.get(actionIndex).action();
                    if (action != null) {
                        action.run();
                    }
                });
                pnOptions.add(btn);
            }

            GridBagConstraints gbc = new GridBagConstraints();
            add(pnOptions, gbc);

            setBackground(clBe);
        }
    }
}
