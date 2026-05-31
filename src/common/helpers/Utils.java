package common.helpers;

import common.entity.Level;

import java.awt.*;

public class Utils {
    private Utils() {
    }

    public static Font createDefaultStyle(int size) {
        return new Font("Arial", Font.BOLD, size);
    }

    public static int convertLevelToNumber(String level) {
        return findLevelByName(level).value();
    }

    public static String convertNumberToLevel(int level) {
        return findLevelByValue(level).name();
    }

    public static int convertNumberToRemove(int level) {
        return findLevelByValue(level).removeParts();
    }

    private static Level findLevelByName(String name) {
        return Levels.LEVEL_LIST.stream()
                .filter(level -> level.name().equals(name))
                .findFirst()
                .orElse(Levels.HARD);
    }

    private static Level findLevelByValue(int value) {
        return Levels.LEVEL_LIST.stream()
                .filter(level -> level.value() == value)
                .findFirst()
                .orElse(Levels.HARD);
    }

    public static String formatTime(long elapsedSeconds) {
        long minutes = elapsedSeconds / 60;
        long seconds = elapsedSeconds % 60;

        return String.format("%02d:%02d", minutes, seconds);
    }
}
