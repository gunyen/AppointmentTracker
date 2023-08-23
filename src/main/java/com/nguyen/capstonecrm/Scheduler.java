package com.nguyen.capstonecrm;

import com.nguyen.capstonecrm.DAO.DBConnection;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

/**
 * Application launch
 */
public class Scheduler extends Application {
    static Stage stage;
    static Parent scene;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Scheduler.class.getResource("Login.fxml"));//Login.fxml
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        //Application CSS file
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("styles.css")).toExternalForm());
        /*
        Application Icon
        stage.getIcons().add(new Image(Objects.requireNonNull(Scheduler.class.getResourceAsStream(""))));
        */
        stage.setTitle("CapstoneCRM");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) throws Exception {
        launch(args);
    }

    /**
     * <p>method for navigating through the application</p>
     *
     * @param event
     * @param fxml
     * @throws IOException
     */
    public static void navigate(ActionEvent event, String fxml) throws IOException {
        DBConnection.openConnection();
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(Objects.requireNonNull(Scheduler.class.getResource(fxml)));
        //Application CSS file
        scene.getStylesheets().add(Objects.requireNonNull(Scheduler.class.getResource("styles.css")).toExternalForm());
        /*
        Application Icon
        stage.getIcons().add(new Image(Objects.requireNonNull(Scheduler.class.getResourceAsStream(""))));
        */

        stage.setScene(new Scene(scene));
        stage.show();
        DBConnection.closeConnection();
    }

}