package com.danclowry.noteapp.util;

import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

import java.io.PrintWriter;
import java.io.StringWriter;

public class AlertBuilder {
    public static Alert createAlert(String title, String content, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setHeaderText(null);
        alert.setTitle(title);
        alert.setContentText(content);
        return alert;
    }

    public static Alert createExceptionAlert(String title, String content, Exception ex) {
        Alert alert = createAlert(title, content, Alert.AlertType.ERROR);

        Label exLabel = new Label("Exception details");

        TextArea exDetails = new TextArea();
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        ex.printStackTrace(pw);
        exDetails.setText(sw.toString());
        exDetails.setEditable(false);
        exDetails.setWrapText(true);
        GridPane.setVgrow(exDetails, Priority.ALWAYS);
        GridPane.setHgrow(exDetails, Priority.ALWAYS);

        GridPane exGrid = new GridPane();
        exGrid.setMaxWidth(Double.MAX_VALUE);
        exGrid.setMaxHeight(Double.MAX_VALUE);
        exGrid.add(exLabel, 0, 0);
        exGrid.add(exDetails, 0, 1);

        alert.getDialogPane().setExpandableContent(exGrid);

        return alert;
    }
}
