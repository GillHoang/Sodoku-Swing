import javax.swing.*;

public class SodokuPanel extends JPanel {
    public SodokuPanel(String username) {
        super();

        add(new JLabel("Hello " + username + "!"));
    }
}
