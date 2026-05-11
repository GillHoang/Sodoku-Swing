package ui.ending;

import main.Main;

import java.util.List;

public class BadEnding extends Ending {
    public BadEnding() {
        super("Bạn đã quá số lần thử cho phép!", 34, List.of(
                new EndingAction("Chơi lại", Main.STATE::restartSudokuGame),
                new EndingAction("Chọn level khác", Main.STATE::navigateToChooseLevel)
        ));
    }
}
