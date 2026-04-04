package helpers;

import java.awt.*;

public class Utils {
    public static Font createDefaultStyle(int size) {
        return new Font("Arial", Font.BOLD, size);
    }

    public static int convertLevelToNumber(String level) {
        if (level.equals("Dễ")) return 1;
        else if (level.equals("Trung Bình")) return 2;
        else return 3;
    }

    public static String convertNumberToLevel(int level) {
        if (level == 1) return "Dễ";
        else if (level == 2) return "Trung Bình";
        return "Khó";
    }
}
