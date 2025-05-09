package Controller;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class ClientMenuController {

    @FXML
    private StackPane centerContent; // Zone centrale dynamique

    public void initialize() {
        try {
            showHome(); // Afficher la vue "Home" par défaut
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void showHome() throws IOException {
        // Générer dynamiquement la vue Home
        Parent homeView = createHomeView();
        centerContent.getChildren().setAll(homeView);
    }

    @FXML
    private void showReservations() throws IOException {
        // Générer dynamiquement la vue Reservations
        Parent reservationsView = createReservationsView();
        centerContent.getChildren().setAll(reservationsView);
    }

    @FXML
    private void navigateToCalendar() {
        System.out.println("Navigating to Calendar...");
    }

    @FXML
    private void navigateToProfile() {
        System.out.println("Navigating to Profile...");
    }

    @FXML
    private void navigateToHelp() {
        System.out.println("Navigating to Help...");
    }

    @FXML
    private void handleLogout() {
        System.out.println("Logging out...");
    }

    public void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Méthode pour générer dynamiquement la vue Home
    private Parent createHomeView() {
        // Créer une Vue Home dynamiquement
        VBox homeView = new VBox(20);
        homeView.setPadding(new Insets(20));
        homeView.setStyle("-fx-background-color: white;");

        TextField searchField = new TextField();
        searchField.setPromptText("Rechercher un événement...");
        searchField.setPrefWidth(400);

        Label sectionTitle = new Label("All Events");
        sectionTitle.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #27ae60;");

        ScrollPane scrollPane = new ScrollPane();
        FlowPane cardsContainer = new FlowPane(50, 50);
        cardsContainer.setPrefWrapLength(1000);
        cardsContainer.setStyle("-fx-background-color: #ecf0f1; -fx-padding: 10px;");

        // Ajouter des cartes d'événements fictives
        for (int i = 0; i < 5; i++) {
            VBox card = new VBox(10);
            card.setPrefSize(250, 350);
            card.setStyle("-fx-background-color: white; -fx-border-radius: 10px; -fx-padding: 10px;");

            Label eventNameLabel = new Label("Événement " + (i + 1));
            Label dateLabel = new Label("Date : 2023-10-05");
            Label priceLabel = new Label("Prix : $50.00");

            Button reservationButton = new Button("Make Reservation");
            reservationButton.setStyle(
                    "-fx-background-color: #27ae60; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 8px 15px; -fx-cursor: hand;"
            );

            card.getChildren().addAll(eventNameLabel, dateLabel, priceLabel, reservationButton);
            cardsContainer.getChildren().add(card);
        }

        scrollPane.setContent(cardsContainer);

        homeView.getChildren().addAll(searchField, sectionTitle, scrollPane);
        return homeView;
    }

    // Méthode pour générer dynamiquement la vue Reservations
    private Parent createReservationsView() {
        // Créer une Vue Reservations dynamiquement
        VBox reservationsView = new VBox(20);
        reservationsView.setPadding(new Insets(20));
        reservationsView.setStyle("-fx-background-color: white;");

        Label sectionTitle = new Label("My Reservations");
        sectionTitle.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #27ae60;");

        ScrollPane scrollPane = new ScrollPane();
        FlowPane reservationsContainer = new FlowPane(50, 50);
        reservationsContainer.setPrefWrapLength(1000);
        reservationsContainer.setStyle("-fx-background-color: #ecf0f1; -fx-padding: 10px;");

        // Ajouter des cartes de réservations fictives
        for (int i = 0; i < 3; i++) {
            VBox card = new VBox(10);
            card.setPrefSize(250, 300);
            card.setStyle("-fx-background-color: white; -fx-border-radius: 10px; -fx-padding: 10px;");

            Label eventNameLabel = new Label("Événement : Événement " + (i + 1));
            Label reservationDateLabel = new Label("Date : 2023-10-05");
            Label totalPriceLabel = new Label("Prix total : $100.00");

            Button modifyButton = new Button("Modify");
            Button deleteButton = new Button("Delete");
            Button showButton = new Button("Show");

            HBox buttons = new HBox(10, modifyButton, deleteButton, showButton);
            buttons.setAlignment(javafx.geometry.Pos.CENTER);

            card.getChildren().addAll(eventNameLabel, reservationDateLabel, totalPriceLabel, buttons);
            reservationsContainer.getChildren().add(card);
        }

        scrollPane.setContent(reservationsContainer);

        reservationsView.getChildren().addAll(sectionTitle, scrollPane);
        return reservationsView;
    }
}