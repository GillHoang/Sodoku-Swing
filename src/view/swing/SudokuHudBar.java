package view.swing;

import common.helpers.Utils;
import model.GameSnapshot;

import javax.swing.*;
import java.awt.*;

import static common.helpers.Colors.clLam;
import static common.helpers.Colors.clTrang;

public class SudokuHudBar extends JPanel {
    private final JLabel lbScore = new JLabel("Điểm: 0");
    private final JLabel lbTime = new JLabel("Thời gian: 00:00");
    private final JLabel lbMistake = new JLabel("Lỗi: 0/3");

    public SudokuHudBar() {
        super(new BorderLayout());
        setBackground(clLam);

        JPanel pnScore = new JPanel();
        lbScore.setFont(Utils.createDefaultStyle(20));
        lbScore.setForeground(clTrang);
        pnScore.add(lbScore);
        pnScore.setBackground(clLam);

        JPanel pnTime = new JPanel();
        lbTime.setFont(Utils.createDefaultStyle(20));
        lbTime.setForeground(clTrang);
        pnTime.add(lbTime);
        pnTime.setBackground(clLam);

        JPanel pnMistake = new JPanel();
        lbMistake.setFont(Utils.createDefaultStyle(20));
        lbMistake.setForeground(clTrang);
        pnMistake.add(lbMistake);
        pnMistake.setBackground(clLam);

        add(pnScore, BorderLayout.WEST);
        add(pnTime, BorderLayout.CENTER);
        add(pnMistake, BorderLayout.EAST);
    }

    public void updateHud(GameSnapshot snapshot) {
        lbScore.setText("Điểm: " + snapshot.score());
        lbTime.setText("Thời gian: " + Utils.formatTime(snapshot.elapsedTimeSeconds()));
        lbMistake.setText("Lỗi: " + snapshot.mistakes() + "/" + snapshot.maxMistakes());
    }

    public void updateTime(long elapsedSeconds) {
        lbTime.setText("Thời gian: " + Utils.formatTime(elapsedSeconds));
    }
}
