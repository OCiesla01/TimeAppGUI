import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Clock extends JFrame implements ActionListener {
    JLabel mainLabel, topLine, dayLabel;
    JButton backToMain;
    SimpleDateFormat timeFormat, dayFormat, dateFormat;
    String time, day, date;
    Clock() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(600, 400);
        this.setLayout(null);
        this.setResizable(false);
        this.setTitle("Clock");

        timeFormat = new SimpleDateFormat("hh:mm:ss a");
        time = timeFormat.format(Calendar.getInstance().getTime());

        dayFormat = new SimpleDateFormat("EEEE");
        day = dayFormat.format(Calendar.getInstance().getTime());

        dateFormat = new SimpleDateFormat("dd MMMMM yyyy");
        date = dateFormat.format(Calendar.getInstance().getTime());

        dayLabel = new JLabel();
        dayLabel.setBounds(115, 270, 400, 50);
        dayLabel.setFont(new Font("Oswald", Font.PLAIN, 35));
        dayLabel.setOpaque(true);
        dayLabel.setText(day + ", " + date);

        mainLabel = new JLabel();
        mainLabel.setText(time);
        mainLabel.setHorizontalAlignment(JLabel.CENTER);
        mainLabel.setVerticalAlignment(JLabel.CENTER);
        mainLabel.setFont(new Font("MV Boli", Font.PLAIN, 50));
        mainLabel.setBounds(45, 50, 485, 200);
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


        this.add(dayLabel);
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
