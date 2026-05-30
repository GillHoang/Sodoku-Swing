package controller;

import controller.navigation.AppScreen;
import controller.navigation.CardNavigator;
import model.GameSession;
import view.*;
import view.swing.DefaultSwingViewsFactory;

import javax.swing.*;
import java.awt.*;

public class AppController {
    private final JPanel rootPanel;
    private final CardNavigator navigator;
    private final GameSession session;
    private final LoginView loginView;
    private final ChooseLevelView chooseLevelView;
    private final EndingView goodEndingView;
    private final EndingView badEndingView;
    private final GameView gameView;
    private final Component loginComponent;
    private final Component chooseLevelComponent;
    private final Component goodEndingComponent;
    private final Component badEndingComponent;
    private final Component gameComponent;
    private GameController gameController;

    public AppController() {
        this(new DefaultSwingViewsFactory());
    }

    public AppController(SwingViewsFactory viewsFactory) {
        this.rootPanel = new JPanel();
        CardLayout cardLayout = new CardLayout();
        this.rootPanel.setLayout(cardLayout);
        this.navigator = new CardNavigator(rootPanel, cardLayout);
        this.session = new GameSession();

        LoginView login = viewsFactory.createLoginView();
        ChooseLevelView chooseLevel = viewsFactory.createChooseLevelView();
        EndingView goodEnding = viewsFactory.createGoodEndingView();
        EndingView badEnding = viewsFactory.createBadEndingView();
        GameView game = viewsFactory.createGameView();

        this.loginView = login;
        this.chooseLevelView = chooseLevel;
        this.goodEndingView = goodEnding;
        this.badEndingView = badEnding;
        this.gameView = game;

        this.loginComponent = toComponent(login);
        this.chooseLevelComponent = toComponent(chooseLevel);
        this.goodEndingComponent = toComponent(goodEnding);
        this.badEndingComponent = toComponent(badEnding);
        this.gameComponent = toComponent(game);

        bindActions();
        registerStaticCards();
    }

    private static Component toComponent(Object view) {
        if (!(view instanceof Component component)) {
            throw new IllegalArgumentException("View must be a Swing Component: " + view.getClass());
        }
        return component;
    }

    public JPanel getRootPanel() {
        return rootPanel;
    }

    private void bindActions() {
        loginView.setStartHandler(this::goToChooseLevel);
        chooseLevelView.setLevelSelectHandler(this::startSudokuGame);
        goodEndingView.setPrimaryAction(this::restartSudokuGame);
        goodEndingView.setSecondaryAction(this::navigateToChooseLevel);
        badEndingView.setPrimaryAction(this::restartSudokuGame);
        badEndingView.setSecondaryAction(this::navigateToChooseLevel);
    }

    private void registerStaticCards() {
        navigator.register(AppScreen.LOGIN, loginComponent);
        navigator.register(AppScreen.CHOOSE_LEVEL, chooseLevelComponent);
        navigator.register(AppScreen.SUDOKU, gameComponent);
        navigator.register(AppScreen.GOOD_ENDING, goodEndingComponent);
        navigator.register(AppScreen.BAD_ENDING, badEndingComponent);
    }

    private void goToChooseLevel(String username) {
        if (username == null || username.isBlank()) {
            return;
        }

        session.setUsername(username);
        chooseLevelView.setUsername(session.getUsername());
        navigator.show(AppScreen.CHOOSE_LEVEL);
    }

    private void startSudokuGame(int level) {
        session.setLevel(level);
        stopCurrentGameController();

        gameController = new GameController(session, gameView, this::showGoodEnding, this::showBadEnding,
                this::navigateToChooseLevel);
        gameController.startGame();

        navigator.show(AppScreen.SUDOKU);
    }

    private void restartSudokuGame() {
        startSudokuGame(session.getLevel());
    }

    private void navigateToChooseLevel() {
        stopCurrentGameController();
        navigator.show(AppScreen.CHOOSE_LEVEL);
    }

    private void showGoodEnding() {
        goodEndingView.showResult(session.getScore(), session.getElapsedTimeSeconds());
        navigator.show(AppScreen.GOOD_ENDING);
    }

    private void showBadEnding() {
        badEndingView.showResult(session.getScore(), session.getElapsedTimeSeconds());
        navigator.show(AppScreen.BAD_ENDING);
    }

    private void stopCurrentGameController() {
        if (gameController != null) {
            gameController.stop();
        }
    }
}
