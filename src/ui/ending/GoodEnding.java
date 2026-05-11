package ui.ending;

import main.Main;

import java.util.List;

public class GoodEnding extends Ending {
    public GoodEnding() {
        super("Chúc mừng bạn đã qua màn thành công!", 25, List.of(
                new EndingAction("Chọn level khác", Main.STATE::navigateToChooseLevel)
        ));
    }
}
