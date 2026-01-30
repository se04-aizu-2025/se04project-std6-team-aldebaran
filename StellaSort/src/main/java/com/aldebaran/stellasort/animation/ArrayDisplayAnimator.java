package com.aldebaran.stellasort.animation;

import com.aldebaran.stellasort.component.ArrayDisplay;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class ArrayDisplayAnimator {
    private final ArrayDisplay arrayDisplay;

    public ArrayDisplayAnimator(ArrayDisplay arrayDisplay) {
        this.arrayDisplay = arrayDisplay;
    }

    public Animation highlightThenDisableElement(int index) {
        Timeline timeline = new Timeline();

        KeyFrame highlightElement = new KeyFrame(Duration.millis(200), actionEvent -> {
            arrayDisplay.removeElementStyleClass(index, "array-element-disabled"); // if already disabled, this will remove the class
            arrayDisplay.addElementStyleClass(index, "array-element-highlighted");
        });

        KeyFrame disableElement = new KeyFrame(Duration.millis(500), actionEvent -> {
            arrayDisplay.removeElementStyleClass(index, "array-element-highlighted");
            arrayDisplay.addElementStyleClass(index, "array-element-disabled");
        });

        timeline.getKeyFrames().add(highlightElement);
        timeline.getKeyFrames().add(disableElement);

        return timeline;
    }

    public Animation enableElementAndSetValue(int index, String value) {
        Timeline timeline = new Timeline();

        KeyFrame enableHighlightElement = new KeyFrame(Duration.millis(200), actionEvent -> {
            arrayDisplay.removeElementStyleClass(index, "array-element-disabled");
            arrayDisplay.addElementStyleClass(index, "array-element-highlighted");
        });

        KeyFrame setElementValue = new KeyFrame(Duration.millis(500), actionEvent -> {
            arrayDisplay.setElement(index, value);
        });

        KeyFrame disableHighlightElement = new KeyFrame(Duration.millis(1000), actionEvent -> {
            arrayDisplay.removeElementStyleClass(index, "array-element-highlighted");
        });

        timeline.getKeyFrames().add(enableHighlightElement);
        timeline.getKeyFrames().add(setElementValue);
        timeline.getKeyFrames().add(disableHighlightElement);

        return timeline;
    }
}
