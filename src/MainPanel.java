import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainPanel extends JFrame implements ActionListener {
    JButton openStopwatch;
    MainPanel() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(500, 700);
        this.setResizable(false);
        this.setLayout(null);

        openStopwatch = new JButton("Stopwatch");
        openStopwatch.setBounds(100, 100, 100, 50);
        openStopwatch.setFocusable(false);
        openStopwatch.addActionListener(this);


        this.add(openStopwatch);
        this.setVisible(true);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == openStopwatch) {
            this.dispose();
            new Stopwatch();
        }
    }
}