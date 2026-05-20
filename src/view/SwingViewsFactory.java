package view;

public interface SwingViewsFactory {
    LoginView createLoginView();

    ChooseLevelView createChooseLevelView();

    GameView createGameView();

    EndingView createGoodEndingView();

    EndingView createBadEndingView();
}
