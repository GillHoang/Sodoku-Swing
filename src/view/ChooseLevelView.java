package view;

import java.util.function.IntConsumer;

public interface ChooseLevelView {
    void setUsername(String username);

    void setLevelSelectHandler(IntConsumer onLevelSelected);
}
