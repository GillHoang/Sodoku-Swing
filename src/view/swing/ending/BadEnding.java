package view.swing.ending;

import view.EndingView;

import java.util.List;

public class BadEnding extends Ending implements EndingView {
    public BadEnding() {
        super("Bạn đã quá số lần thử cho phép!", 34, List.of(
                new EndingAction("Chơi lại", null),
                new EndingAction("Chọn level khác", null)
        ));
    }

    @Override
    public void setPrimaryAction(Runnable action) {
        setAction(0, action);
    }

    @Override
    public void setSecondaryAction(Runnable action) {
        setAction(1, action);
    }
}
