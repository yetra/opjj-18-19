package hr.fer.zemris.java.gui.charts;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * This program demonstrated the {@link BarChart} and {@link BarChartComponent}
 * classes. It reads chart data from a file specified through the command-line
 * arguments and displays the corresponding chart on the {@link BarChartComponent}
 * frame.
 *
 * The default chart file is ./test_chart.txt
 *
 * @author Bruna Dujmović
 *
 */
public class BarChartDemo extends JFrame {

    /**
     * The chart to display.
     */
    private BarChart chart;

    /**
     * The path to the file that contains chart data.
     */
    private Path filePath;

    /**
     * Constructs a {@link BarChartDemo} frame.
     *
     * @param chart the chart to display
     * @param filePath the path to the file that contains chart data
     */
    public BarChartDemo(BarChart chart, Path filePath) {
        this.chart = chart;
        this.filePath = filePath;

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("BarChartDemo");
        setLocation(20, 20);
        setSize(1000, 600);
        setBackground(Color.WHITE);

        initGUI();
    }

    /**
     * Initializes the {@link BarChartDemo} GUI.
     */
    private void initGUI() {
        Container cp = getContentPane();
        cp.setLayout(new BorderLayout());
        cp.setBackground(Color.WHITE);

        JLabel label = new JLabel(filePath.toString(), SwingConstants.CENTER);
        label.setBorder(new EmptyBorder(5,0,10,0));
        cp.add(label, BorderLayout.NORTH);
        cp.add(new BarChartComponent(chart), BorderLayout.CENTER);
    }

    /**
     * The main method. Reads chart data from a file whose path is specified through
     * the command-line arguments and displays the chart on a {@link BarChartDemo}
     * frame.
     *
     * @param args the command-line arguments, one is expected - a path to the file
     *             containing chart data
     */
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("This program expects 1 argument, "
                    + args.length + " were given.");
            System.exit(1);
        }

        try {
            Path filePath = Paths.get(args[0]);
            List<String> lines = Files.readAllLines(filePath);
            BarChart chart = parseToBarChart(lines);

            SwingUtilities.invokeLater(() -> {
                JFrame frame = new BarChartDemo(chart, filePath);
                frame.setVisible(true);
            });

        } catch (IOException | InvalidPathException e) {
            System.out.println("Issue with reading the given file.");
            System.exit(1);

        } catch (NumberFormatException e) {
            System.out.println("Illegal value format!");
            System.exit(1);

        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }
    }

    /**
     * Parses the given list of lines to a {@link BarChart} object.
     *
     * @param lines the list of lines to parse
     * @return a {@link BarChart} object parsed from the given string
     * @throws IllegalArgumentException if the lines cannot be properly parsed
     */
    private static BarChart parseToBarChart(List<String> lines) {
        if (lines.size() < 6) {
            System.out.println("File must have at least 6 lines!");
            System.exit(1);
        }

        String[] valueStrings = lines.get(2).split("\\s");

        List<XYValue> values = new ArrayList<>();
        for (String valueString : valueStrings) {
            values.add(XYValue.fromString(valueString));
        }

        int minY = Integer.parseInt(lines.get(3));
        int maxY = Integer.parseInt(lines.get(4));
        int ySpacing = Integer.parseInt(lines.get(5));

        return new BarChart(values, lines.get(0), lines.get(1), minY, maxY, ySpacing);
    }
}
