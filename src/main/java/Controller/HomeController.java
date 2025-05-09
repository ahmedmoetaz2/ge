package Controller;

import entities.Evenement;
import Services.EvenementService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class HomeController implements Initializable {

    @FXML
    private FlowPane cardsContainer;

    @FXML
    private TextField searchField; // Champ de recherche

    private ClientMenuController parentController;

    private final EvenementService evenementService = new EvenementService();

    public void setParentController(ClientMenuController parentController) {
        this.parentController = parentController;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            refreshCards();
        } catch (SQLException e) {
            parentController.showAlert("Erreur", "Impossible de charger les événements : " + e.getMessage());
        }

        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                filterEventsByName(newValue);
            } catch (SQLException e) {
                parentController.showAlert("Erreur", "Impossible de filtrer les événements : " + e.getMessage());
            }
        });
    }

    public void refreshCards() throws SQLException {
        List<Evenement> events = evenementService.getAll();
        displayCards(events);
    }

    private void filterEventsByName(String searchText) throws SQLException {
        List<Evenement> allEvents = evenementService.getAll();
        List<Evenement> filteredEvents = allEvents.stream()
                .filter(event -> event.getNameEvent().toLowerCase().contains(searchText.toLowerCase()))
                .toList();
        displayCards(filteredEvents);
    }

    private void displayCards(List<Evenement> events) {
        cardsContainer.getChildren().clear();

        for (Evenement event : events) {
            VBox card = createEventCard(event);
            cardsContainer.getChildren().add(card);
        }
    }

    private VBox createEventCard(Evenement event) {
        VBox card = new VBox(10);
        card.setPrefSize(250, 350);
        card.setStyle("-fx-background-color: white; -fx-border-radius: 10px; -fx-padding: 10px;");

        ImageView imageView = new ImageView(new Image(new File(event.getImagePath()).toURI().toString()));
        imageView.setFitWidth(230);
        imageView.setPreserveRatio(true);

        Label nameLabel = new Label(event.getNameEvent());
        nameLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        Label dateLabel = new Label("Date: " + event.getDateD().toString());
        dateLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #555555;");

        Label priceLabel = new Label("Price: $" + event.getPrice());
        priceLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #555555;");

        Button reservationButton = new Button("Make Reservation");
        reservationButton.setStyle(
                "-fx-background-color: #27ae60; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 8px 15px; -fx-cursor: hand;"
        );
        reservationButton.setOnAction(e -> openReservationForm(event));

        card.getChildren().addAll(imageView, nameLabel, dateLabel, priceLabel, reservationButton);
        return card;
    }

    private void openReservationForm(Evenement event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/event_plannerrr/reservationform.fxml"));
            Parent root = loader.load();

            ReservationFormController controller = loader.getController();
            controller.initData(event); // Passer l'événement au formulaire

            Stage stage = new Stage();
            stage.setTitle("Réserver pour l'événement : " + event.getNameEvent());
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception ex) {
            parentController.showAlert("Erreur", "Impossible d'ouvrir le formulaire de réservation : " + ex.getMessage());
        }
    }
}