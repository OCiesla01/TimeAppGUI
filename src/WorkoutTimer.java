import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

public class WorkoutTimer extends JFrame implements ActionListener {
    JButton backToMain, startWorkout;
    JLabel topLine, botLine, workTimeLabel, restTimeLabel, workTimeText, restTimeText, workoutText, restText;
    JTextField setWorkTime, setRestTime;
    int workTime = 5, restTime = 5, formattedWorkMin, formattedWorkSec, formattedRestMin, formattedRestSec;
    String ddWorkTimeMin, ddWorkTimeSec, ddRestTimeMin, ddRestTimeSec;
    boolean isWorkTime = true;
    DecimalFormat dFormat = new DecimalFormat("00");
    Timer timer;
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
        workTimeLabel.setText("00");
        workTimeLabel.setBounds(40, 60, 400, 200);
        workTimeLabel.setHorizontalAlignment(JTextField.CENTER);
        workTimeLabel.setVerticalAlignment(JTextField.CENTER);
        workTimeLabel.setFont(new Font("MV Boli", Font.BOLD, 50));
        workTimeLabel.setBackground(new Color(0xCDCDCD));
        workTimeLabel.setBorder(BorderFactory.createLineBorder(new Color(0xB4B4B4), 3));
        workTimeLabel.setOpaque(true);

        restTimeLabel = new JLabel();
        restTimeLabel.setText("00");
        restTimeLabel.setBounds(110, 280, 250, 150);
        restTimeLabel.setHorizontalAlignment(JTextField.CENTER);
        restTimeLabel.setVerticalAlignment(JTextField.CENTER);
        restTimeLabel.setFont(new Font("MV Boli", Font.BOLD, 50));
        restTimeLabel.setBackground(new Color(0xCDCDCD));
        restTimeLabel.setBorder(BorderFactory.createLineBorder(new Color(0xB4B4B4), 3));
        restTimeLabel.setOpaque(true);

        workoutText = new JLabel("Work time");
        workoutText.setBounds(175, 0, 100, 50);
        workoutText.setForeground(Color.red);

        restText = new JLabel("Rest time");
        restText.setBounds(100, 0, 100, 50);
        restText.setForeground(Color.red);

        setWorkTime = new JTextField();
        setWorkTime.setBounds(120, 560, 40, 60);
        setWorkTime.setFont(new Font("Roboto", Font.PLAIN, 15));

        setRestTime = new JTextField();
        setRestTime.setBounds(400, 560, 40, 60);
        setRestTime.setFont(new Font("Roboto", Font.PLAIN, 15));

        workTimeText = new JLabel("Set work time");
        workTimeText.setBounds(20, 570, 100, 25);

        restTimeText = new JLabel("Set rest time");
        restTimeText.setBounds(300, 570, 100, 25);

        startWorkout = new JButton();
        startWorkout.setBounds(212, 475, 40, 40);
        startWorkout.setFocusable(false);
        startWorkout.addActionListener(this);


        workTimeLabel.add(workoutText);
        restTimeLabel.add(restText);

        this.add(startWorkout);
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
            this.dispose();
            new MainPanel();
        }
        if (e.getSource() == startWorkout) {
            if (confirmTime()) {
                formatTime();
                timer = new Timer(1000, e1 -> {
                    if (isWorkTime) {
                        if (workTime > 0) {
                            workTime--;
                            formatTime();
                        } else {
                            isWorkTime = false;
                            workTime = Integer.parseInt(setWorkTime.getText());
                        }

                    }
                    if (!isWorkTime) {
                        if (restTime > 0) {
                            restTime--;
                            formatTime();
                        } else {
                            isWorkTime = true;
                            restTime = Integer.parseInt(setRestTime.getText());
                        }
                    }
                });
                timer.start();
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
        if (workTime == 0 && restTime == 0) {
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
