package com.aldebaran.stellasort.component;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputArray {
    @FunctionalInterface
    public interface InputValidationRule {
        @Nullable String validate(int input);
    }

    private final TextArea textArea;
    private final Label errorMessageLabel;
    private final Button inputButton;

    private final InputValidationRule validationRule;

    public InputArray(TextArea textArea, Label errorMessageLabel, Button inputButton, InputValidationRule validationRule) {
        this.textArea = textArea;
        this.errorMessageLabel = errorMessageLabel;
        this.inputButton = inputButton;
        this.validationRule = validationRule;

        // Set up the initial state
        errorMessageLabel.setVisible(false);

        // Set up a focus listener to validate input on focus lost
        textArea.focusedProperty().addListener((observable, oldValue, newValue) -> validateTextAreaInput());
    }

    public List<Integer> readInputArray(String input) {
        List<Integer> inputArray = new java.util.ArrayList<>(List.of());

        String regex = "\\d+";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);

        for (int i = 0; matcher.find(); i++) {
            inputArray.add(Integer.parseInt(matcher.group()));
        }
        return inputArray;
    }

    // Return null if valid, otherwise return an error message
    @Nullable public String validate(List<Integer> inputArray) {
        // Validate input format
        String input = textArea.getText().trim();
        String regex = "^\\d+(\\s*,\\s*\\d+)*$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);

        if (!matcher.matches()) return "Input must be a comma-separated list of positive integers.";

        // Parse input into an array
        inputArray = readInputArray(input);

        // Validate input values
        for (int i : inputArray) {
            String errorMessage = validationRule.validate(i);
            if (errorMessage != null) return errorMessage;
        }

        return null;
    }

    // Validate the input text area and update the button state accordingly
    // Returns true if the input is valid, false otherwise
    public boolean validateTextAreaInput() {
        String errorMessage = validate(new java.util.ArrayList<>(List.of()));
        if (errorMessage != null) {
            inputButton.setDisable(true);
            errorMessageLabel.setVisible(true);
            errorMessageLabel.setText(errorMessage);
            return false;
        }

        inputButton.setDisable(false);
        errorMessageLabel.setVisible(false);
        errorMessageLabel.setText("");
        return true;
    }

    public void onInputButtonClick(List<Integer> inputArray) {
        inputArray.clear();
        boolean isValid = validateTextAreaInput();
        if (!isValid) return;
        inputArray.addAll(readInputArray(textArea.getText()));
    }
}
