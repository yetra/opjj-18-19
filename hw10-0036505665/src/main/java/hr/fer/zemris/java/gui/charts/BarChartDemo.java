package hr.fer.zemris.java.gui.charts;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class BarChartDemo extends JFrame {

    private BarChart chart;

    public BarChartDemo(BarChart chart) throws HeadlessException {
        this.chart = chart;

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("BarChart");
        setLocation(20, 20);
        setSize(1000, 600);
        setBackground(Color.WHITE);

        initGUI();
    }

    private void initGUI() {
        Container cp = getContentPane();
//        cp.setLayout(null);

        JComponent chartComponent = new BarChartComponent(chart);
        cp.add(chartComponent);
    }

    public static void main(String[] args) {
        BarChart chart = new BarChart(
                Arrays.asList(
                        new XYValue(1,8), new XYValue(2,20), new XYValue(3,22),
                        new XYValue(4,10), new XYValue(5,4)
                ),
                "Number of people in the car",
                "Frequency",
                0,      // y-os kreće od 0
                22,     // y-os ide do 22
                2
        );

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame frame = new BarChartDemo(chart);
                frame.setVisible(true);
            }
        });
    }
}
