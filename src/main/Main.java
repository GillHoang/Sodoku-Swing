package main;

import state.GameState;
import ui.LoginPanel;

import javax.swing.*;

public class Main extends JFrame {
    public static final GameState STATE = GameState.getInstance();

    public Main() {
        super("Sudoku");

        STATE.getPnCard().add(new LoginPanel(), "login");

        this.add(STATE.getPnCard());

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
