package Controller;

import entities.Reservation;
import Services.ReservationService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.List;

public class ReservationsViewController {

    @FXML
    private FlowPane reservationsContainer;

    private ClientMenuController parentController;

    private final ReservationService reservationService = new ReservationService();

    public ReservationsViewController() throws SQLException {
    }

    public void setParentController(ClientMenuController parentController) {
        this.parentController = parentController;
    }

    public void initialize() {
        try {
            loadReservations();
        } catch (SQLException e) {
            parentController.showAlert("Erreur", "Impossible de charger les réservations : " + e.getMessage());
        }
    }

    private void loadReservations() throws SQLException {
        List<Reservation> reservations = reservationService.afficher();
        reservationsContainer.getChildren().clear();

        for (Reservation reservation : reservations) {
            VBox card = createReservationCard(reservation);
            reservationsContainer.getChildren().add(card);
        }
    }

    private VBox createReservationCard(Reservation reservation) {
        VBox card = new VBox(10);
        card.setPrefSize(250, 300);
        card.setStyle("-fx-background-color: white; -fx-border-radius: 10px; -fx-padding: 10px;");

        Label eventNameLabel = new Label("Événement : " + reservation.getEventId());
        eventNameLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        Label reservationDateLabel = new Label("Date : " + reservation.getReservationDate().toString());
        reservationDateLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #555555;");

        Label totalPriceLabel = new Label("Prix total : $" + reservation.getTotalPrice());
        totalPriceLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #555555;");

        Button modifyButton = new Button("Modify");
        modifyButton.setOnAction(e -> openReservationFormForModification(reservation));

        Button deleteButton = new Button("Delete");
        deleteButton.setOnAction(e -> deleteReservation(reservation));

        Button showButton = new Button("Show");
        showButton.setOnAction(e -> openReservationFormForViewing(reservation));

        HBox buttons = new HBox(10, modifyButton, deleteButton, showButton);
        buttons.setAlignment(javafx.geometry.Pos.CENTER);

        card.getChildren().addAll(eventNameLabel, reservationDateLabel, totalPriceLabel, buttons);
        return card;
    }

    private void openReservationFormForModification(Reservation reservation) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/event_plannerrr/reservationform.fxml"));
            Parent root = loader.load();

            ReservationFormController controller = loader.getController();
            controller.initDataForModification(reservation);

            Stage stage = new Stage();
            stage.setTitle("Modifier la réservation");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            parentController.showAlert("Erreur", "Impossible d'ouvrir le formulaire de modification : " + e.getMessage());
        }
    }

    private void deleteReservation(Reservation reservation) {
        try {
            reservationService.supprimer(reservation);
            loadReservations(); // Recharger les réservations après suppression
        } catch (SQLException e) {
            parentController.showAlert("Erreur", "Impossible de supprimer la réservation : " + e.getMessage());
        }
    }

    private void openReservationFormForViewing(Reservation reservation) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/event_plannerrr/reservationform.fxml"));
            Parent root = loader.load();

            ReservationFormController controller = loader.getController();
            controller.initDataForViewing(reservation);

            Stage stage = new Stage();
            stage.setTitle("Détails de la réservation");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            parentController.showAlert("Erreur", "Impossible d'ouvrir le formulaire de visualisation : " + e.getMessage());
        }
    }
}