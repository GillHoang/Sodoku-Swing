package helpers;

import java.awt.*;

public class Utils {
    private Utils() {
    }

    public static Font createDefaultStyle(int size) {
        return new Font("Arial", Font.BOLD, size);
    }

    public static int convertLevelToNumber(String level) {
        if (level.equals(Levels.EASY.name())) return Levels.EASY.value();
        else if (level.equals(Levels.MEDIUM.name())) return Levels.MEDIUM.value();
        else return Levels.HARD.value();
    }

    public static String convertNumberToLevel(int level) {
        if (level == Levels.EASY.value()) return Levels.EASY.name();
        else if (level == Levels.MEDIUM.value()) return Levels.MEDIUM.name();
        return Levels.HARD.name();
    }

    public static int convertNumberToRemove(int level) {
        if (level == Levels.EASY.value()) return Levels.EASY.removeParts();
        else if (level == Levels.MEDIUM.value()) return Levels.MEDIUM.removeParts();
        return Levels.HARD.removeParts();
    }

    public static String formatTime(long millis) {
        long minutes = millis / 60;
        long seconds = millis % 60;

        return String.format("%02d:%02d", minutes, seconds);
    }
}
