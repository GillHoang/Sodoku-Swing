package state;

import helpers.Sudoku;
import helpers.Utils;
import main.Main;
import observer.GameEvent;
import observer.GameObserver;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static helpers.Colors.clTrang;

public class GameState {
    private static final int MAX_MISTAKES = 3;
    private static GameState instance;
    private final JPanel pnCard;
    private final JLabel lbScore;
    private final JLabel lbTime;
    private final JLabel lbMistakes;
    private final CardLayout lyCard;
    private final List<GameObserver> observers = new ArrayList<>();
    private final Timer timer;
    private int level;
    private int mistakes;
    private boolean isCompleted;
    private int[][] board;
    private int[][] solution;
    private long startTime;
    private String username;
    private int score;

    private GameState() {
        solution = new int[9][9];
        board = new int[9][9];
        mistakes = 0;
        isCompleted = false;

        lbScore = new JLabel("Điểm: ");
        lbScore.setFont(Utils.createDefaultStyle(20));
        lbScore.setForeground(clTrang);

        lbTime = new JLabel("Thời gian: ");
        lbTime.setFont(Utils.createDefaultStyle(20));
        lbTime.setForeground(clTrang);

        lbMistakes = new JLabel("Lỗi: ");
        lbMistakes.setFont(Utils.createDefaultStyle(20));
        lbMistakes.setForeground(clTrang);

        timer = new Timer(1000, e -> {
            long elapsed = Main.STATE.getElapsedTime();
            Main.STATE.getLbTime().setText("Thời gian: " + Utils.formatTime(elapsed));
        });

        pnCard = new JPanel();
        lyCard = new CardLayout();
        pnCard.setLayout(lyCard);
    }

    public static GameState getInstance() {
        if (instance == null) {
            instance = new GameState();
        }

        return instance;
    }

    public void addObserver(GameObserver go) {
        observers.add(go);
    }

    public void init() {
        solution = Sudoku.sudokuGenerator();
        board = Sudoku.createPuzzle(solution, Utils.convertNumberToRemove(level));
        mistakes = 0;
        isCompleted = false;
        score = 0;

        notifyEvent(GameEvent.GAME_RESET);
    }

    public boolean setCellValue(int row, int colm, int value) {

        if (mistakes == MAX_MISTAKES) return false;

        board[row][colm] = value;

        notifyCellChanged(row, colm, value);

        return true;
    }

    public int[][] getBoard() {
        return board;
    }

    public int[][] getSolution() {
        return solution;
    }

    public void startTimer() {
        this.startTime = System.currentTimeMillis();
    }

    public long getElapsedTime() {
        if (startTime == 0) {
            return 0;
        }

        return (System.currentTimeMillis() - startTime) / 1000;
    }

    public int getMistakes() {
        return mistakes;
    }

    public void incrementMistakes() {
        this.mistakes++;

        notifyEvent(GameEvent.MISTAKE);
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        this.isCompleted = completed;
        if (completed) {
            notifyEvent(GameEvent.GAME_COMPLETED);
        }
    }

    public void setLost() {
        notifyEvent(GameEvent.GAME_LOST);
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public JPanel getPnCard() {
        return pnCard;
    }

    public CardLayout getLyCard() {
        return lyCard;
    }

    public int getScore() {
        return score;
    }

    public void addScore(int score) {
        if (this.score < 0) return;
        this.score += score;
    }

    public JLabel getLbScore() {
        return lbScore;
    }

    public JLabel getLbTime() {
        return lbTime;
    }

    public JLabel getLbMistakes() {
        return lbMistakes;
    }

    public Timer getTimer() {
        return timer;
    }

    public int getMaxMistakes() {
        return MAX_MISTAKES;
    }

    public boolean checkDone() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (board[i][j] != solution[i][j]) return false;
                if (board[i][j] == 0) return false;
            }
        }

        return true;
    }

    private void notifyCellChanged(int row, int col, int value) {
        for (GameObserver o : observers)
            o.onCellChanged(row, col, value);
    }

    private void notifyEvent(GameEvent event) {
        for (GameObserver o : observers)
            o.onGameStateChanged(event);
    }
}
