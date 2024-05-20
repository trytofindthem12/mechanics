import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;

public class TimeTable extends JFrame implements ActionListener {

    private JPanel screen = new JPanel(), tools = new JPanel();
    private JButton tool[];
    private JTextField field[];
    private CourseArray courses;
    private Color CRScolor[] = { Color.RED, Color.GREEN, Color.BLACK };
    private BufferedWriter logFile;

    public TimeTable() {
        super("Dynamic Time Table");
        setSize(600, 900);
        setLayout(new FlowLayout());

        screen.setPreferredSize(new Dimension(400, 800));
        add(screen);

        setTools();
        add(tools);

        // Open log file
        try {
            logFile = new BufferedWriter(new FileWriter("log.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        setVisible(true);
    }

    public void setTools() {
        String capField[] = { "Slots:", "Courses:", "Clash File:", "Iters:", "Shift:" };
        field = new JTextField[capField.length];

        String capButton[] = { "Load", "Start", "Step", "Print", "Exit", "Continue" }; // Added "Continue" button
        tool = new JButton[capButton.length];

        tools.setLayout(new GridLayout(2 * capField.length + capButton.length, 1));

        for (int i = 0; i < field.length; i++) {
            tools.add(new JLabel(capField[i]));
            field[i] = new JTextField(5);
            tools.add(field[i]);
        }

        for (int i = 0; i < tool.length; i++) {
            tool[i] = new JButton(capButton[i]);
            tool[i].addActionListener(this);
            tools.add(tool[i]);
        }

        field[0].setText("19");
        field[1].setText("461");
        field[2].setText("kfu-s-93.stu");
        field[3].setText("1");
    }

    public void draw() {
        Graphics g = screen.getGraphics();
        int width = Integer.parseInt(field[0].getText()) * 10;
        for (int courseIndex = 1; courseIndex < courses.length(); courseIndex++) {
            g.setColor(CRScolor[courses.status(courseIndex) > 0 ? 0 : 1]);
            g.drawLine(0, courseIndex, width, courseIndex);
            g.setColor(CRScolor[CRScolor.length - 1]);
            g.drawLine(10 * courses.slot(courseIndex), courseIndex, 10 * courses.slot(courseIndex) + 10,
                    courseIndex);
        }
    }

    private int getButtonIndex(JButton source) {
        int result = 0;
        while (source != tool[result])
            result++;
        return result;
    }

    public void actionPerformed(ActionEvent click) {
        int min, step, clashes;

        switch (getButtonIndex((JButton) click.getSource())) {
            case 0:
                int slots = Integer.parseInt(field[0].getText());
                courses = new CourseArray(Integer.parseInt(field[1].getText()) + 1, slots);
                courses.readClashes(field[2].getText());
                draw();
                break;
            case 1:
                min = Integer.MAX_VALUE;
                step = 0;
                for (int i = 1; i < courses.length(); i++)
                    courses.setSlot(i, 0);

                for (int iteration = 1; iteration <= Integer.parseInt(field[3].getText()); iteration++) {
                    courses.iterate(Integer.parseInt(field[4].getText()));
                    draw();
                    clashes = courses.clashesLeft();
                    if (clashes < min) {
                        min = clashes;
                        step = iteration;
                    }
                }
                try {
                    logFile.write("Shift = " + field[4].getText() + "\tMin clashes = " + min + "\tat step " + step + "\n");
                    logFile.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                setVisible(true);
                break;
            case 2:
                courses.iterate(Integer.parseInt(field[4].getText()));
                draw();
                break;
            case 3:
                System.out.println("Exam\tSlot\tClashes");
                for (int i = 1; i < courses.length(); i++)
                    System.out.println(i + "\t" + courses.slot(i) + "\t" + courses.status(i));
                break;
            case 4:
                // Close log file
                try {
                    logFile.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.exit(0);
                break;
            case 5: // Continue button action
                courses.iterateExam(Integer.parseInt(field[1].getText()), Integer.parseInt(field[4].getText()));
                draw();
                break;
        }
    }

    public static void main(String[] args) {
		        new TimeTable();
    }
}

