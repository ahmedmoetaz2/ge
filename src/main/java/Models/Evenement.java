package Models;

import javafx.beans.property.*;

import java.time.LocalDate;

public class Evenement {
    private final IntegerProperty idEvent = new SimpleIntegerProperty();
    private final StringProperty nameEvent = new SimpleStringProperty();
    private final StringProperty categorieEvent = new SimpleStringProperty();
    private final StringProperty description = new SimpleStringProperty();
    private final FloatProperty price = new SimpleFloatProperty();
    private final ObjectProperty<LocalDate> dateD = new SimpleObjectProperty<>();
    private final ObjectProperty<LocalDate> dateF = new SimpleObjectProperty<>();
    private final IntegerProperty lieuId = new SimpleIntegerProperty();
    private final IntegerProperty userId = new SimpleIntegerProperty();
    private final IntegerProperty atelierId = new SimpleIntegerProperty();
    private final StringProperty workshopsAvailable = new SimpleStringProperty();
    private final IntegerProperty numberOfWorkshop = new SimpleIntegerProperty();

    // Constructeur avec tous les paramètres
    public Evenement(int idEvent, String nameEvent, String categorieEvent, String description, float price,
                     LocalDate dateD, LocalDate dateF, int lieuId, int userId, int atelierId,
                     String workshopsAvailable, int numberOfWorkshop) {
        this.idEvent.set(idEvent);
        this.nameEvent.set(nameEvent);
        this.categorieEvent.set(categorieEvent);
        this.description.set(description);
        this.price.set(price);
        this.dateD.set(dateD);
        this.dateF.set(dateF);
        this.lieuId.set(lieuId);
        this.userId.set(userId);
        this.atelierId.set(atelierId);
        this.workshopsAvailable.set(workshopsAvailable);
        this.numberOfWorkshop.set(numberOfWorkshop);
    }

    // Getters et setters pour JavaFX properties
    public int getIdEvent() {
        return idEvent.get();
    }

    public IntegerProperty idEventProperty() {
        return idEvent;
    }

    public void setIdEvent(int idEvent) {
        this.idEvent.set(idEvent);
    }

    public String getNameEvent() {
        return nameEvent.get();
    }

    public StringProperty nameEventProperty() {
        return nameEvent;
    }

    public void setNameEvent(String nameEvent) {
        this.nameEvent.set(nameEvent);
    }

    public String getCategorieEvent() {
        return categorieEvent.get();
    }

    public StringProperty categorieEventProperty() {
        return categorieEvent;
    }

    public void setCategorieEvent(String categorieEvent) {
        this.categorieEvent.set(categorieEvent);
    }

    public String getDescription() {
        return description.get();
    }

    public StringProperty descriptionProperty() {
        return description;
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

    public float getPrice() {
        return price.get();
    }

    public FloatProperty priceProperty() {
        return price;
    }

    public void setPrice(float price) {
        this.price.set(price);
    }

    public LocalDate getDateD() {
        return dateD.get();
    }

    public ObjectProperty<LocalDate> dateDProperty() {
        return dateD;
    }

    public void setDateD(LocalDate dateD) {
        this.dateD.set(dateD);
    }

    public LocalDate getDateF() {
        return dateF.get();
    }

    public ObjectProperty<LocalDate> dateFProperty() {
        return dateF;
    }

    public void setDateF(LocalDate dateF) {
        this.dateF.set(dateF);
    }

    public int getLieuId() {
        return lieuId.get();
    }

    public IntegerProperty lieuIdProperty() {
        return lieuId;
    }

    public void setLieuId(int lieuId) {
        this.lieuId.set(lieuId);
    }

    public int getUserId() {
        return userId.get();
    }

    public IntegerProperty userIdProperty() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId.set(userId);
    }

    public int getAtelierId() {
        return atelierId.get();
    }

    public IntegerProperty atelierIdProperty() {
        return atelierId;
    }

    public void setAtelierId(int atelierId) {
        this.atelierId.set(atelierId);
    }

    public String getWorkshopsAvailable() {
        return workshopsAvailable.get();
    }

    public StringProperty workshopsAvailableProperty() {
        return workshopsAvailable;
    }

    public void setWorkshopsAvailable(String workshopsAvailable) {
        this.workshopsAvailable.set(workshopsAvailable);
    }

    public int getNumberOfWorkshop() {
        return numberOfWorkshop.get();
    }

    public IntegerProperty numberOfWorkshopProperty() {
        return numberOfWorkshop;
    }

    public void setNumberOfWorkshop(int numberOfWorkshop) {
        this.numberOfWorkshop.set(numberOfWorkshop);
    }
}