package controller.navigation;

public enum AppScreen {
    LOGIN("login"),
    CHOOSE_LEVEL("chooseLevel"),
    SUDOKU("sudoku"),
    GOOD_ENDING("goodEnding"),
    BAD_ENDING("badEnding");

    private final String cardName;

    AppScreen(String cardName) {
        this.cardName = cardName;
    }

    public String cardName() {
        return cardName;
    }
}