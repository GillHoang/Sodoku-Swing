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

        STATE.addObserver(new MistakeObserver());
        STATE.addObserver(new ScoreObserver());
        STATE.addObserver(new BadEndingObserver());
        STATE.addObserver(new GoodEndingObserver());

        STATE.getPnCard().add(new LoginPanel(), GameState.CARD_LOGIN);

        this.add(STATE.getPnCard());

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
