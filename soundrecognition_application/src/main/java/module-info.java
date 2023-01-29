module it.unipi.dii.dmml.soundrecognition.soundrecognition_application {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires weka;
    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires java.sql;
    requires java.desktop;

    opens it.unipi.dii.dmml.soundrecognition.soundrecognition_application to javafx.fxml;
    exports it.unipi.dii.dmml.soundrecognition.soundrecognition_application;

    exports it.unipi.dii.dmml.soundrecognition.soundrecognition_application.controller;
    opens it.unipi.dii.dmml.soundrecognition.soundrecognition_application.controller to javafx.fxml;

    exports it.unipi.dii.dmml.soundrecognition.soundrecognition_application.entities;
    opens it.unipi.dii.dmml.soundrecognition.soundrecognition_application.entities to javafx.fxml;
}