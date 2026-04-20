package state;

import helpers.Sudoku;
import helpers.Utils;

import javax.swing.*;
import java.awt.*;

public class GameState {
    private static GameState instance;
    private int level;
    private int mistakes;
    private boolean isCompleted;
    private int[][] board;
    private int[][] solution;
    private long startTime;
    private String username;
    private final JPanel pnCard;
    private final CardLayout lyCard;

    private GameState() {
        solution = new int[9][9];
        board = new int[9][9];
        mistakes = 0;
        isCompleted = false;

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

    public void init() {
        solution = Sudoku.sudokuGenerator();
        board = Sudoku.createPuzzle(solution, Utils.convertNumberToRemove(level));
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
        return (System.currentTimeMillis() - startTime) / 1000;
    }

    public int getMistakes() {
        return mistakes;
    }

    public void incrementMistakes() {
        this.mistakes++;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        this.isCompleted = completed;
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
}
