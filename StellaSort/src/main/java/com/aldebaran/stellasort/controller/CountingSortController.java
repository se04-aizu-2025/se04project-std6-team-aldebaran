package com.aldebaran.stellasort.controller;

import com.aldebaran.stellasort.animation.ArrayBarChartAnimator;
import com.aldebaran.stellasort.component.ArrayBarChart;
import com.aldebaran.stellasort.component.ArrayDisplay;
import com.aldebaran.stellasort.component.InputArray;
import com.aldebaran.stellasort.component.PlayButton;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;

// TODO: figure out how best to load the array into the bar chart
// TODO: create animation to play when an element is added to the counting array
// TODO: create animation to play when element is evaluated in the input array
// TODO: create animation for when an element in the sorting array is calculated

public class CountingSortController {

    /*
     * The text field displays the input array
     */
    @FXML private TextArea inputArrayTextArea;
    @FXML private Text inputArrayErrorMessageLabel;
    @FXML private Button inputArrayButton; // disabled until input is valid

    private InputArray inputArray;
    private final List<Integer> input = new ArrayList<>();

    /*
     * The input and result array display
     */

    @FXML private FlowPane inputArrayFlowPane;
    private ArrayDisplay inputArrayDisplay;

    /*
     * The play button starts the sorting process
     */

    /*
     * The array display displays the input array and the sorted array
     * It displays how they are progressed through by the sorting algorithm
     */

    /*
     * The bar chart displays the index of the counting array as the x-axis labels and the count at that position as the y-axis values
     */
    @FXML private BarChart<String, Number> barChart;

    private ArrayBarChart arrayBarChart;
    private ArrayBarChartAnimator arrayBarChartAnimator;

    @FXML private void initialize() {
        inputArray = new InputArray(inputArrayTextArea, inputArrayErrorMessageLabel, inputArrayButton, (i) -> (i < 0) ? "All input values must be positive integers" : null);
        inputArrayDisplay = new ArrayDisplay(inputArrayFlowPane);
        arrayBarChart = new ArrayBarChart(barChart);
        arrayBarChartAnimator = new ArrayBarChartAnimator(arrayBarChart);
        arrayBarChart.initializeBarChart();
    }

    private void onInputSet() {

    }

    @FXML private void onPlaySortButtonClick() {
        List<Integer> inputArray = List.of(2, 1, 3, 5, 3, 2);
        arrayBarChart.setBarChart(inputArray, (int i) -> (i % 2 == 0) ? "bar-highlighted" : null );

        inputArrayDisplay.setArray(inputArray, (i) -> (i % 2 == 0) ? "array-element-highlighted" : null);
    }

    @FXML private void onSetInputButtonClick() {
//        List<Integer> inputArray = List.of(3, 3, 3, 1, 1, 6, 6, 7, 8, 9);
//        int max = inputArray.stream().max(Integer::compareTo).orElse(0);
//        List<Integer> zeroArray = Collections.nCopies(inputArray.size(), 0);
//
//        arrayBarChart.setYBounds(0, inputArray.size()+1);
//        arrayBarChart.setBarChart(zeroArray, ArrayBarChart.NO_RULE);
//
//        List<Animation> countingSortAnimations = CountingSort.getSortAnimations(
//                inputArray,
//                max,
//                (index, oldValue) -> {
//                    return List.of(arrayBarChartAnimator.incrementBarAnimation(index, oldValue));
//                },
//                (index, oldValue) -> {
//                    return List.of(arrayBarChartAnimator.decrementBarAnimation(index, oldValue));
//                }
//        );
//
//        SequentialTransition sequence = new SequentialTransition();
//        sequence.getChildren().addAll(countingSortAnimations);
//        sequence.play();
    }

    @FXML private void onInputArrayButtonClicked() {
        inputArray.onInputButtonClick(input);
        inputArrayDisplay.setArray(input, ArrayDisplay.NO_RULE);
    }

    //#region BarChart Method
}
