import ui.LoginPanel;

import javax.swing.*;
import java.awt.*;

public class Main extends JFrame {
    public Main() {
        super("Sodoku");

        JPanel cardPanel = new JPanel();
        CardLayout cardLayout = new CardLayout();
        cardPanel.setLayout(cardLayout);

        cardPanel.add(new LoginPanel(cardPanel, cardLayout), "login");

        this.add(cardPanel);

        setup();
    }

    public static void main(String[] args) {
        new Main();
    }

    private void setup() {
        this.setSize(600, 800);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

}
