package model;

import common.helpers.Sudoku;
import common.helpers.Utils;

public class GameSession {
    private static final int SIZE = 9;
    private static final int MAX_MISTAKES = 3;
    private final int[][] solution = new int[SIZE][SIZE];
    private final int[][] board = new int[SIZE][SIZE];
    private final boolean[][] fixedCells = new boolean[SIZE][SIZE];
    private final boolean[][] wasCorrect = new boolean[SIZE][SIZE];
    private int level;
    private int mistakes;
    private int score;
    private GamePhase phase = GamePhase.PLAYING;
    private long startTime;
    private String username = "";

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? "" : username.trim();
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public GamePhase getPhase() {
        return phase;
    }

    public void startNewGame() {
        int[][] generated = Sudoku.sudokuGenerator();
        int[][] puzzle = Sudoku.createPuzzle(generated, Utils.convertNumberToRemove(level));

        applyGeneratedPuzzle(generated, puzzle);
    }

    void seedBoardForTest(int[][] sol, int[][] brd) {
        for (int i = 0; i < SIZE; i++) {
            System.arraycopy(sol[i], 0, solution[i], 0, SIZE);
            System.arraycopy(brd[i], 0, board[i], 0, SIZE);
            for (int j = 0; j < SIZE; j++) {
                fixedCells[i][j] = board[i][j] != 0;
                wasCorrect[i][j] = board[i][j] != 0 && board[i][j] == solution[i][j];
            }
        }
        mistakes = 0;
        score = 0;
        phase = GamePhase.PLAYING;
        startTime = System.currentTimeMillis();
        if (isSolved()) {
            phase = GamePhase.WON;
        }
    }

    private void applyGeneratedPuzzle(int[][] generated, int[][] puzzle) {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                solution[i][j] = generated[i][j];
                board[i][j] = puzzle[i][j];
                fixedCells[i][j] = board[i][j] != 0;
                wasCorrect[i][j] = board[i][j] != 0 && board[i][j] == solution[i][j];
            }
        }

        mistakes = 0;
        score = 0;
        phase = GamePhase.PLAYING;
        startTime = System.currentTimeMillis();
    }

    public CellInputResult inputCell(int row, int col, int value) {
        if (phase != GamePhase.PLAYING) {
            return CellInputResult.rejected();
        }
        if (!canEditCell(row, col)) {
            return CellInputResult.rejected();
        }

        board[row][col] = value;
        boolean correctNow = value != 0 && value == solution[row][col];

        if (!wasCorrect[row][col] && correctNow) {
            score += 20;
        }
        wasCorrect[row][col] = correctNow;

        boolean incorrectInput = value != 0 && value != solution[row][col];
        if (incorrectInput) {
            mistakes++;
            if (mistakes >= MAX_MISTAKES) {
                phase = GamePhase.LOST;
            }
        } else if (isSolved()) {
            phase = GamePhase.WON;
        }

        boolean completed = phase != GamePhase.PLAYING;
        boolean won = phase == GamePhase.WON;
        return new CellInputResult(true, correctNow, incorrectInput, completed, won);
    }

    public boolean canEditCell(int row, int col) {
        if (phase != GamePhase.PLAYING || mistakes >= MAX_MISTAKES) {
            return false;
        }
        return !fixedCells[row][col] && !isCellCorrect(row, col);
    }

    public boolean isCellCorrect(int row, int col) {
        int value = board[row][col];
        return value != 0 && value == solution[row][col];
    }

    public int getCell(int row, int col) {
        return board[row][col];
    }

    public int getSolutionCell(int row, int col) {
        return solution[row][col];
    }

    public int getMistakes() {
        return mistakes;
    }

    public int getScore() {
        return score;
    }

    public int getMaxMistakes() {
        return MAX_MISTAKES;
    }

    public long getElapsedTimeSeconds() {
        if (startTime == 0) {
            return 0;
        }
        return (System.currentTimeMillis() - startTime) / 1000;
    }

    public boolean isCompleted() {
        return phase != GamePhase.PLAYING;
    }

    public boolean isWon() {
        return phase == GamePhase.WON;
    }

    public GameSnapshot snapshot() {
        int[][] copiedBoard = new int[SIZE][SIZE];
        boolean[][] copiedFixed = new boolean[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++) {
            System.arraycopy(board[i], 0, copiedBoard[i], 0, SIZE);
            System.arraycopy(fixedCells[i], 0, copiedFixed[i], 0, SIZE);
        }
        boolean completed = phase != GamePhase.PLAYING;
        boolean won = phase == GamePhase.WON;
        return new GameSnapshot(
                username,
                level,
                copiedBoard,
                copiedFixed,
                score,
                mistakes,
                MAX_MISTAKES,
                getElapsedTimeSeconds(),
                completed,
                won
        );
    }

    private boolean isSolved() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (board[i][j] == 0 || board[i][j] != solution[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    public record CellInputResult(boolean accepted, boolean correctInput, boolean incorrectInput, boolean completed,
                                  boolean won) {
        public static CellInputResult rejected() {
            return new CellInputResult(false, false, false, false, false);
        }
    }
}
