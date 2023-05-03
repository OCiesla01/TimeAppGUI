import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class MainPanel extends JFrame implements ActionListener {
    JButton openStopwatch, openCountdown, openClock, openWorkoutTimer;
    MainPanel() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(500, 700);
        this.setResizable(false);
        this.setLayout(null);

        openStopwatch = new JButton("Stopwatch");
        openStopwatch.setBounds(100, 100, 100, 50);
        openStopwatch.setFocusable(false);
        openStopwatch.addActionListener(this);

        openCountdown = new JButton("Countdown");
        openCountdown.setBounds(210, 100, 100, 50);
        openCountdown.setFocusable(false);
        openCountdown.addActionListener(this);

        openClock = new JButton("Clock");
        openClock.setBounds(320, 100, 100, 50);
        openClock.setFocusable(false);
        openClock.addActionListener(this);

        openWorkoutTimer = new JButton("Workout Timer");
        openWorkoutTimer.setBounds(100, 200, 150, 50);
        openWorkoutTimer.setFocusable(false);
        openWorkoutTimer.addActionListener(this);

        this.add(openWorkoutTimer);
        this.add(openClock);
        this.add(openCountdown);
        this.add(openStopwatch);
        this.setVisible(true);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == openStopwatch) {
            this.dispose();
            new Stopwatch();
        } else if (e.getSource() == openCountdown) {
            this.dispose();
            try {
                new Countdown();
            } catch (LineUnavailableException | IOException | UnsupportedAudioFileException ex) {
                throw new RuntimeException(ex);
            }
        } else if (e.getSource() == openClock) {
            this.dispose();
            new Clock();
        } else if (e.getSource() == openWorkoutTimer) {
            this.dispose();
            new WorkoutTimer();
        }
    }
}
