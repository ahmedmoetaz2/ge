package Controllers;

import Models.Evenement;
import Services.EvenementService;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;

public class EventListController {
    @FXML
    private TableView<Evenement> eventTable;
    @FXML
    private TableColumn<Evenement, Integer> idColumn;
    @FXML
    private TableColumn<Evenement, String> nameColumn;
    @FXML
    private TableColumn<Evenement, String> categoryColumn;
    @FXML
    private TableColumn<Evenement, LocalDate> dateDColumn;
    @FXML
    private TableColumn<Evenement, LocalDate> dateFColumn;
    @FXML
    private TableColumn<Evenement, Float> priceColumn;
    @FXML
    private TableColumn<Evenement, String> workshopsColumn;
    @FXML
    private TableColumn<Evenement, Integer> nbWorkshopsColumn;

    private final EvenementService evenementService = new EvenementService();

    public EventListController() throws SQLException {
    }

    public void initialize() {
        idColumn.setCellValueFactory(cellData -> cellData.getValue().idEventProperty().asObject());
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameEventProperty());
        categoryColumn.setCellValueFactory(cellData -> cellData.getValue().categorieEventProperty());
        dateDColumn.setCellValueFactory(cellData -> cellData.getValue().dateDProperty());
        dateFColumn.setCellValueFactory(cellData -> cellData.getValue().dateFProperty());
        priceColumn.setCellValueFactory(cellData -> cellData.getValue().priceProperty().asObject());
        workshopsColumn.setCellValueFactory(cellData -> cellData.getValue().workshopsAvailableProperty());
        nbWorkshopsColumn.setCellValueFactory(cellData -> cellData.getValue().numberOfWorkshopProperty().asObject());

        refreshTable();
    }

    public void refreshTable() {
        try {
            eventTable.setItems(FXCollections.observableArrayList(evenementService.afficher()));
        } catch (SQLException e) {
            showAlert("Erreur", "Impossible de charger les événements : " + e.getMessage());
        }
    }

    @FXML
    private void handleAddEvent() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/event_plannerrr/event_Form.fxml"));
            Parent root = loader.load();
            EventFormController controller = loader.getController();
            controller.setEventListController(this);
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Ajouter un événement");
            stage.show();
        } catch (IOException e) {
            showAlert("Erreur", "Impossible de charger le formulaire : " + e.getMessage());
        }
    }

    @FXML
    private void handleEditEvent() {
        Evenement selectedEvent = eventTable.getSelectionModel().getSelectedItem();
        if (selectedEvent == null) {
            showAlert("Aucune sélection", "Veuillez sélectionner un événement.");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/event_plannerrr/event_Form.fxml"));
            Parent root = loader.load();
            EventFormController controller = loader.getController();
            controller.setEvent(selectedEvent);
            controller.setEventListController(this);
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Modifier un événement");
            stage.show();
        } catch (IOException e) {
            showAlert("Erreur", "Impossible de charger le formulaire : " + e.getMessage());
        }
    }

    @FXML
    private void handleDeleteEvent() {
        Evenement selectedEvent = eventTable.getSelectionModel().getSelectedItem();
        if (selectedEvent == null) {
            showAlert("Aucune sélection", "Veuillez sélectionner un événement.");
            return;
        }

        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
        confirmation.setTitle("Confirmation");
        confirmation.setHeaderText("Suppression d'un événement");
        confirmation.setContentText("Êtes-vous sûr de vouloir supprimer cet événement ?");
        confirmation.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                try {
                    evenementService.supprimer(selectedEvent);
                    refreshTable();
                } catch (SQLException e) {
                    showAlert("Erreur", "Impossible de supprimer l'événement : " + e.getMessage());
                }
            }
        });
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}