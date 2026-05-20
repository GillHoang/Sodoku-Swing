package app;

import controller.AppController;

import javax.swing.*;

public class Main extends JFrame {

    public Main() {
        super("Sudoku");
        AppController appController = new AppController();
        this.add(appController.getRootPanel());

        setup();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::new);
    }

    private void setup() {
        this.setSize(600, 700);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

}
