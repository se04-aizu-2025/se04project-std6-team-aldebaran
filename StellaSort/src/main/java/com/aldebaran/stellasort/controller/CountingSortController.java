package com.aldebaran.stellasort.controller;

import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class CountingSortController {
    @FXML
    private Text testText;

    @FXML
    private void onTestButtonClicked() {
        testText.setText("Hello, you have pressed the button!");
    }
}
