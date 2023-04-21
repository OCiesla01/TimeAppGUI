import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Clock extends JFrame implements ActionListener {
    JLabel mainLabel, topLine;
    JButton backToMain;
//    Calendar calendar;
    SimpleDateFormat timeFormat;
    String time;
    Clock() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(600, 400);
        this.setLayout(null);
        this.setResizable(false);
        this.setTitle("Clock");

        timeFormat = new SimpleDateFormat("hh:mm:ss a");
        time = timeFormat.format(Calendar.getInstance().getTime());

        mainLabel = new JLabel();
        mainLabel.setText(time);
        mainLabel.setHorizontalAlignment(JLabel.CENTER);
        mainLabel.setVerticalAlignment(JLabel.CENTER);
        mainLabel.setFont(new Font("MV Boli", Font.PLAIN, 50));
        mainLabel.setBounds(45, 70, 485, 250);
        mainLabel.setBackground(new Color(0xCDCDCD));
        mainLabel.setOpaque(true);
        mainLabel.setBorder(BorderFactory.createLineBorder(new Color(0xA8A8A8), 2));

        topLine = new JLabel();
        topLine.setBounds(0, 35, 600, 3);
        topLine.setBackground(Color.black);
        topLine.setOpaque(true);

        backToMain = new JButton("<- Go Back");
        backToMain.setBounds(0, 0, 100, 30);
        backToMain.setFocusable(false);
        backToMain.addActionListener(this);

        this.add(backToMain);
        this.add(topLine);
        this.add(mainLabel);
        this.setVisible(true);

        setTime();

    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == backToMain) {
            this.dispose();
            new MainPanel();
        }
    }
    public void setTime() {
        timer.start();
    }
    Timer timer = new Timer(1000, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            time = timeFormat.format(Calendar.getInstance().getTime());
            mainLabel.setText(time);
        }
    });
}
