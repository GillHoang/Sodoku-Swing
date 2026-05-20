package view.swing;

import common.helpers.Levels;
import common.helpers.Utils;
import view.ChooseLevelView;

import javax.swing.*;
import java.awt.*;
import java.util.function.IntConsumer;

import static common.helpers.Colors.*;

public class ChooseLevelPanel extends JPanel implements ChooseLevelView {
    private final UpPanel upPanel;
    private transient IntConsumer onLevelSelected;

    public ChooseLevelPanel() {
        setLayout(new BorderLayout());

        upPanel = new UpPanel();
        add(upPanel, BorderLayout.NORTH);
        add(new CenterPanel(this::handleLevelSelected), BorderLayout.CENTER);

        setBackground(clBe);
    }

    @Override
    public void setUsername(String username) {
        upPanel.setUsername(username);
    }

    @Override
    public void setLevelSelectHandler(IntConsumer onLevelSelected) {
        this.onLevelSelected = onLevelSelected;
    }

    private void handleLevelSelected(int level) {
        if (onLevelSelected != null) {
            onLevelSelected.accept(level);
        }
    }

    static class CenterPanel extends JPanel {
        private final transient IntConsumer onLevelSelected;

        public CenterPanel(IntConsumer onLevelSelected) {
            this.onLevelSelected = onLevelSelected;
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

            btnLevel.addActionListener(e -> onLevelSelected.accept(Utils.convertLevelToNumber(level)));

            return btnLevel;
        }
    }

    static class UpPanel extends JPanel {
        private final JLabel lbTitle;

        public UpPanel() {
            setLayout(new BorderLayout());

            lbTitle = new JLabel("Xin chào! Vui lòng chọn level phù hợp", SwingConstants.CENTER);
            lbTitle.setFont(Utils.createDefaultStyle(20));
            lbTitle.setForeground(clTrang);

            add(lbTitle, BorderLayout.CENTER);

            setBackground(clLam);
            setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(clLam, 2), BorderFactory.createEmptyBorder(10, 15, 10, 15)));
        }

        public void setUsername(String username) {
            lbTitle.setText("Xin chào " + username + "! Vui lòng chọn level phù hợp");
        }
    }
}
