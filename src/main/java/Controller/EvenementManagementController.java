package Controller;

import entities.Evenement;
import Services.EvenementService;
import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class EvenementManagementController {
    @FXML private VBox dashboardPane;
    @FXML private BorderPane eventManagementPane;
    @FXML private BorderPane userManagementPane;
    @FXML private BorderPane workshopManagementPane;
    @FXML private BorderPane stockManagementPane;
    @FXML private BorderPane locationManagementPane;
    @FXML private BorderPane subscriptionManagementPane;
    @FXML private StackPane contentPane;
    @FXML private FlowPane cardsContainer;
    @FXML private TextField searchField;
    @FXML private Label userNameLabel;
    @FXML private ImageView profileImage;
    @FXML private Label statusLabel;
    @FXML private Label eventCountLabel;
    @FXML private Label lastEventTimeLabel;

    private final IntegerProperty eventCount = new SimpleIntegerProperty(0);
    private final StringProperty lastEventTime = new SimpleStringProperty("");
    private final EvenementService evenementService = new EvenementService();
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    @FXML
    public void initialize() {
        // Setup admin profile
        userNameLabel.setText("Admin User");
        try {
            profileImage.setImage(new Image("file:///path/to/admin-profile.jpg"));
        } catch (Exception e) {
            System.err.println("Could not load admin profile image");
        }

        // Setup search field action
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                if (newValue.isEmpty()) {
                    loadAllEvents();
                } else {
                    List<Evenement> searchResults = evenementService.searchByName(newValue);
                    displayEvents(searchResults);
                }
            } catch (SQLException e) {
                showError("Search error: " + e.getMessage());
            }
        });

        // Default view is dashboard
        showDashboard();
        eventCountLabel.textProperty().bind(eventCount.asString());
        lastEventTimeLabel.textProperty().bind(lastEventTime);
        updateEventStats();
    }

    private void updateEventStats() {
        try {
            List<Evenement> events = evenementService.getAll();
            ObservableList<Evenement> observableEvents = FXCollections.observableArrayList(events);

            eventCount.set(observableEvents.size());

            if (!observableEvents.isEmpty()) {
                LocalDate lastDate = observableEvents.get(observableEvents.size() - 1).getDateD();
                lastEventTime.set(calculateTimeSince(lastDate));
            } else {
                lastEventTime.set("Aucun événement");
            }
        } catch (SQLException e) {
            showError("Erreur de base de données: " + e.getMessage());
        }
    }

    private String calculateTimeSince(LocalDate eventDate) {
        Period period = Period.between(eventDate, LocalDate.now());
        if (period.getDays() == 0) return "Ajouté aujourd'hui";
        if (period.getDays() == 1) return "Ajouté hier";
        if (period.getDays() <= 7) return "Ajouté il y a " + period.getDays() + " jours";
        return "Ajouté le " + eventDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    @FXML
    public void showDashboard() {
        dashboardPane.setVisible(true);
        eventManagementPane.setVisible(false);
        userManagementPane.setVisible(false);
        workshopManagementPane.setVisible(false);
        stockManagementPane.setVisible(false);
        locationManagementPane.setVisible(false);
        subscriptionManagementPane.setVisible(false);
    }


    @FXML
    public void showEventManagement() {
        dashboardPane.setVisible(false);
        eventManagementPane.setVisible(true);
        userManagementPane.setVisible(false);
        workshopManagementPane.setVisible(false);
        stockManagementPane.setVisible(false);
        locationManagementPane.setVisible(false);
        subscriptionManagementPane.setVisible(false);

        // Load events when switching to event management
        refreshEventCards();
    }

    @FXML
    public void refreshEventCards() {
        try {
            loadAllEvents();
            updateEventStats(); // Mise à jour des statistiques
        } catch (SQLException e) {
            showError("Error loading events: " + e.getMessage());
        }
    }

    private void loadAllEvents() throws SQLException {
        List<Evenement> events = evenementService.getAll();
        displayEvents(events);
    }

    private void displayEvents(List<Evenement> events) {
        cardsContainer.getChildren().clear();

        for (Evenement event : events) {
            VBox card = createEventCard(event);
            cardsContainer.getChildren().add(card);
        }
    }

    private VBox createEventCard(Evenement event) {
        VBox card = new VBox();
        card.setPrefWidth(300);
        card.setPrefHeight(340);
        card.setStyle("-fx-alignment: center; -fx-background-color: white; -fx-background-radius: 10; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 10, 0, 0, 3);");
        card.setPadding(new Insets(0, 0, 15, 0));
        card.setSpacing(8);

        // Event Image
        ImageView imageView = new ImageView();
        imageView.setFitWidth(300);
        imageView.setFitHeight(150);
        imageView.setPreserveRatio(false);

        try {
            imageView.setImage(new Image(event.getImagePath()));
        } catch (Exception e) {
            // Default image if the event image fails to load
            try {
                imageView.setImage(new Image("file:///path/to/default-event-image.jpg"));
            } catch (Exception ex) {
                System.err.println("Error loading default image: " + ex.getMessage());
            }
        }

        // Event Details
        Label nameLabel = new Label(event.getNameEvent());
        nameLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 16px; -fx-text-fill: #0c3b8c; -fx-padding: 0 15 0 15;");

        Label categoryLabel = new Label("Category: " + event.getCategorieEvent());
        categoryLabel.setStyle("-fx-font-size: 14px; -fx-padding: 0 15 0 15;");

        Label priceLabel = new Label("Price: " + event.getPrice() + " TND");
        priceLabel.setStyle("-fx-font-size: 14px; -fx-padding: 0 15 0 15;");

        Label dateLabel = new Label("Dates: " + event.getDateD().format(DATE_FORMATTER) + " - " + event.getDateF().format(DATE_FORMATTER));
        dateLabel.setStyle("-fx-font-size: 14px; -fx-padding: 0 15 0 15;");

        Label locationLabel = new Label("Place: " + event.getLieuName());
        locationLabel.setStyle("-fx-font-size: 14px; -fx-padding: 0 15 0 15;");

        // Details Button
        Button detailsButton = new Button("Details");
        detailsButton.setStyle("-fx-background-color: #0c3b8c; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 8 30 8 30; -fx-background-radius: 20;");

        HBox buttonContainer = new HBox();
        buttonContainer.setAlignment(javafx.geometry.Pos.CENTER); // Déjà présent
        buttonContainer.setMaxWidth(300); // Ajouté pour forcer la largeur
        buttonContainer.setPadding(new Insets(0, 15, 0, 15)); // Ajouté pour l'espacement
        HBox.setHgrow(buttonContainer, Priority.ALWAYS); // Ajouté pour l'expansion
        buttonContainer.getChildren().add(detailsButton);

        // Add all elements to the card
        card.getChildren().addAll(imageView, nameLabel, categoryLabel, priceLabel, dateLabel, locationLabel, buttonContainer);

        // Configure details button action
        detailsButton.setOnAction(e -> openEventDetails(event));

        return card;
    }

    private void openEventDetails(Evenement event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/event_plannerrr/event_details.fxml"));
            Parent root = loader.load();

            EventDetailsController controller = loader.getController();
            controller.setEvent(event);
            controller.setRefreshCallback(this::refreshEventCards);

            Stage stage = new Stage();
            stage.setTitle("Event Details");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        } catch (IOException e) {
            showError("Error opening event details: " + e.getMessage());
        }
    }

    @FXML
    public void openAddEventForm() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/event_plannerrr/event_form.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Add an event");
            stage.setScene(new Scene(root));

            EventFormController controller = loader.getController();
            controller.setMode("add");

            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();

            // Refresh event cards after adding a new event
            refreshEventCards();
        } catch (IOException e) {
            showError("Error opening event form: " + e.getMessage());
        }
    }

    @FXML
    public void handleLogout() {
        // Implement logout functionality
        Stage stage = (Stage) contentPane.getScene().getWindow();
        stage.close();

        // Optionally open login screen
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/login.fxml"));
            Parent root = loader.load();
            Stage loginStage = new Stage();
            loginStage.setTitle("Login");
            loginStage.setScene(new Scene(root));
            loginStage.show();
        } catch (IOException e) {
            System.err.println("Error opening login screen: " + e.getMessage());
        }
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}