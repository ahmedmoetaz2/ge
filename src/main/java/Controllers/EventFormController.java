package Controllers;

import Models.Evenement;
import Services.EvenementService;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.time.LocalDate;

public class EventFormController {
    @FXML
    private TextField nameField;
    @FXML
    private TextField categoryField;
    @FXML
    private TextArea descriptionField;
    @FXML
    private TextField priceField;
    @FXML
    private TextField workshopsField;
    @FXML
    private TextField nbWorkshopsField;
    @FXML
    private DatePicker dateDField;
    @FXML
    private DatePicker dateFField;
    @FXML
    private TextField lieuIdField;
    @FXML
    private TextField userIdField;
    @FXML
    private TextField atelierIdField;

    private EvenementService evenementService = new EvenementService();
    private EventListController eventListController;
    private Evenement event;

    public void setEventListController(EventListController controller) {
        this.eventListController = controller;
    }

    public void setEvent(Evenement event) {
        this.event = event;
        if (event != null) {
            nameField.setText(event.getNameEvent());
            categoryField.setText(event.getCategorieEvent());
            descriptionField.setText(event.getDescription());
            priceField.setText(String.valueOf(event.getPrice()));
            workshopsField.setText(event.getWorkshopsAvailable());
            nbWorkshopsField.setText(String.valueOf(event.getNumberOfWorkshop()));
            dateDField.setValue(event.getDateD());
            dateFField.setValue(event.getDateF());
            lieuIdField.setText(String.valueOf(event.getLieuId()));
            userIdField.setText(String.valueOf(event.getUserId()));
            atelierIdField.setText(String.valueOf(event.getAtelierId()));
        }
    }

    @FXML
    private void handleSave() {
        try {
            String name = nameField.getText();
            String category = categoryField.getText();
            String description = descriptionField.getText();
            float price = Float.parseFloat(priceField.getText());
            String workshops = workshopsField.getText();
            int nbWorkshops = Integer.parseInt(nbWorkshopsField.getText());
            LocalDate dateD = dateDField.getValue();
            LocalDate dateF = dateFField.getValue();
            int lieuId = Integer.parseInt(lieuIdField.getText());
            int userId = Integer.parseInt(userIdField.getText());
            int atelierId = Integer.parseInt(atelierIdField.getText());

            if (event == null) {
                event = new Evenement(
                        0,
                        name,
                        category,
                        description,
                        price,
                        dateD,
                        dateF,
                        lieuId,
                        userId,
                        atelierId,
                        workshops,
                        nbWorkshops
                );
                evenementService.ajouter(event); // Appel de la méthode ajouter
            } else {
                event.setNameEvent(name);
                event.setCategorieEvent(category);
                event.setDescription(description);
                event.setPrice(price);
                event.setDateD(dateD);
                event.setDateF(dateF);
                event.setLieuId(lieuId);
                event.setUserId(userId);
                event.setAtelierId(atelierId);
                event.setWorkshopsAvailable(workshops);
                event.setNumberOfWorkshop(nbWorkshops);
                evenementService.modifier(event); // Appel de la méthode modifier
            }

            eventListController.refreshTable();
            closeWindow();
        } catch (NumberFormatException e) {
            showAlert("Erreur", "Veuillez entrer des valeurs numériques valides.");
        } catch (SQLException e) {
            showAlert("Erreur", "Erreur lors de la sauvegarde : " + e.getMessage());
        }
    }

    @FXML
    private void closeWindow() {
        Stage stage = (Stage) nameField.getScene().getWindow();
        stage.close();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}