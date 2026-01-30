package com.aldebaran.stellasort.controller;

import com.aldebaran.stellasort.animation.ArrayBarChartAnimator;
import com.aldebaran.stellasort.animation.ArrayDisplayAnimator;
import com.aldebaran.stellasort.animation.CountingSortProcessTextsAnimator;
import com.aldebaran.stellasort.component.ArrayBarChart;
import com.aldebaran.stellasort.component.ArrayDisplay;
import com.aldebaran.stellasort.component.InputArray;
import com.aldebaran.stellasort.component.PlayButton;
import com.aldebaran.stellasort.service.CountingSort;
import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.animation.SequentialTransition;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// TODO: figure out how best to load the array into the bar chart x
// TODO: create animation to play when an element is added to the counting array x
// TODO: create animation to play when element is evaluated in the input array
// TODO: create animation for when an element in the sorting array is calculated

public class CountingSortController {
    //#region Input Array and Input Array Component
    @FXML private TextArea inputArrayTextArea;
    @FXML private Text inputArrayErrorMessageLabel;
    @FXML private Button inputArrayButton; // disabled until input is valid

    private InputArray inputArray;
    private final List<Integer> input = new ArrayList<>();
    //#endregion

    //#region Play Button Component
    @FXML private Button playButtonBarChart;
    private PlayButton playButtonBarChartSort;
    //#endregion

    //#region Array Display Components
    @FXML private FlowPane inputArrayFlowPane;
    private ArrayDisplay inputArrayDisplay;
    private ArrayDisplayAnimator inputArrayDisplayAnimator;

    @FXML private FlowPane resultArrayFlowPane;
    private ArrayDisplay resultArrayDisplay;
    private ArrayDisplayAnimator resultArrayDisplayAnimator;
    //#endregion

    //#region Process Label Components
    @FXML private Text inputToCountProcessText;
    @FXML private Text countToResultProcessText;

    private CountingSortProcessTextsAnimator countingSortProcessLabelsAnimator;
    //#endregion

    //#region Bar Chart Component
    @FXML private BarChart<String, Number> barChart;
    private ArrayBarChart arrayBarChart;
    private ArrayBarChartAnimator arrayBarChartAnimator;
    //#endregion

    @FXML private void initialize() {
        // Input Array Components
        inputArray = new InputArray(inputArrayTextArea, inputArrayErrorMessageLabel, inputArrayButton, (i) -> (i < 0) ? "All input values must be positive integers" : null);
        // Play Button Component
        playButtonBarChartSort = new PlayButton(playButtonBarChart);
        // Array Display Components
        inputArrayDisplay = new ArrayDisplay(inputArrayFlowPane);
        inputArrayDisplayAnimator = new ArrayDisplayAnimator(inputArrayDisplay);
        resultArrayDisplay = new ArrayDisplay(resultArrayFlowPane);
        resultArrayDisplayAnimator = new ArrayDisplayAnimator(resultArrayDisplay);
        // Process Label Components
        inputToCountProcessText.setVisible(false);
        countToResultProcessText.setVisible(false);
        countingSortProcessLabelsAnimator = new CountingSortProcessTextsAnimator(inputToCountProcessText, countToResultProcessText);
        // Bar Chart Component
        arrayBarChart = new ArrayBarChart(barChart);
        arrayBarChartAnimator = new ArrayBarChartAnimator(arrayBarChart);
        arrayBarChart.initializeBarChart();
    }

    //#region Sorts with Animations
    private void sortWithBarChartAnimations() {
        // Display empty result array display
        List<String> emptyArray = Collections.nCopies(input.size(), "");
        resultArrayDisplay.setArray(emptyArray, (i) -> "array-element-disabled");

        int max = input.stream().max(Integer::compareTo).orElse(0);
        List<Integer> zeroArray = Collections.nCopies(max+1, 0);

        arrayBarChart.setYBounds(0, input.size());
        arrayBarChart.setBarChart(zeroArray, ArrayBarChart.NO_RULE);

        // This is probably unnecessary
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                if (arrayBarChart.getDatasetSize() == max+1) {
                    System.out.println("Counting Sort Animation Finished");
                    List<Animation> countingSortAnimations = CountingSort.getSortAnimations(
                            input,
                            max,
                            (index, oldValue) -> {
                                return List.of(arrayBarChartAnimator.incrementBarAnimation(index, oldValue), countingSortProcessLabelsAnimator.hideTexts());
                            },
                            (index, oldValue) -> {
                                return List.of(arrayBarChartAnimator.decrementBarAnimation(index, oldValue));
                            },
                            (inputIndex, inputValue) -> {
                                return List.of(countingSortProcessLabelsAnimator.countInput(inputValue), inputArrayDisplayAnimator.highlightThenDisableElement(inputIndex));
                            },
                            (inputIndex, inputValue, resultIndex, resultValue, resultIndexOffset) -> {
                                return List.of(countingSortProcessLabelsAnimator.writeResult(inputIndex, inputValue, resultIndex, resultIndexOffset), inputArrayDisplayAnimator.highlightThenDisableElement(inputIndex));
                            },
                            (index, indexOffset, newValue) -> {
                                return List.of(resultArrayDisplayAnimator.enableElementAndSetValue(index, Integer.toString(newValue)), countingSortProcessLabelsAnimator.hideTexts());
                            }
                    );

                    SequentialTransition sequence = new SequentialTransition();
                    sequence.getChildren().addAll(countingSortAnimations);
                    sequence.play();
                    playButtonBarChartSort.togglePlayPause();
                    stop();
                }
            }
        };

        timer.start();
    }
    //#endregion

    //#region Button Actions

    @FXML private void onInputArrayButtonClicked() {
        // Display input in input array display
        inputArray.onInputButtonClick(input);
        inputArrayDisplay.setArray(input.stream().map(val -> Integer.toString(val)).toList(), ArrayDisplay.NO_RULE);
        // Display empty result array display
        List<String> emptyArray = Collections.nCopies(input.size(), "");
        resultArrayDisplay.setArray(emptyArray, (i) -> "array-element-disabled");
    }

    @FXML private void onPlaySortBarChart() {
        playButtonBarChartSort.togglePlayPause();
        if (playButtonBarChartSort.isPlaying()) sortWithBarChartAnimations();
    }

    //#endregion
}
