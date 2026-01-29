package com.aldebaran.stellasort.component;

import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import org.jetbrains.annotations.Nullable;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ArrayDisplay {
    @FunctionalInterface
    public interface ArrayElementCssRule {
        @Nullable String getStyleClass(int index);
    }
    public static ArrayElementCssRule NO_RULE = (i) -> null;
    private static final String DEFAULT_CSS_FILE = "/com/aldebaran/stellasort/array-display.css";

    private final FlowPane flowPane;
    private final List<Label> labels = new ArrayList<>();

    public ArrayDisplay(FlowPane flowPane) {
        this.flowPane = flowPane;
        flowPane.setHgap(2);
        flowPane.setVgap(2);

        try {
            URL arrayDisplayCss = getClass().getResource(DEFAULT_CSS_FILE);
            assert arrayDisplayCss != null;
            flowPane.getStylesheets().add(arrayDisplayCss.toExternalForm());
        } catch (NullPointerException e) {
            System.out.println("Array Display CSS does not exists.");
        }
    }

    //#region Whole ArrayDisplay

    public void clearArray() {
        labels.forEach(flowPane.getChildren()::remove);
        labels.clear();
    }

    public void setArray(List<Integer> array, ArrayElementCssRule arrayElementCssRule) {
        clearArray();

        for (int i = 0; i < array.size(); i++) {
            Label label = new Label(Integer.toString(array.get(i)));
            labels.add(label);
            flowPane.getChildren().add(label);

            label.getStyleClass().add("array-element");
            if (arrayElementCssRule.getStyleClass(i) != null) label.getStyleClass().add(arrayElementCssRule.getStyleClass(i));
        }
    }

    public void addCssRule(ArrayElementCssRule arrayElementCssRule) {
        for (int i = 0; i < labels.size(); i++) {
            // Set Style Class
            if (arrayElementCssRule.getStyleClass(i) == null) return;
            labels.get(i).getStyleClass().add(arrayElementCssRule.getStyleClass(i));
        }
    }

    //#endregion

    //#region Single Element

    public void setElement(int index, int value) {
        labels.get(index).setText(Integer.toString(value));
    }

    // TODO: probably should have some input validation
    public Label removeElement(int index) {
        Label label = labels.remove(index);
        flowPane.getChildren().remove(label);
        return label;
    }

    public void addElementStyleClass(int index, String styleClass) {
        labels.get(index).getStyleClass().add(styleClass);
    }

    public void removeElementStyleClass(int index, String styleClass) {
        labels.get(index).getStyleClass().remove(styleClass);
    }

    //#endregion
}
