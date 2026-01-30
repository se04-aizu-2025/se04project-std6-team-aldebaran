package com.aldebaran.stellasort.controller;

import com.aldebaran.stellasort.animation.ArrayBarChartAnimator;
import com.aldebaran.stellasort.component.ArrayBarChart;
import com.aldebaran.stellasort.service.CountingSort;
import javafx.animation.Animation;
import javafx.animation.SequentialTransition;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;

import java.util.Collections;
import java.util.List;

// TODO: figure out how best to load the array into the bar chart
// TODO: create animation to play when an element is added to the counting array
// TODO: create animation to play when element is evaluated in the input array
// TODO: create animation for when an element in the sorting array is calculated

public class CountingSortController {

    /*
     * The text field displays the input array
     */

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
        arrayBarChart = new ArrayBarChart(barChart);
        arrayBarChartAnimator = new ArrayBarChartAnimator(arrayBarChart);
        arrayBarChart.initializeBarChart();
    }

    private void onInputSet() {

    }

    @FXML private void onPlaySortButtonClick() {
        List<Integer> inputArray = List.of(2, 1, 3, 5, 3, 2);
        arrayBarChart.setBarChart(inputArray, (int i) -> (i % 2 == 0) ? "bar-highlight" : null );
    }

    @FXML private void onSetInputButtonClick() {
        List<Integer> inputArray = List.of(3, 3, 3, 1, 1, 6, 6, 7, 8, 9);
        int max = inputArray.stream().max(Integer::compareTo).orElse(0);
        List<Integer> zeroArray = Collections.nCopies(inputArray.size(), 0);

        arrayBarChart.setYBounds(0, inputArray.size()+1);
        arrayBarChart.setBarChart(zeroArray, ArrayBarChart.NO_RULE);

        List<Animation> countingSortAnimations = CountingSort.getSortAnimations(
                inputArray,
                max,
                (index, oldValue) -> {
                    return List.of(arrayBarChartAnimator.incrementBarAnimation(index, oldValue));
                },
                (index, oldValue) -> {
                    return List.of(arrayBarChartAnimator.decrementBarAnimation(index, oldValue));
                }
        );

        SequentialTransition sequence = new SequentialTransition();
        sequence.getChildren().addAll(countingSortAnimations);
        sequence.play();
    }

    //#region BarChart Methodd
}
