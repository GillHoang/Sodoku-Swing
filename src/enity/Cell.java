package enity;

import javax.swing.*;

public class Cell {
    private boolean isSelected;
    private boolean isFilled;
    private boolean fromGenerator;
    private JButton btn;
    private int value;

    public Cell(int value, JButton btn, boolean fromGenerator) {
        this.value = value;
        this.btn = btn;
        this.fromGenerator = fromGenerator;
        this.isSelected = false;
        this.isFilled = false;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public boolean isFilled() {
        return isFilled;
    }

    public void setFilled(boolean filled) {
        isFilled = filled;
    }

    public boolean isFromGenerator() {
        return fromGenerator;
    }

    public void setFromGenerator(boolean fromGenerator) {
        this.fromGenerator = fromGenerator;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public JButton getBtn() {
        return btn;
    }

    public void setBtn(JButton btn) {
        this.btn = btn;
    }
}
