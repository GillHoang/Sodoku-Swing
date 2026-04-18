package helpers;

import enity.Level;

import java.util.List;

public class Levels {
    public static final Level EASY = new Level(1, "Easy", 35);
    public static final Level MEDIUM = new Level(2, "Medium", 45);
    public static final Level HARD = new Level(3, "Hard", 52);
    public static final List<Level> LEVEL_LIST = List.of(EASY, MEDIUM, HARD);

    private Levels() {
    }
}
