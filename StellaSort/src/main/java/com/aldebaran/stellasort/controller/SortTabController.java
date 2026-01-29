package com.aldebaran.stellasort.controller;
import com.aldebaran.stellasort.service.SortAlgorithm;
import javafx.fxml.FXML;


public class SortTabController {

    private SortAlgorithm algorithm;

    public void setAlgorithm(SortAlgorithm algorithm) {
        this.algorithm = algorithm;
    }

    @FXML
    private void onSort() {
        switch (algorithm) {
            case BUBBLE -> {}
            case COUNTING -> {}
            case HEAP -> {}
            case QUICK -> {}
        }
    }
}