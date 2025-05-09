package org.example.event_plannerrr;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApplication extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            // Load the main admin dashboard
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/event_plannerrr/EvenementManagement.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);
            primaryStage.setTitle("LifeLine - Admin Dashboard");
            primaryStage.setScene(scene);
            primaryStage.setMaximized(true);
            primaryStage.show();

        } catch (IOException e) {
            e.printStackTrace();
            showErrorAlert(e);
        }
    }

    private void showErrorAlert(Exception e) {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.ERROR);
        alert.setTitle("Application Error");
        alert.setHeaderText("Error Starting Application");
        alert.setContentText("An error occurred while starting the application:\n" + e.getMessage());
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}