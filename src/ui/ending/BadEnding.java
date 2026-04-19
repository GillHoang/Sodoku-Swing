package ui.ending;

import java.util.Arrays;

public class BadEnding extends Ending {
    public BadEnding() {
        super("Bạn đã quá số lần thử cho phép!", 34, Arrays.asList("Chơi lại", "Chọn level khác"));
    }
}