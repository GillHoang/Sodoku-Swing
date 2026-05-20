package model;

import java.util.Arrays;
import java.util.Objects;

public record GameSnapshot(
        String username,
        int level,
        int[][] board,
        boolean[][] fixedCells,
        int score,
        int mistakes,
        int maxMistakes,
        long elapsedTimeSeconds,
        boolean completed,
        boolean won
) {
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GameSnapshot(
                String username1, int level1, int[][] board1, boolean[][] cells, int score1, int mistakes1,
                int maxMistakes1, long timeSeconds, boolean completed1, boolean won1
        ))) return false;
        return level == level1
                && score == score1
                && mistakes == mistakes1
                && maxMistakes == maxMistakes1
                && elapsedTimeSeconds == timeSeconds
                && completed == completed1
                && won == won1
                && Objects.equals(username, username1)
                && Arrays.deepEquals(board, board1)
                && Arrays.deepEquals(fixedCells, cells);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(username, level, score, mistakes, maxMistakes, elapsedTimeSeconds, completed, won);
        result = 31 * result + Arrays.deepHashCode(board);
        result = 31 * result + Arrays.deepHashCode(fixedCells);
        return result;
    }

    @Override
    public String toString() {
        return "GameSnapshot{" +
                "username='" + username + '\'' +
                ", level=" + level +
                ", board=" + Arrays.deepToString(board) +
                ", fixedCells=" + Arrays.deepToString(fixedCells) +
                ", score=" + score +
                ", mistakes=" + mistakes +
                ", maxMistakes=" + maxMistakes +
                ", elapsedTimeSeconds=" + elapsedTimeSeconds +
                ", completed=" + completed +
                ", won=" + won +
                '}';
    }
}
