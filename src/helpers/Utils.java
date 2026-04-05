package helpers;

import java.awt.*;

public class Utils {
    private Utils() {
    }

    public static Font createDefaultStyle(int size) {
        return new Font("Arial", Font.BOLD, size);
    }

    public static int convertLevelToNumber(String level) {
        if (level.equals(Levels.EASY)) return Levels.LEVEL_EASY;
        else if (level.equals(Levels.MEDIUM)) return Levels.LEVEL_MEDIUM;
        else return Levels.LEVEL_HARD;
    }

    public static String convertNumberToLevel(int level) {
        if (level == Levels.LEVEL_EASY) return Levels.EASY;
        else if (level == Levels.LEVEL_MEDIUM) return Levels.MEDIUM;
        return Levels.HARD;
    }

    public static int convertNumberToRemove(int level) {
        if (level == Levels.LEVEL_EASY) return Levels.REMOVE_EASY;
        else if (level == Levels.LEVEL_MEDIUM) return Levels.REMOVE_MEDIUM;
        return Levels.REMOVE_HARD;
    }
}
