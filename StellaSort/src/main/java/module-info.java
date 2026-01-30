module com.aldebaran.stellasort {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires javafx.graphics;
    requires annotations;
    requires java.desktop;

    opens com.aldebaran.stellasort to javafx.fxml;
    exports com.aldebaran.stellasort;
    exports com.aldebaran.stellasort.controller;
    opens com.aldebaran.stellasort.controller to javafx.fxml;
    exports com.aldebaran.stellasort.service;
    opens com.aldebaran.stellasort.service to javafx.fxml;
}