package state;

import helpers.Sudoku;
import helpers.Utils;
import observer.GameEvent;
import observer.GameObserver;
import ui.SudokuPanel;
import ui.ending.Ending;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static helpers.Colors.clTrang;

public class GameState {
    public static final String CARD_LOGIN = "login";
    public static final String CARD_CHOOSE_LEVEL = "chooseLevel";
    public static final String CARD_SUDOKU = "SudokuPanel";
    public static final String CARD_GOOD_ENDING = "GoodEnding";
    public static final String CARD_BAD_ENDING = "BadEnding";

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

        timer = new Timer(1000, e ->
                lbTime.setText("Thời gian: " + Utils.formatTime(getElapsedTime())));

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

        if (mistakes >= MAX_MISTAKES) return false;

        board[row][colm] = value;

        notifyCellChanged(row, colm, value);

        return true;
    }

    public int getCell(int row, int col) {
        return board[row][col];
    }

    public int getSolutionCell(int row, int col) {
        return solution[row][col];
    }

    public boolean hasEmptyCell() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (board[i][j] == 0) {
                    return true;
                }
            }
        }
        return false;
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
        if (this.isCompleted == completed) return;

        this.isCompleted = completed;
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

    public void stopTimer() {
        timer.stop();
    }

    public void removeSudokuPanels() {
        for (Component c : pnCard.getComponents()) {
            if (c instanceof SudokuPanel) {
                pnCard.remove(c);
            }
        }
    }

    public boolean hasCard(String cardName) {
        for (Component c : pnCard.getComponents()) {
            String name = c.getName();
            if (cardName.equals(name)) {
                return true;
            }
        }
        return false;
    }

    public void removeEndingPanels() {
        for (Component c : pnCard.getComponents()) {
            if (c instanceof Ending) {
                pnCard.remove(c);
            }
        }
    }

    public void startSudokuGame() {
        stopTimer();
        isCompleted = false;
        removeSudokuPanels();

        SudokuPanel sudokuPanel = new SudokuPanel();
        sudokuPanel.setName(CARD_SUDOKU);
        pnCard.add(sudokuPanel, CARD_SUDOKU);
        lyCard.show(pnCard, CARD_SUDOKU);
        pnCard.revalidate();
        pnCard.repaint();
    }

    public void restartSudokuGame() {
        removeEndingPanels();
        startSudokuGame();
    }

    public void navigateToChooseLevel() {
        stopTimer();
        isCompleted = false;
        removeEndingPanels();
        removeSudokuPanels();
        lyCard.show(pnCard, CARD_CHOOSE_LEVEL);
        pnCard.revalidate();
        pnCard.repaint();
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
