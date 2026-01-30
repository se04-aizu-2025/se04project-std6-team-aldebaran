package com.aldebaran.stellasort.animation;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class CountingSortProcessTextsAnimator {
    private final Text inputToCountProcessText;
    private final Text countToResultProcessText;

    public CountingSortProcessTextsAnimator(Text inputToCountProcessText, Text countToResultProcessText) {
        this.inputToCountProcessText = inputToCountProcessText;
        this.countToResultProcessText = countToResultProcessText;
    }

    public Animation hideTexts() {
        Timeline timeline = new Timeline();
        KeyFrame hideInputText = new KeyFrame(Duration.millis(200), actionEvent -> inputToCountProcessText.setVisible(false));
        timeline.getKeyFrames().add(hideInputText);
        return timeline;
    }

    public Animation countInput(int value) {
        Timeline timeline = new Timeline();
        KeyFrame countInput = new KeyFrame(Duration.millis(200), actionEvent -> {
            inputToCountProcessText.setVisible(true);
            inputToCountProcessText.setText("Count " + value + " from the input array, and increment count[" + value + "]");
        });
        timeline.getKeyFrames().add(countInput);
        return timeline;
    }

    public Animation writeResult(int inputIndex, int inputValue, int resultIndex, int resultIndexOffset) {
        String message = "Determine the index of the element " + inputValue + " from the count array: (" + (resultIndex - resultIndexOffset) + " + " + resultIndexOffset + ") where " + resultIndexOffset + " is the offset derived from the count array before this iteration.";

        Timeline timeline = new Timeline();
        KeyFrame writeResult = new KeyFrame(Duration.millis(200), actionEvent -> {
            countToResultProcessText.setVisible(true);
            countToResultProcessText.setText(message);
        });
        timeline.getKeyFrames().add(writeResult);
        return timeline;
    }
}
