package view.swing.ending;

import view.EndingView;

import java.util.List;

public class GoodEnding extends Ending implements EndingView {
    public GoodEnding() {
        super("Chúc mừng bạn đã qua màn thành công!", 25, List.of(
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
