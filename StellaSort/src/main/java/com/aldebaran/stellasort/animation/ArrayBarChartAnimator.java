package com.aldebaran.stellasort.animation;

import com.aldebaran.stellasort.component.ArrayBarChart;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class ArrayBarChartAnimator {
    private final ArrayBarChart barChart;

    public ArrayBarChartAnimator(ArrayBarChart barChart) {
        this.barChart = barChart;
    }

    public Animation changeBarValue(int index, int oldValue, int newValue) {
        Timeline timeline = new Timeline();

        KeyFrame highlightBar = new KeyFrame(Duration.millis(200), actionEvent -> {
            barChart.setBarValue(index, oldValue);
            barChart.addBarStyleClass(index, "bar-highlighted");
        });
        KeyFrame incrementValue = new KeyFrame(Duration.millis(700), actionEvent -> {
            barChart.setBarValue(index, newValue);
        });
        KeyFrame resetBar = new KeyFrame(Duration.millis(1000), actionEvent -> {
//            barChart.setBarValue(index, oldValue);
            barChart.removeBarStyleClass(index, "bar-highlighted");
        });

        timeline.getKeyFrames().add(highlightBar);
        timeline.getKeyFrames().add(incrementValue);
        timeline.getKeyFrames().add(resetBar);

        return timeline;
    }

    public Animation incrementBarAnimation(int index, int oldValue) {
        return changeBarValue(index, oldValue, oldValue + 1);
    };

    public Animation decrementBarAnimation(int index, int oldValue) {
        return changeBarValue(index, oldValue, oldValue - 1);
    };

}
