package Controller;

import entities.Evenement;
import entities.Reservation;
import Services.EvenementService;
import Services.ReservationService;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class ReservationFormController {

    @FXML
    private TextField eventNameField;
    @FXML
    private TextField nbWorkshopsField;
    @FXML
    private ComboBox<String> workshopsComboBox;
    @FXML
    private DatePicker reservationDatePicker;
    @FXML
    private TextField numberOfTicketsField;
    @FXML
    private TextField totalPriceField;
    @FXML
    private ComboBox<String> paymentMethodComboBox;
    @FXML
    private HBox creditCardContainer;
    @FXML
    private TextField creditCardNumberField;

    private Evenement selectedEvent;
    private Reservation selectedReservation;
    private final EvenementService evenementService = new EvenementService();
    private final ReservationService reservationService = new ReservationService();

    public ReservationFormController() throws SQLException {
    }

    // Initialisation pour une nouvelle réservation
    public void initData(Evenement event) {
        this.selectedEvent = event;
        this.selectedReservation = null;

        // Remplir le nom de l'événement
        eventNameField.setText(event.getNameEvent());

        // Remplir la combobox des workshops à partir de l'événement
        List<String> workshops = Arrays.asList(event.getWorkshopsAvailable().split(","));
        workshopsComboBox.getItems().addAll(workshops);

        // Calculer le prix total dynamiquement
        numberOfTicketsField.textProperty().addListener((obs, oldVal, newVal) -> updateTotalPrice());

        // Configuration initiale
        paymentMethodComboBox.getItems().addAll("Cash", "Credit Card");
        paymentMethodComboBox.getSelectionModel().selectFirst();
        toggleCreditCardField();
    }

    // Initialisation pour modifier une réservation existante
    public void initDataForModification(Reservation reservation) {
        this.selectedReservation = reservation;
        this.selectedEvent = null;

        // Remplir les champs avec les données de la réservation
        eventNameField.setText(String.valueOf(reservation.getEventId()));
        nbWorkshopsField.setText(String.valueOf(reservation.getNbWorkshops()));
        workshopsComboBox.setValue(reservation.getWorkshops());
        reservationDatePicker.setValue(reservation.getReservationDate().toLocalDate());
        numberOfTicketsField.setText(String.valueOf(reservation.getNumberOfTickets()));
        totalPriceField.setText(String.format("$%.2f", reservation.getTotalPrice()));
        paymentMethodComboBox.setValue(reservation.getPaymentMethod());
        creditCardNumberField.setText(reservation.getCreditCardNumber());

        // Activer les champs pour modification
        creditCardNumberField.setEditable(true);
    }

    // Initialisation pour afficher une réservation (lecture seule)
    public void initDataForViewing(Reservation reservation) {
        this.selectedReservation = reservation;
        this.selectedEvent = null;

        // Remplir les champs et désactiver l'édition
        eventNameField.setText(String.valueOf(reservation.getEventId()));
        nbWorkshopsField.setText(String.valueOf(reservation.getNbWorkshops()));
        workshopsComboBox.setValue(reservation.getWorkshops());
        reservationDatePicker.setValue(reservation.getReservationDate().toLocalDate());
        numberOfTicketsField.setText(String.valueOf(reservation.getNumberOfTickets()));
        totalPriceField.setText(String.format("$%.2f", reservation.getTotalPrice()));
        paymentMethodComboBox.setValue(reservation.getPaymentMethod());
        creditCardNumberField.setText(reservation.getCreditCardNumber());

        // Désactiver tous les champs
        eventNameField.setEditable(false);
        nbWorkshopsField.setEditable(false);
        workshopsComboBox.setDisable(true);
        reservationDatePicker.setDisable(true);
        numberOfTicketsField.setEditable(false);
        totalPriceField.setEditable(false);
        paymentMethodComboBox.setDisable(true);
        creditCardNumberField.setEditable(false);
    }

    // Gestion dynamique du champ de carte bancaire
    @FXML
    private void initialize() {
        paymentMethodComboBox.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldVal, newVal) -> toggleCreditCardField()
        );
    }

    private void toggleCreditCardField() {
        boolean isVisible = "Credit Card".equals(paymentMethodComboBox.getValue());
        creditCardContainer.setVisible(isVisible);
        creditCardContainer.setManaged(isVisible);
    }

    // Calcul du prix total
    private void updateTotalPrice() {
        try {
            int numberOfTickets = Integer.parseInt(numberOfTicketsField.getText());
            float eventPrice = selectedEvent != null ? selectedEvent.getPrice() : 0;
            BigDecimal totalPrice = BigDecimal.valueOf(eventPrice * numberOfTickets);
            totalPriceField.setText(String.format("$%.2f", totalPrice));
        } catch (NumberFormatException e) {
            totalPriceField.setText("$0.00");
        }
    }

    // Gestion du bouton "Save"
    @FXML
    private void handleSave() {
        try {
            if (selectedReservation != null) {
                // Mode modification
                updateExistingReservation();
            } else {
                // Mode création
                createNewReservation();
            }
            Stage stage = (Stage) eventNameField.getScene().getWindow();
            stage.close();
        } catch (Exception e) {
            showAlert("Erreur", "Échec de l'opération : " + e.getMessage());
        }
    }

    private void createNewReservation() throws Exception {
        int eventId = selectedEvent.getIdEvent();
        String workshops = workshopsComboBox.getValue();
        int nbWorkshops = Integer.parseInt(nbWorkshopsField.getText());
        LocalDateTime reservationDate = reservationDatePicker.getValue().atStartOfDay();
        int numberOfTickets = Integer.parseInt(numberOfTicketsField.getText());
        BigDecimal totalPrice = new BigDecimal(totalPriceField.getText().replace("$", ""));
        String paymentMethod = paymentMethodComboBox.getValue();
        String creditCardNumber = "Credit Card".equals(paymentMethod) ? creditCardNumberField.getText() : null;
        LocalDateTime paymentDate = LocalDateTime.now();

        Reservation newReservation = new Reservation(
                eventId,
                workshops,
                nbWorkshops,
                reservationDate,
                numberOfTickets,
                totalPrice,
                paymentMethod,
                creditCardNumber,
                paymentDate
        );
        reservationService.ajouter(newReservation);
    }

    private void updateExistingReservation() throws SQLException {
        selectedReservation.setWorkshops(workshopsComboBox.getValue());
        selectedReservation.setNbWorkshops(Integer.parseInt(nbWorkshopsField.getText()));
        selectedReservation.setReservationDate(reservationDatePicker.getValue().atStartOfDay());
        selectedReservation.setNumberOfTickets(Integer.parseInt(numberOfTicketsField.getText()));
        selectedReservation.setTotalPrice(new BigDecimal(totalPriceField.getText().replace("$", "")));
        selectedReservation.setPaymentMethod(paymentMethodComboBox.getValue());
        selectedReservation.setCreditCardNumber("Credit Card".equals(paymentMethodComboBox.getValue())
                ? creditCardNumberField.getText()
                : null);

        reservationService.modifier(selectedReservation);
    }

    // Gestion du bouton "Cancel"
    @FXML
    private void handleCancel() {
        Stage stage = (Stage) eventNameField.getScene().getWindow();
        stage.close();
    }

    // Affichage des alertes
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}