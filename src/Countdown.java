import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;

public class Countdown extends JFrame implements ActionListener {
    JLabel mainLabel, topLine, topLine2, enterMinutes, enterSeconds, timeOverL;
    JButton backToMain, start, reset, acceptTimeInput, timeOverB;
    JTextField enteredMinutes, enteredSeconds;
    ImageIcon acceptTime = new ImageIcon("acceptTime.png");
    Image acceptTimeImage = acceptTime.getImage();
    Image resizedAcceptTimeImage = acceptTimeImage.getScaledInstance(25, 25, Image.SCALE_SMOOTH);
    ImageIcon acceptTimeResized;
    boolean isStarted = false;
    int seconds = 0, minutes = 1;
    String ddSeconds, ddMinutes;
    Timer timer;
    DecimalFormat dFormat = new DecimalFormat("00");
    File file;
    Clip clip;
    Countdown() throws LineUnavailableException, IOException, UnsupportedAudioFileException {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(500, 700);
        this.setResizable(false);
        this.setLayout(null);
        this.setTitle("Countdown Timer");

        mainLabel = new JLabel("0" + minutes + ":" + "0" + seconds);
        mainLabel.setHorizontalAlignment(JTextField.CENTER);
        mainLabel.setVerticalAlignment(JTextField.CENTER);
        mainLabel.setFont(new Font("MV Boli", Font.BOLD, 50));
        mainLabel.setBounds(40, 135, 400, 400);
        mainLabel.setBackground(new Color(0xCDCDCD));
        mainLabel.setBorder(BorderFactory.createLineBorder(new Color(0xB4B4B4), 3));
        mainLabel.setOpaque(true);

        enterMinutes = new JLabel("Minutes");
        enterMinutes.setBounds(100, 40, 100, 25);
        enterMinutes.setFont(new Font("Roboto", Font.PLAIN, 15));

        enterSeconds = new JLabel("Seconds");
        enterSeconds.setBounds(270, 40, 100, 25);
        enterSeconds.setFont(new Font("Roboto", Font.PLAIN, 15));

        enteredMinutes = new JTextField();
        enteredMinutes.setBounds(100, 60, 100, 25);
        enteredMinutes.setBackground(new Color(0xC4C4C4));
        enteredMinutes.setFont(new Font("Roboto", Font.PLAIN, 15));
        enteredMinutes.setText("0");

        enteredSeconds = new JTextField();
        enteredSeconds.setBounds(270, 60, 100, 25);
        enteredSeconds.setBackground(new Color(0xC4C4C4));
        enteredSeconds.setFont(new Font("Roboto", Font.PLAIN, 15));
        enteredSeconds.setText("0");

        acceptTimeResized = new ImageIcon(resizedAcceptTimeImage);

        acceptTimeInput = new JButton();
        acceptTimeInput.setBounds(220, 85, 25, 25);
        acceptTimeInput.setBackground(new Color(0xC4C4C4));
        acceptTimeInput.setIcon(acceptTimeResized);
        acceptTimeInput.setFocusable(false);
        acceptTimeInput.addActionListener(this);

        start = new JButton("START");
        start.setBounds(100, 570, 100, 50);
        start.setBackground(new Color(0xB4B4B4));
        start.setBorder(BorderFactory.createLineBorder(new Color(0x9B9B9B), 5));
        start.addActionListener(this);
        start.setFocusable(false);

        reset = new JButton("RESET");
        reset.setBounds(270, 570, 100, 50);
        reset.setBackground(new Color(0xB4B4B4));
        reset.setBorder(BorderFactory.createLineBorder(new Color(0x9B9B9B), 5));
        reset.addActionListener(this);
        reset.setFocusable(false);

        topLine = new JLabel();
        topLine.setBounds(0, 40, 700, 3);
        topLine.setBackground(Color.black);
        topLine.setOpaque(true);

        topLine2 = new JLabel();
        topLine2.setBounds(0, 115, 700, 3);
        topLine2.setBackground(Color.black);
        topLine2.setOpaque(true);

        backToMain = new JButton("<- Go Back");
        backToMain.setBounds(0, 0, 100, 30);
        backToMain.setFocusable(false);
        backToMain.addActionListener(this);

        timeOverL = new JLabel("Time's up!");
        timeOverL.setBounds(140, 400, 300, 60);
        timeOverL.setFont(new Font("MV Boli", Font.BOLD, 40));
        timeOverL.setVisible(false);

        timeOverB = new JButton();
        timeOverB.setBounds(215, 460, 55, 30);
        timeOverB.setFocusable(false);
        timeOverB.setBackground(new Color(0xB0B0B0));
        timeOverB.setBorder(BorderFactory.createLineBorder(new Color(0x939393), 2));
        timeOverB.setText("OK");
        timeOverB.setVisible(false);
        timeOverB.addActionListener(this);

        file = new File("Sounds/countdownSound.wav");
        AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
        clip = AudioSystem.getClip();
        clip.open(audioStream);

        this.add(timeOverL);
        this.add(timeOverB);
        this.add(enterSeconds);
        this.add(enterMinutes);
        this.add(enteredSeconds);
        this.add(enteredMinutes);
        this.add(acceptTimeInput);
        this.add(start);
        this.add(reset);
        this.add(topLine);
        this.add(topLine2);
        this.add(backToMain);
        this.add(mainLabel);
        this.setVisible(true);

        setCountdownTimer();
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == backToMain) {
            clip.close();
            this.dispose();
            new MainPanel();
        }
        if (e.getSource() == acceptTimeInput) {
            try {
                minutes = Integer.parseInt(enteredMinutes.getText());
                seconds = Integer.parseInt(enteredSeconds.getText());
            }
            catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Minutes and/or seconds must be Integer",
                        "Entered time error", JOptionPane.ERROR_MESSAGE);
            }
            if (minutes == 0 && seconds == 0) {
                JOptionPane.showMessageDialog(null, "Minutes and seconds can't be both '0'",
                        "Entered time error", JOptionPane.ERROR_MESSAGE);
            } else {
                if (seconds > 59) {
                    JOptionPane.showMessageDialog(null, "Seconds value can't be greater than 59",
                            "Seconds error", JOptionPane.ERROR_MESSAGE);
                } else {
                    ddMinutes = dFormat.format(minutes);
                    ddSeconds = dFormat.format(seconds);
                    mainLabel.setText(ddMinutes + ":" + ddSeconds);
                }
            }
        }
        if (e.getSource() == start) {
            if (!isStarted) {
                startCountdownTimer();
            } else {
                stopCountdownTimer();
            }
        }
        if (e.getSource() == reset) {
            resetCountdownTimer();
        }
        if (e.getSource() == timeOverB) {
            resetCountdownTimer();
            clip.stop();
        }
    }
    public void setCountdownTimer() {
        timer = new Timer(1000, e -> {
             seconds--;
             ddSeconds = dFormat.format(seconds);
             ddMinutes = dFormat.format(minutes);
             mainLabel.setText(ddMinutes + ":" + ddSeconds);

             if (seconds == -1) {
                 minutes--;
                 seconds = 59;
                 ddSeconds = dFormat.format(seconds);
                 ddMinutes = dFormat.format(minutes);
                 mainLabel.setText(ddMinutes + ":" + ddSeconds);
             }
             if (minutes == 0 && seconds == 0) {
                 mainLabel.setText("00:00");
                 timer.stop();
                 timeOverL.setVisible(true);
                 timeOverB.setVisible(true);
                 clip.start();
                 clip.loop(Clip.LOOP_CONTINUOUSLY);
             }
        });
    }
    public void startCountdownTimer() {
        isStarted = true;
        start.setText("STOP");
        timer.start();
        enteredMinutes.setEditable(false);
        enteredSeconds.setEditable(false);
        acceptTimeInput.setEnabled(false);
    }
    public void stopCountdownTimer() {
        isStarted = false;
        start.setText("START");
        timer.stop();
        enteredMinutes.setEditable(false);
        enteredSeconds.setEditable(false);
        acceptTimeInput.setEnabled(false);
    }
    public void resetCountdownTimer() {
        isStarted = false;
        start.setText("START");
        timer.stop();
        timeOverL.setVisible(false);
        timeOverB.setVisible(false);
        enteredMinutes.setEditable(true);
        enteredSeconds.setEditable(true);
        acceptTimeInput.setEnabled(true);
        seconds = 0;
        minutes = 1;
        mainLabel.setText("0" + minutes + ":" + "0" + seconds);
    }
}