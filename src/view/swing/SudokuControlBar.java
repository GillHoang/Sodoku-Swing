package view.swing;

import common.helpers.Colors;
import common.helpers.Utils;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.function.Consumer;

public class SudokuControlBar extends JPanel {
    private static final String NOTES_OFF_LABEL = "Ghi chú: Tắt";
    private static final String NOTES_ON_LABEL = "Ghi chú: Bật";
    private final JButton hintBtn = createButton("Gợi ý (3)");
    private final JButton notesBtn = createButton(NOTES_OFF_LABEL);
    private transient Runnable onExit;
    private transient Runnable onHint;
    private transient Consumer<Boolean> onToggleNotes;
    private boolean notesEnabled;

    public SudokuControlBar() {
        super(new GridLayout(1, 3, 6, 0));
        setBackground(Colors.clLam);
        setBorder(new EmptyBorder(12, 12, 0, 12));

        JButton exitBtn = createButton("← Thoát");
        exitBtn.addActionListener(e -> {
            if (onExit != null) {
                onExit.run();
            }
        });
        hintBtn.addActionListener(e -> {
            if (onHint != null) {
                onHint.run();
            }
        });
        notesBtn.addActionListener(e -> toggleNotes());

        add(exitBtn);
        add(hintBtn);
        add(notesBtn);
    }

    private static JButton createButton(String label) {
        JButton btn = new JButton(label);
        btn.setFont(Utils.createDefaultStyle(16));
        btn.setBackground(Colors.clTrang);
        btn.setForeground(Colors.clDen);
        btn.setFocusPainted(false);
        btn.setMargin(new Insets(4, 4, 4, 4));
        return btn;
    }

    public void setOnExit(Runnable onExit) {
        this.onExit = onExit;
    }

    public void setOnHint(Runnable onHint) {
        this.onHint = onHint;
    }

    public void setOnToggleNotes(Consumer<Boolean> onToggleNotes) {
        this.onToggleNotes = onToggleNotes;
    }

    public void setHintsRemaining(int remaining) {
        hintBtn.setText("Gợi ý (" + remaining + ")");
        hintBtn.setEnabled(remaining > 0);
    }

    public void resetNotes() {
        notesEnabled = false;
        notesBtn.setText(NOTES_OFF_LABEL);
    }

    private void toggleNotes() {
        notesEnabled = !notesEnabled;
        notesBtn.setText(notesEnabled ? NOTES_ON_LABEL : NOTES_OFF_LABEL);
        if (onToggleNotes != null) {
            onToggleNotes.accept(notesEnabled);
        }
    }
}
