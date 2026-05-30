package view;

public interface EndingView {
    void setPrimaryAction(Runnable action);

    void setSecondaryAction(Runnable action);

    void showResult(int score, long elapsedSeconds);
}
