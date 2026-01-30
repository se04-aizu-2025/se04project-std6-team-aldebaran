package com.aldebaran.stellasort.controller;

import com.aldebaran.stellasort.animation.ArrayDisplayAnimator;
import com.aldebaran.stellasort.animation.CountingSortProcessTextsAnimator;
import com.aldebaran.stellasort.animation.HeapDisplayAnimator;
import com.aldebaran.stellasort.component.*;
import com.aldebaran.stellasort.service.HeapSort;
import javafx.animation.Animation;
import javafx.animation.SequentialTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class HeapSortController {
    //#region Input Array and Input Array Component
    @FXML
    private TextArea inputArrayTextArea;
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

    //#region HeapDisplay Component
    @FXML private Pane heapPane;
    private HeapDisplay heapDisplay;
    private HeapDisplayAnimator heapDisplayAnimator;
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
        // Heap Display Components
        heapDisplay = new HeapDisplay(heapPane);
    }

    //#region Sort with Animations
    private void sortWithHeapAnimations() {
        List<Animation> heapSortAnimations = HeapSort.getSortAnimations(input, (i, j) -> {
            return List.of(heapDisplay.swapNodesAnimation(i, j));
        }, (index, value) -> {
            return List.of(heapDisplay.removeFinalNodeAnimations(index), resultArrayDisplayAnimator.enableElementAndSetValue(index, Integer.toString(value)));
        });

        SequentialTransition sequence = new SequentialTransition();
        sequence.getChildren().addAll(heapSortAnimations);
        sequence.play();

        playButtonBarChartSort.togglePlayPause();
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
        // Render the initial heap
        heapDisplay.renderHeap(input.stream().mapToInt(i -> i).toArray());
    }

    @FXML private void onPlaySortHeap() {
        int[] testHeap = { 0, 1, 4, 10, 9, 8, 1, 2, 3, 9 };
        List<Integer> testArray = Arrays.stream(testHeap).boxed().toList();
        playButtonBarChartSort.togglePlayPause();
        if (playButtonBarChartSort.isPlaying()) {
            sortWithHeapAnimations();
        }
    }

    //#endregion
}
