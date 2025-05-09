package Controller;

import Services.EvenementService;
import entities.Evenement;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

public class EventDetailsController {
    @FXML private ImageView eventImageView;
    @FXML private Label eventNameLabel;
    @FXML private Label eventCategoryLabel;
    @FXML private Label eventPriceLabel;
    @FXML private Label eventDateRangeLabel;
    @FXML private Label eventLocationLabel;
    @FXML private Label eventDescriptionLabel;
    @FXML private Label workshopsAvailableLabel;
    @FXML private Label numberOfWorkshopsLabel;

    private Evenement event;
    private final EvenementService evenementService = new EvenementService();
    private Runnable refreshCallback;

    public void setEvent(Evenement event) {
        this.event = event;
        updateUI();
    }

    public void setRefreshCallback(Runnable refreshCallback) {
        this.refreshCallback = refreshCallback;
    }

    private void updateUI() {
        if (event == null) return;

        try {
            eventImageView.setImage(new Image(event.getImagePath()));
        } catch (Exception e) {
            System.err.println("Error loading image: " + e.getMessage());
            // Load a default image instead
            try {
                eventImageView.setImage(new Image("file:///path/to/default-image.jpg"));
            } catch (Exception ex) {
                // If even the default image fails, just leave it empty
            }
        }

        eventNameLabel.setText(event.getNameEvent());
        eventCategoryLabel.setText("Category: " + event.getCategorieEvent());
        eventPriceLabel.setText("Price: " + event.getPrice() + " TND");
        eventDateRangeLabel.setText("Dates: Du " + event.getDateD() + " au " + event.getDateF());
        eventLocationLabel.setText("Place: " + event.getLieuName());

        // Additional details
        if (eventDescriptionLabel != null) {
            eventDescriptionLabel.setText("Description: " + event.getDescription());
        }

        if (numberOfWorkshopsLabel != null) {
            numberOfWorkshopsLabel.setText("Number of workshops: " + event.getNumberOfWorkshop());
        }

        if (workshopsAvailableLabel != null) {
            workshopsAvailableLabel.setText("Workshops available: " + event.getWorkshopsAvailable());
        }
    }

    @FXML
    public void modifyEvent() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/event_plannerrr/event_form.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Edit the event");
            stage.setScene(new Scene(root));

            EventFormController controller = loader.getController();
            controller.setEvent(event);

            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();

            // After closing the edit form, refresh the event details
            try {
                Evenement updatedEvent = evenementService.findById(event.getIdEvent());
                if (updatedEvent != null) {
                    this.event = updatedEvent;
                    updateUI();

                    // Refresh the event list if a callback is provided
                    if (refreshCallback != null) {
                        refreshCallback.run();
                    }
                }
            } catch (SQLException e) {
                showError("Error refreshing event details: " + e.getMessage());
            }

        } catch (IOException e) {
            showError("Error opening edit form: " + e.getMessage());
        }
    }

    @FXML
    public void deleteEvent() {
        Alert confirmDialog = new Alert(Alert.AlertType.CONFIRMATION);
        confirmDialog.setTitle("Confirm Deletion");
        confirmDialog.setHeaderText("Delete Event");
        confirmDialog.setContentText("Are you sure you want to delete this event? This action cannot be undone.");

        Optional<ButtonType> result = confirmDialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                evenementService.delete(event.getIdEvent());

                // Close the details window
                ((Stage) eventImageView.getScene().getWindow()).close();

                // Refresh the event list if a callback is provided
                if (refreshCallback != null) {
                    refreshCallback.run();
                }

            } catch (SQLException e) {
                showError("Error deleting event: " + e.getMessage());
            }
        }
    }

    @FXML
    public void closeWindow() {
        ((Stage) eventImageView.getScene().getWindow()).close();
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}