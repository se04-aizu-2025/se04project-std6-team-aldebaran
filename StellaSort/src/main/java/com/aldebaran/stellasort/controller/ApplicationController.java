package com.aldebaran.stellasort.controller;
import com.aldebaran.stellasort.service.SortAlgorithm;
import javafx.fxml.FXML;

public class ApplicationController {

    @FXML private SortTabController bubbleTabController;
//    @FXML private SortTabController countingTabController;
//    @FXML private SortTabController heapTabController;
    @FXML private SortTabController quickTabController;

    @FXML
    public void initialize() {
        bubbleTabController.setAlgorithm(SortAlgorithm.BUBBLE);
        quickTabController.setAlgorithm(SortAlgorithm.QUICK);
    }
}