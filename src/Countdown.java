import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Countdown extends JFrame implements ActionListener {
    JLabel mainLabel;
    JLabel topLine;
    JLabel topLine2;
    JButton backToMain;
    JButton start;
    JButton reset;
    int minutes;
    int seconds;
    JTextField enteredMinutes;
    JTextField enteredSeconds;
    JButton acceptTimeInput;
    JLabel enterMinutes;
    JLabel enterSeconds;
    ImageIcon acceptTime = new ImageIcon("acceptTime.png");
    Image acceptTimeImage = acceptTime.getImage();
    Image resizedAcceptTimeImage = acceptTimeImage.getScaledInstance(25, 25, Image.SCALE_SMOOTH);
    ImageIcon acceptTimeResized;
    String minutes_string = String.format("%02d", minutes);
    String seconds_string = String.format("%02d", seconds);
    boolean isStarted = false;
    int elapsedTime;
    Timer timer = new Timer(1000, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            elapsedTime -= 1000;
            minutes = (elapsedTime / 60_000) % 60;
            seconds = (elapsedTime / 1000) % 60;
            minutes_string = String.format("%02d", minutes);
            seconds_string = String.format("%02d", seconds);
            mainLabel.setText(minutes_string + ":" + seconds_string);
        }
    });
    Countdown() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(500, 700);
        this.setResizable(false);
        this.setLayout(null);

        mainLabel = new JLabel(minutes_string + ":" + seconds_string);
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

        enteredSeconds = new JTextField();
        enteredSeconds.setBounds(270, 60, 100, 25);
        enteredSeconds.setBackground(new Color(0xC4C4C4));
        enteredSeconds.setFont(new Font("Roboto", Font.PLAIN, 15));

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
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == backToMain) {
            this.dispose();
            new MainPanel();
        }
        if (e.getSource() == acceptTimeInput) {
            try {
                minutes = Integer.parseInt(enteredMinutes.getText());
            }
            catch(NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Minutes must be an Integer", "Minutes Error", JOptionPane.ERROR_MESSAGE);
            }
            try {
                seconds = Integer.parseInt(enteredSeconds.getText());
            }
            catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Seconds must be an Integer", "Seconds Error", JOptionPane.ERROR_MESSAGE);
            }
            elapsedTime = minutes * 60 + seconds;
            if (seconds > 59) {
                JOptionPane.showMessageDialog(null, "Seconds can't be more than 59", "Seconds Error", JOptionPane.ERROR_MESSAGE);
            } else {
                mainLabel.setText(minutes + ":" + seconds);
            }
        }
        if (e.getSource() == start) {
            if (!isStarted) {
                start.setText("STOP");
                start();
            } else {
                start.setText("START");
                stop();
            }
        }
        if (e.getSource() == reset) {
            reset();
        }
    }
    void start() {
        timer.start();
        isStarted = true;
    }
    void stop() {
        isStarted = false;
        timer.stop();
    }
    void reset() {
        stop();
        start.setText("START");
        elapsedTime = 0;
        minutes = 0;
        seconds = 0;
        minutes_string = String.format("%02d", minutes);
        seconds_string = String.format("%02d", seconds);
        mainLabel.setText(minutes_string + ":" + seconds_string);
    }
}