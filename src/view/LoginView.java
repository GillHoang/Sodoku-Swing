package view;

import java.util.function.Consumer;

public interface LoginView {
    void setStartHandler(Consumer<String> onStart);
}