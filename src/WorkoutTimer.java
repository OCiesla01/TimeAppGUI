import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;

public class WorkoutTimer extends JFrame implements ActionListener {
    JButton backToMain, startStopWorkout, resetWorkout;
    JLabel topLine, botLine, workTimeLabel, restTimeLabel, workTimeText, restTimeText, workoutText, restText;
    JTextField setWorkTime, setRestTime;
    int workTime = 0, restTime = 0, formattedWorkMin, formattedWorkSec, formattedRestMin, formattedRestSec;
    String ddWorkTimeMin, ddWorkTimeSec, ddRestTimeMin, ddRestTimeSec;
    boolean isWorkTime = true, isWorking = false;
    DecimalFormat dFormat = new DecimalFormat("00");
    Timer timer;
    File file1, file2;
    Clip clip1, clip2;
    WorkoutTimer() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(500, 700);
        this.setResizable(false);
        this.setLayout(null);
        this.setTitle("Workout Timer");

        backToMain = new JButton("<- Go Back");
        backToMain.setBounds(0, 0, 100, 30);
        backToMain.setFocusable(false);
        backToMain.addActionListener(this);

        topLine = new JLabel();
        topLine.setBounds(0, 40, 700, 3);
        topLine.setBackground(Color.black);
        topLine.setOpaque(true);

        botLine = new JLabel();
        botLine.setBounds(0, 550, 700, 3);
        botLine.setBackground(Color.black);
        botLine.setOpaque(true);

        workTimeLabel = new JLabel();
        workTimeLabel.setText("00" + ":" + workTime + "0");
        workTimeLabel.setBounds(40, 60, 400, 200);
        workTimeLabel.setHorizontalAlignment(JTextField.CENTER);
        workTimeLabel.setVerticalAlignment(JTextField.CENTER);
        workTimeLabel.setFont(new Font("MV Boli", Font.BOLD, 50));
        workTimeLabel.setBackground(new Color(0xCDCDCD));
        workTimeLabel.setBorder(BorderFactory.createLineBorder(new Color(0xB4B4B4), 3));
        workTimeLabel.setOpaque(true);

        restTimeLabel = new JLabel();
        restTimeLabel.setText("00" + ":" + restTime + "0");
        restTimeLabel.setBounds(110, 290, 250, 150);
        restTimeLabel.setHorizontalAlignment(JTextField.CENTER);
        restTimeLabel.setVerticalAlignment(JTextField.CENTER);
        restTimeLabel.setFont(new Font("MV Boli", Font.BOLD, 50));
        restTimeLabel.setBackground(new Color(0xCDCDCD));
        restTimeLabel.setBorder(BorderFactory.createLineBorder(new Color(0xB4B4B4), 3));
        restTimeLabel.setOpaque(true);

        workoutText = new JLabel("Work time");
        workoutText.setBounds(115, 10, 200, 50);
        workoutText.setForeground(new Color(0x858585));
        workoutText.setFont(new Font("Georgia", Font.PLAIN, 40));

        restText = new JLabel("Rest time");
        restText.setBounds(75, 0, 200, 50);
        restText.setForeground(new Color(0x858585));
        restText.setFont(new Font("Georgia", Font.PLAIN, 25));

        setWorkTime = new JTextField();
        setWorkTime.setBounds(20, 575, 60, 60);
        setWorkTime.setFont(new Font("Roboto", Font.PLAIN, 35));
        setWorkTime.setBackground(new Color(0x9A9A9A));
        setWorkTime.setBorder(BorderFactory.createLineBorder(new Color(0x6C6C6C), 3));

        setRestTime = new JTextField();
        setRestTime.setBounds(400, 575, 60, 60);
        setRestTime.setFont(new Font("Roboto", Font.PLAIN, 35));
        setRestTime.setBackground(new Color(0x9A9A9A));
        setRestTime.setBorder(BorderFactory.createLineBorder(new Color(0x6C6C6C), 3));

        workTimeText = new JLabel("Set work time");
        workTimeText.setBounds(100, 590, 100, 25);
        workTimeText.setFont(new Font("Georgia", Font.PLAIN, 15));

        restTimeText = new JLabel("Set rest time");
        restTimeText.setBounds(300, 590, 100, 25);
        restTimeText.setFont(new Font("Georgia", Font.PLAIN, 15));

        startStopWorkout = new JButton();
        startStopWorkout.setText("Start");
        startStopWorkout.setBounds(250, 475, 70, 40);
        startStopWorkout.setFocusable(false);
        startStopWorkout.addActionListener(this);

        resetWorkout = new JButton();
        resetWorkout.setText("Reset");
        resetWorkout.setBounds(150, 475, 70, 40);
        resetWorkout.setFocusable(false);
        resetWorkout.addActionListener(this);


        workTimeLabel.add(workoutText);
        restTimeLabel.add(restText);

        this.add(resetWorkout);
        this.add(startStopWorkout);
        this.add(workTimeText);
        this.add(restTimeText);
        this.add(setWorkTime);
        this.add(setRestTime);
        this.add(restTimeLabel);
        this.add(workTimeLabel);
        this.add(backToMain);
        this.add(botLine);
        this.add(topLine);
        this.setVisible(true);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == backToMain) {
            clip1.close();
            clip2.close();
            this.dispose();
            new MainPanel();
        }
        if (e.getSource() == resetWorkout) {
            isWorking = false;
            isWorkTime = true;
            startStopWorkout.setText("Start");
            workTimeLabel.setText("00:00");
            restTimeLabel.setText("00:00");
            setRestTime.setText("");
            setWorkTime.setText("");
            clip1.stop();
            clip2.stop();
            timer.stop();
        }
        if (e.getSource() == startStopWorkout) {
            if (isWorking) {
                startStopWorkout.setText("Start");
                isWorking = false;
                timer.stop();
            } else {
                if (confirmTime()) {
                    timer = new Timer(1000, e1 -> {
                        if (isWorkTime) {
                            if (workTime > 0) {
                                workTime--;
                                formatTime();
                            } else {
                                isWorkTime = false;
                                file1 = new File("Sounds/restover.wav");
                                AudioInputStream audioStream1;
                                try {
                                    audioStream1 = AudioSystem.getAudioInputStream(file1);
                                } catch (UnsupportedAudioFileException | IOException ex) {
                                    throw new RuntimeException(ex);
                                }
                                try {
                                    clip1 = AudioSystem.getClip();
                                } catch (LineUnavailableException ex) {
                                    throw new RuntimeException(ex);
                                }
                                try {
                                    clip1.open(audioStream1);
                                } catch (LineUnavailableException | IOException ex) {
                                    throw new RuntimeException(ex);
                                }
                                clip1.start();
                                workTime = Integer.parseInt(setWorkTime.getText());
                            }

                        }
                        if (!isWorkTime) {
                            if (restTime > 0) {
                                restTime--;
                                formatTime();
                            } else {
                                isWorkTime = true;
                                file2 = new File("Sounds/workover.wav");
                                AudioInputStream audioStream2;
                                try {
                                    audioStream2 = AudioSystem.getAudioInputStream(file2);
                                } catch (UnsupportedAudioFileException | IOException ex) {
                                    throw new RuntimeException(ex);
                                }
                                try {
                                    clip2 = AudioSystem.getClip();
                                } catch (LineUnavailableException ex) {
                                    throw new RuntimeException(ex);
                                }
                                try {
                                    clip2.open(audioStream2);
                                } catch (LineUnavailableException | IOException ex) {
                                    throw new RuntimeException(ex);
                                }
                                clip2.start();
                                restTime = Integer.parseInt(setRestTime.getText());
                            }
                        }
                    });
                    startStopWorkout.setText("Stop");
                    isWorking = true;
                    timer.start();
                }
            }

        }
    }
    public boolean confirmTime() {
        try {
            workTime = Integer.parseInt(setWorkTime.getText());
            restTime = Integer.parseInt(setRestTime.getText());
        }
        catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "It has to be Integer. You can't workout on Strings.",
                    "Entered time error", JOptionPane.ERROR_MESSAGE);
        }
        if (workTime == 0 || restTime == 0) {
            JOptionPane.showMessageDialog(null, "Come on... That's not a workout",
                    "Entered time error", JOptionPane.ERROR_MESSAGE);
        } else if (workTime > 3600) {
            JOptionPane.showMessageDialog(null, "Let's keep this workout under 60min beast.",
                    "Entered time error", JOptionPane.ERROR_MESSAGE);
        } else if (restTime > 3600) {
            JOptionPane.showMessageDialog(null, "Bro... that's some long rest.",
                    "Entered time error", JOptionPane.ERROR_MESSAGE);
        } else {
            return true;
        }
        return false;
    }
    public void formatTime() {
        formattedWorkMin = workTime / 60;
        formattedWorkSec = workTime % 60;
        formattedRestMin = restTime / 60;
        formattedRestSec = restTime % 60;
        ddWorkTimeMin = dFormat.format(formattedWorkMin);
        ddWorkTimeSec = dFormat.format(formattedWorkSec);
        ddRestTimeMin = dFormat.format(formattedRestMin);
        ddRestTimeSec = dFormat.format(formattedRestSec);
        workTimeLabel.setText(ddWorkTimeMin + ":" + ddWorkTimeSec);
        restTimeLabel.setText(ddRestTimeMin + ":" + ddRestTimeSec);
    }
}
