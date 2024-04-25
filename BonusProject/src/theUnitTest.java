import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.junit.Test;

import javax.swing.*;

import static org.junit.Assert.assertEquals;

public class theUnitTest {

    @Test
    public void testGraphCreation() {
        XYSeries series = new XYSeries("Test Series");
        series.add(1, 10);
        series.add(2, 20);
        series.add(3, 30);

        XYSeriesCollection dataset = new XYSeriesCollection(series);
        JFreeChart chart = ChartFactory.createXYLineChart("Test Chart", "X", "Y", dataset);

        ChartPanel chartPanel = new ChartPanel(chart);

        JFrame frame = new JFrame("Graph Test");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(chartPanel);
        frame.pack();
        frame.setVisible(true);

        assertEquals("Test Series", chart.getXYPlot().getDataset().getSeriesKey(0));
        assertEquals(1.0, chart.getXYPlot().getDataset().getXValue(0, 0), 0.001);
        assertEquals(10.0, chart.getXYPlot().getDataset().getYValue(0, 0), 0.001);
        assertEquals(2.0, chart.getXYPlot().getDataset().getXValue(0, 1), 0.001);
        assertEquals(20.0, chart.getXYPlot().getDataset().getYValue(0, 1), 0.001);
        assertEquals(3.0, chart.getXYPlot().getDataset().getXValue(0, 2), 0.001);
        assertEquals(30.0, chart.getXYPlot().getDataset().getYValue(0, 2), 0.001);
    }

    @Test
    public void testPinConstants() {
        assertEquals(14, Pins.A0);
        assertEquals(15, Pins.A1);
        assertEquals(16, Pins.A2);
        assertEquals(2, Pins.D2);
        assertEquals(3, Pins.D3);
        assertEquals(6, Pins.D6);
        assertEquals(4, Pins.D4);
        assertEquals(13, Pins.D13);
        assertEquals(0x3C, Pins.I2C0);
    }
}
