import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class MainPanel extends JFrame implements ActionListener {
    JButton openStopwatch, openCountdown, openClock, openWorkoutTimer, click;
    JLabel stopwatchT, countdownT, clockT, workoutTimerT;
    JLabel arrow1, arrow2, arrow3, arrow4;
    ImageIcon stopwatchIcon = new ImageIcon("Images/stopwatch.png");
    Image stopwatchIconImage = stopwatchIcon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
    ImageIcon resizedStopwatchIcon = new ImageIcon(stopwatchIconImage);

    ImageIcon countdownIcon = new ImageIcon("Images/countdown.png");
    Image countdownResizedIcon = countdownIcon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
    ImageIcon resizedCountdownIcon = new ImageIcon(countdownResizedIcon);

    ImageIcon clockIcon = new ImageIcon("Images/clock.png");
    Image clockResizedIcon = clockIcon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
    ImageIcon resizedClockIcon = new ImageIcon(clockResizedIcon);

    ImageIcon workoutIcon = new ImageIcon("Images/workout.png");
    Image workoutResizedIcon = workoutIcon.getImage().getScaledInstance(110, 150, Image.SCALE_SMOOTH);
    ImageIcon resizedWorkoutIcon = new ImageIcon(workoutResizedIcon);

    ImageIcon arrowIcon = new ImageIcon("Images/arrow.png");
    Image arrowResizedIcon = arrowIcon.getImage().getScaledInstance(15, 15, Image.SCALE_SMOOTH);
    ImageIcon resizedArrowIcon = new ImageIcon(arrowResizedIcon);
    MainPanel() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(500, 700);
        this.setResizable(false);
        this.setLayout(null);

        openStopwatch = new JButton();
        openStopwatch.setBounds(20, 25, 200, 175);
        openStopwatch.setIcon(resizedStopwatchIcon);
        openStopwatch.setFocusable(false);
        openStopwatch.setOpaque(false);
        openStopwatch.setContentAreaFilled(false);
        openStopwatch.setBorderPainted(false);
        openStopwatch.addActionListener(this);

        stopwatchT = new JLabel("Stopwatch");
        stopwatchT.setBounds(30, 190, 200, 50);
        stopwatchT.setFont(new Font("MV Boli", Font.PLAIN, 35));

        openCountdown = new JButton();
        openCountdown.setBounds(270, 25, 200, 175);
        openCountdown.setIcon(resizedCountdownIcon);
        openCountdown.setFocusable(false);
        openCountdown.setOpaque(false);
        openCountdown.setContentAreaFilled(false);
        openCountdown.setBorderPainted(false);
        openCountdown.addActionListener(this);

        countdownT = new JLabel("Countdown");
        countdownT.setBounds(270, 190, 200, 50);
        countdownT.setFont(new Font("MV Boli", Font.PLAIN, 35));

        openClock = new JButton();
        openClock.setBounds(20, 400, 200, 175);
        openClock.setIcon(resizedClockIcon);
        openClock.setFocusable(false);
        openClock.setOpaque(false);
        openClock.setContentAreaFilled(false);
        openClock.setBorderPainted(false);
        openClock.addActionListener(this);

        clockT = new JLabel("Clock");
        clockT.setBounds(75, 565, 100, 50);
        clockT.setFont(new Font("MV Boli", Font.PLAIN, 35));

        openWorkoutTimer = new JButton();
        openWorkoutTimer.setBounds(270, 380, 200, 175);
        openWorkoutTimer.setIcon(resizedWorkoutIcon);
        openWorkoutTimer.setFocusable(false);
        openWorkoutTimer.setOpaque(false);
        openWorkoutTimer.setContentAreaFilled(false);
        openWorkoutTimer.setBorderPainted(false);
        openWorkoutTimer.addActionListener(this);

        workoutTimerT = new JLabel("Workout");
        workoutTimerT.setBounds(300, 565, 200, 50);
        workoutTimerT.setFont(new Font("MV Boli", Font.PLAIN, 35));

        click = new JButton("Click on icon");
        click.setBounds(150, 300, 175, 50);
        click.setFont(new Font("MV Boli", Font.PLAIN, 20));
        click.setFocusable(false);
        click.setOpaque(false);
        click.setContentAreaFilled(false);
        click.setBorderPainted(false);
        click.addActionListener(this);

        arrow1 = new JLabel();
        arrow1.setIcon(resizedArrowIcon);
        arrow1.setBounds(115, 20, 25, 25);
        arrow1.setVisible(false);

        arrow2 = new JLabel();
        arrow2.setIcon(resizedArrowIcon);
        arrow2.setBounds(365, 20, 25, 25);
        arrow2.setVisible(false);

        arrow3 = new JLabel();
        arrow3.setIcon(resizedArrowIcon);
        arrow3.setBounds(115, 375, 25, 25);
        arrow3.setVisible(false);

        arrow4 = new JLabel();
        arrow4.setIcon(resizedArrowIcon);
        arrow4.setBounds(365, 360, 25, 25);
        arrow4.setVisible(false);


        this.add(arrow1);
        this.add(arrow2);
        this.add(arrow3);
        this.add(arrow4);
        this.add(click);
        this.add(stopwatchT);
        this.add(countdownT);
        this.add(clockT);
        this.add(workoutTimerT);
        this.add(openWorkoutTimer);
        this.add(openClock);
        this.add(openCountdown);
        this.add(openStopwatch);
        this.setVisible(true);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == click) {
            if (!arrow1.isVisible()) {
                arrow1.setVisible(true);
                arrow2.setVisible(true);
                arrow3.setVisible(true);
                arrow4.setVisible(true);
            } else {
                arrow1.setVisible(false);
                arrow2.setVisible(false);
                arrow3.setVisible(false);
                arrow4.setVisible(false);
            }
        }
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
