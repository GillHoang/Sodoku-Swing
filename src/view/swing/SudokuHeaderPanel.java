package view.swing;

import common.helpers.Utils;

import javax.swing.*;
import java.awt.*;

import static common.helpers.Colors.*;

public class SudokuHeaderPanel extends JPanel {
    private final JLabel lbTitle = new JLabel("", SwingConstants.CENTER);

    public SudokuHeaderPanel() {
        super(new BorderLayout());
        lbTitle.setFont(Utils.createDefaultStyle(20));
        lbTitle.setForeground(clTrang);
        add(lbTitle, BorderLayout.CENTER);
        setBackground(clLam);
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(clLam, 2),
                BorderFactory.createEmptyBorder(10, 15, 10, 15)));
    }

    public void setTitle(String username, String levelName) {
        lbTitle.setText("Xin chào " + username + ", bạn đã chọn level " + levelName);
    }
}
