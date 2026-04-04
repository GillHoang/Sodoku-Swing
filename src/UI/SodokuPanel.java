package UI;

import helpers.Utils;

import javax.swing.*;

public class SodokuPanel extends JPanel {
    public SodokuPanel(String username, int level) {
        super();

        String tLevel = Utils.convertNumberToLevel(level);

        add(new JLabel("Hello " + username + ", bạn đang chơi level " + tLevel + "!", JLabel.CENTER));
    }
}
