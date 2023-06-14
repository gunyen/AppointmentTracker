package com.nguyen.c195;

import com.nguyen.c195.DAO.DBConnection;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class Scheduler extends Application {
    static Stage stage;
    static Parent scene;
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Scheduler.class.getResource("Login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setTitle("Appointment Tracker");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) throws Exception {
        launch(args);
    }

    public static void navigate(ActionEvent event, String fxml) throws IOException {
        DBConnection.openConnection();
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(Scheduler.class.getResource(fxml));
        stage.setScene(new Scene(scene));
        stage.show();
        DBConnection.closeConnection();
    }

}