package listeners;

import main.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import static helpers.Colors.*;

public class SudokuKeyListener extends KeyAdapter {
    private final int row1;
    private final int col1;
    private final JButton[][] buttons;

    public SudokuKeyListener(int row, int col, JButton[][] buttons) {
        this.row1 = row;
        this.col1 = col;
        this.buttons = buttons;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (!isCellEditable(row1, col1)) return;

        int keyCode = e.getKeyCode();

        if (isDeleteKey(keyCode)) {
            handleDelete();
            return;
        }

        int number = parseNumberInput(keyCode);
        if (number >= 1 && number <= 9) {
            updateCell(row1, col1, number);
        }
    }

    private boolean isCellEditable(int row, int col) {
        Color fg = buttons[row][col].getForeground();
        return fg != clVang && fg != clDen;
    }

    private boolean isDeleteKey(int keyCode) {
        return keyCode == KeyEvent.VK_BACK_SPACE || keyCode == KeyEvent.VK_DELETE;
    }

    private void handleDelete() {
        if (!buttons[row1][col1].getText().equals(" ")) {
            updateCell(row1, col1, 0);
        }
    }

    private int parseNumberInput(int keyCode) {
        if (keyCode >= KeyEvent.VK_1 && keyCode <= KeyEvent.VK_9) {
            return keyCode - KeyEvent.VK_0;
        }
        if (keyCode >= KeyEvent.VK_NUMPAD1 && keyCode <= KeyEvent.VK_NUMPAD9) {
            return keyCode - KeyEvent.VK_NUMPAD0;
        }
        return 0;
    }

    private void updateCell(int row, int col, int value) {
        if (Main.STATE.isCompleted()) return;

        boolean stt = Main.STATE.setCellValue(row, col, value);

        if (!stt) return;

        JButton btn = buttons[row][col];

        if (value == 0) {
            btn.setText(" ");
            btn.setForeground(null);
        } else {
            btn.setText(String.valueOf(value));
            btn.setForeground(value == Main.STATE.getSolution()[row][col] ? clVang : clDo);
        }
    }
}
