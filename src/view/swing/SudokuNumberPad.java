package view.swing;

import common.helpers.Colors;
import common.helpers.Utils;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.function.IntConsumer;

public class SudokuNumberPad extends JPanel {
    private transient IntConsumer onDigit;

    public SudokuNumberPad() {
        super(new GridLayout(1, 10, 6, 0));
        setBackground(Colors.clLam);
        setBorder(new EmptyBorder(8, 12, 12, 12));

        for (int n = 1; n <= 9; n++) {
            add(createDigitButton(n));
        }

        JButton clearBtn = createPadButton("Xóa");
        clearBtn.addActionListener(e -> fireDigit(0));
        add(clearBtn);
    }

    public void setOnDigit(IntConsumer onDigit) {
        this.onDigit = onDigit;
    }

    private JButton createDigitButton(int digit) {
        JButton btn = createPadButton(String.valueOf(digit));
        btn.addActionListener(e -> fireDigit(digit));
        return btn;
    }

    private static JButton createPadButton(String label) {
        JButton btn = new JButton(label);
        btn.setFont(Utils.createDefaultStyle(20));
        btn.setBackground(Colors.clTrang);
        btn.setForeground(Colors.clDen);
        btn.setFocusPainted(false);
        btn.setMargin(new Insets(4, 4, 4, 4));
        return btn;
    }

    private void fireDigit(int value) {
        if (onDigit != null) {
            onDigit.accept(value);
        }
    }
}
