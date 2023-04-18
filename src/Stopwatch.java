import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class Stopwatch extends JFrame implements ActionListener {
    JButton backToMain;
    JLabel topLine;
    JLabel mainLabel;
    JButton start;
    JButton reset;
    boolean isStarted = false;
    int elapsedTime = 0;
    int hours = 0;
    int minutes = 0;
    int seconds = 0;
    String hours_string = String.format("%02d", hours);
    String minutes_string = String.format("%02d", minutes);
    String seconds_string = String.format("%02d", seconds);
    Timer timer = new Timer(1000, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            elapsedTime += 1000;
            hours = (elapsedTime / 3_600_000);
            minutes = (elapsedTime / 60_000) % 60;
            seconds = (elapsedTime / 1000) % 60;
            hours_string = String.format("%02d", hours);
            minutes_string = String.format("%02d", minutes);
            seconds_string = String.format("%02d", seconds);
            mainLabel.setText(hours_string + ":" + minutes_string + ":" + seconds_string);
        }
    });
    Stopwatch() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(500, 700);
        this.setResizable(false);
        this.setLayout(null);

        start = new JButton("START");
        start.setBounds(100, 520, 100, 50);
        start.setBackground(new Color(0xB4B4B4));
        start.setBorder(BorderFactory.createLineBorder(new Color(0x9B9B9B), 5));
        start.addActionListener(this);
        start.setFocusable(false);

        reset = new JButton("RESET");
        reset.setBounds(270, 520, 100, 50);
        reset.setBackground(new Color(0xB4B4B4));
        reset.setBorder(BorderFactory.createLineBorder(new Color(0x9B9B9B), 5));
        reset.addActionListener(this);
        reset.setFocusable(false);

        mainLabel = new JLabel(hours_string + ":" + minutes_string + ":" + seconds_string);
        mainLabel.setHorizontalAlignment(JTextField.CENTER);
        mainLabel.setVerticalAlignment(JTextField.CENTER);
        mainLabel.setFont(new Font("MV Boli", Font.BOLD, 50));
        mainLabel.setBounds(40, 60, 400, 450);
        mainLabel.setBackground(new Color(0xCDCDCD));
        mainLabel.setBorder(BorderFactory.createLineBorder(new Color(0xB4B4B4), 3));
        mainLabel.setOpaque(true);

        topLine = new JLabel();
        topLine.setBounds(0, 40, 700, 3);
        topLine.setBackground(Color.black);
        topLine.setOpaque(true);

        backToMain = new JButton("<- Go Back");
        backToMain.setBounds(0, 0, 100, 30);
        backToMain.setFocusable(false);
        backToMain.addActionListener(this);

        this.add(start);
        this.add(reset);
        this.add(mainLabel);
        this.add(topLine);
        this.add(backToMain);
        this.setVisible(true);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == backToMain) {
            this.dispose();
            new MainPanel();
        } else if (e.getSource() == start) {
            if (!isStarted) {
                start.setText("STOP");
                isStarted = true;
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
    }
    void stop() {
        isStarted = false;
        timer.stop();
    }
    void reset() {
        stop();
        start.setText("START");
        elapsedTime = 0;
        hours = 0;
        minutes = 0;
        seconds = 0;
        hours_string = String.format("%02d", hours);
        minutes_string = String.format("%02d", minutes);
        seconds_string = String.format("%02d", seconds);
        mainLabel.setText(hours_string + ":" + minutes_string + ":" + seconds_string);
    }
}