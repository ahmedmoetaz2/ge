package org.example.event_plannerrr;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApplication extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Charger le fichier FXML principal (event_List.fxml)
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/event_plannerrr/event_List.fxml"));
        Parent root = loader.load();

        // Créer une scène avec la racine chargée depuis le FXML
        Scene scene = new Scene(root, 800, 600);

        // Configurer la fenêtre principale
        primaryStage.setTitle("Gestion des événements");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}