package main;

import observer.impl.BadEndingObserver;
import observer.impl.GoodEndingObserver;
import observer.impl.MistakeObserver;
import observer.impl.ScoreObserver;
import state.GameState;
import ui.LoginPanel;

import javax.swing.*;

public class Main extends JFrame {
    public static final GameState STATE = GameState.getInstance();

    public Main() {
        super("Sudoku");

        STATE.addObserver(new GoodEndingObserver());
        STATE.addObserver(new BadEndingObserver());
        STATE.addObserver(new MistakeObserver());
        STATE.addObserver(new ScoreObserver());

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
