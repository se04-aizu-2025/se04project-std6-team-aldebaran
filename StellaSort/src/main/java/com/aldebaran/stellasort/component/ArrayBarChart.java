package com.aldebaran.stellasort.component;

import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.chart.*;
import org.jetbrains.annotations.Nullable;

import java.net.URL;
import java.util.List;

public class ArrayBarChart {
    @FunctionalInterface
    public interface BarCssRule {
        @Nullable String getStyleClass(int index);
    }
    public static BarCssRule NO_RULE = (i) -> null;
    private static final String DEFAULT_CSS_FILE = "/com/aldebaran/stellasort/array-bar-chart.css";

    private final BarChart<String, Number> barChart;

    public ArrayBarChart(BarChart<String, Number> barChart) {
        this.barChart = barChart;
        barChart.setAnimated(false);
        try {
            URL barChartCss = getClass().getResource(DEFAULT_CSS_FILE);
            assert barChartCss != null;
            barChart.getStylesheets().add(barChartCss.toExternalForm());
        } catch (NullPointerException e) {
            System.out.println("Bar chart CSS does not exists.");
        }
    }

    public void initializeBarChart() {
        // Set the initial array
        // TODO: potentially create a way to set the initial y-axis range so that I can have an initially empty bar chart
        List<Integer> initialArray = List.of(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        setBarChart(initialArray, NO_RULE);
    }

    public int getYValueAt(int index) {
        return (int) barChart.getData().getFirst().getData().get(index).getYValue();
    }

    public void setYBounds(int min, int max) {
        NumberAxis yAxis = (NumberAxis) barChart.getYAxis();
        yAxis.setAutoRanging(false);
        yAxis.setLowerBound(min);
        yAxis.setUpperBound(max);
    }

    public void setBarChart(List<Integer> countingArray, BarCssRule barCssRule) {
        // Clear the chart
        barChart.getData().clear();
        CategoryAxis xAxis = (CategoryAxis) barChart.getXAxis();
        xAxis.getCategories().clear();

        // Set the categories
        for (int i = 0; i < countingArray.size(); i++) {
            xAxis.getCategories().add(Integer.toString(i));
        }

        // Set the data
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Counting Array");
        for (int i = 0; i < countingArray.size(); i++) {
            series.getData().add(new XYChart.Data<>(Integer.toString(i), countingArray.get(i)));
        }

        barChart.getData().add(series);

        Platform.runLater(() -> {
            barChart.applyCss();
            barChart.layout();

            for (int i = 0; i < series.getData().size(); i++) {
                Node node = series.getData().get(i).getNode();
                if (node == null) continue;

                String styleClass = barCssRule.getStyleClass(i);
                if (styleClass != null && !node.getStyleClass().contains(styleClass)) {
                    node.getStyleClass().add(styleClass);
                }

                node.applyCss();
            }
        });
    }


    // TODO: doesn't check if the index or value is valid
    public void setBarValue(int index, int value) {
        XYChart.Data<String, Number> bar = barChart.getData().getFirst().getData().get(index);
        // Set the value
        bar.setYValue(value);
    }

    // TODO: doesn't check if the index is valid
    public void addBarStyleClass(int index, String styleClass) {
        XYChart.Data<String, Number> bar = barChart.getData().getFirst().getData().get(index);
        // Add the style class
        bar.getNode().getStyleClass().add(styleClass);
    }

    public void removeBarStyleClass(int index, String styleClass) {
        XYChart.Data<String, Number> bar = barChart.getData().getFirst().getData().get(index);
        // Set the style class
        // Remove only removes the first occurrence, however, due to the way setBar is written, there should always only be one occurrence
        if (styleClass != null) bar.getNode().getStyleClass().remove(styleClass);
    }
}
