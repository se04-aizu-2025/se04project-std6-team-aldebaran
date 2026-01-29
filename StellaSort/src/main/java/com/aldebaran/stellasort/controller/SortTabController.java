package com.aldebaran.stellasort.controller;
import com.aldebaran.stellasort.service.SortAlgorithm;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.chart.BarChart;
import java.util.Arrays;
import javafx.scene.control.Label;

public class SortTabController {

    private SortAlgorithm algorithm;

	@FXML private TextArea inputArea;
    @FXML private BarChart<String, Number> arrayBarChartNode;
	@FXML private Label statusLabel;
	
	private int[] array;
	
    public void setAlgorithm(SortAlgorithm algorithm) {
        this.algorithm = algorithm;
    }

    @FXML
    private void onSort() {
        switch (algorithm) {
            case BUBBLE -> {}
            case COUNTING -> {}
            case HEAP -> {}
            case QUICK -> {}
        }
    }
	
	@FXML
	private void onRandomize() {
	
	}
	
	@FXML
	private void onInputArray() {
    try {
        String text = inputArea.getText();
        array = Arrays.stream(text.split("[,\\s]+"))
                      .mapToInt(Integer::parseInt)
                      .toArray();
					  
		statusLabel.setText("");

    } catch (Exception e) {
        statusLabel.setText("Invalid input");
    }
}
	
	
}







	