package view.swing;

import view.ChooseLevelView;
import view.EndingView;
import view.GameView;
import view.LoginView;
import view.SwingViewsFactory;
import view.swing.ending.BadEnding;
import view.swing.ending.GoodEnding;

public class DefaultSwingViewsFactory implements SwingViewsFactory {

    @Override
    public LoginView createLoginView() {
        return new LoginPanel();
    }

    @Override
    public ChooseLevelView createChooseLevelView() {
        return new ChooseLevelPanel();
    }

    @Override
    public GameView createGameView() {
        return new SudokuPanel();
    }

    @Override
    public EndingView createGoodEndingView() {
        return new GoodEnding();
    }

    @Override
    public EndingView createBadEndingView() {
        return new BadEnding();
    }
}
