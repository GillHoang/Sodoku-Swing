package controller.navigation;

import javax.swing.*;
import java.awt.*;

public class CardNavigator {
    private final JPanel rootPanel;
    private final CardLayout cardLayout;

    public CardNavigator(JPanel rootPanel, CardLayout cardLayout) {
        this.rootPanel = rootPanel;
        this.cardLayout = cardLayout;
    }

    public void register(AppScreen screen, Component component) {
        rootPanel.add(component, screen.cardName());
    }

    public void show(AppScreen screen) {
        cardLayout.show(rootPanel, screen.cardName());
        rootPanel.revalidate();
        rootPanel.repaint();
    }
}
