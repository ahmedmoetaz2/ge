package entities;

import java.time.LocalDate;

public class Evenement {
    private Integer idEvent;
    private String nameEvent;
    private String categorieEvent;
    private String workshopsAvailable;
    private Integer numberOfWorkshop;
    private String description;
    private Float price;
    private String lieuName;
    private LocalDate dateD;
    private LocalDate dateF;
    private Integer lieuld; // Correspond à l'ID du lieu
    private Integer userid; // Correspond à l'ID de l'utilisateur
    private Integer atelierId; // Correspond à l'ID de l'atelier
    private String imagePath; // Chemin vers l'image de l'événement

    // Constructeur vide
    public Evenement() {}

    // Constructeur complet
    public Evenement(Integer idEvent, String nameEvent, String categorieEvent, String workshopsAvailable,
                     Integer numberOfWorkshop, String description, Float price, String lieuName,
                     LocalDate dateD, LocalDate dateF, Integer lieuld, Integer userid, Integer atelierId, String imagePath) {
        this.idEvent = idEvent;
        this.nameEvent = nameEvent;
        this.categorieEvent = categorieEvent;
        this.workshopsAvailable = workshopsAvailable;
        this.numberOfWorkshop = numberOfWorkshop;
        this.description = description;
        this.price = price;
        this.lieuName = lieuName;
        this.dateD = dateD;
        this.dateF = dateF;
        this.lieuld = lieuld;
        this.userid = userid;
        this.atelierId = atelierId;
        this.imagePath = imagePath;
    }

    // Constructeur pour nouvel événement (sans ID)
    public Evenement(String nameEvent, String categorieEvent, String workshopsAvailable,
                     Integer numberOfWorkshop, String description, Float price, String lieuName,
                     LocalDate dateD, LocalDate dateF, Integer lieuld, Integer userid, Integer atelierId, String imagePath) {
        this.nameEvent = nameEvent;
        this.categorieEvent = categorieEvent;
        this.workshopsAvailable = workshopsAvailable;
        this.numberOfWorkshop = numberOfWorkshop;
        this.description = description;
        this.price = price;
        this.lieuName = lieuName;
        this.dateD = dateD;
        this.dateF = dateF;
        this.lieuld = lieuld;
        this.userid = userid;
        this.atelierId = atelierId;
        this.imagePath = imagePath;
    }

    // Ajout d'une méthode pour récupérer la date de début comme "date de création"
    public LocalDate getDate() {
        return dateD; // Utilisez dateD comme référence temporelle
    }


    // Getters et Setters
    public Integer getIdEvent() { return idEvent; }
    public void setIdEvent(Integer idEvent) { this.idEvent = idEvent; }

    public String getNameEvent() { return nameEvent; }
    public void setNameEvent(String nameEvent) { this.nameEvent = nameEvent; }

    public String getCategorieEvent() { return categorieEvent; }
    public void setCategorieEvent(String categorieEvent) { this.categorieEvent = categorieEvent; }

    public String getWorkshopsAvailable() { return workshopsAvailable; }
    public void setWorkshopsAvailable(String workshopsAvailable) { this.workshopsAvailable = workshopsAvailable; }

    public Integer getNumberOfWorkshop() { return numberOfWorkshop; }
    public void setNumberOfWorkshop(Integer numberOfWorkshop) { this.numberOfWorkshop = numberOfWorkshop; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Float getPrice() { return price; }
    public void setPrice(Float price) { this.price = price; }

    public String getLieuName() { return lieuName; }
    public void setLieuName(String lieuName) { this.lieuName = lieuName; }

    public LocalDate getDateD() { return dateD; }
    public void setDateD(LocalDate dateD) { this.dateD = dateD; }

    public LocalDate getDateF() { return dateF; }
    public void setDateF(LocalDate dateF) { this.dateF = dateF; }

    public Integer getLieuld() { return lieuld; }
    public void setLieuld(Integer lieuld) { this.lieuld = lieuld; }

    public Integer getUserid() { return userid; }
    public void setUserid(Integer userid) { this.userid = userid; }

    public Integer getAtelierId() { return atelierId; }
    public void setAtelierId(Integer atelierId) { this.atelierId = atelierId; }

    public String getImagePath() { return imagePath; }
    public void setImagePath(String imagePath) { this.imagePath = imagePath; }

    @Override
    public String toString() {
        return "Evenement{" +
                "idEvent=" + idEvent +
                ", nameEvent='" + nameEvent + '\'' +
                ", categorieEvent='" + categorieEvent + '\'' +
                ", price=" + price +
                ", lieuName='" + lieuName + '\'' +
                ", dateD=" + dateD +
                ", dateF=" + dateF +
                '}';
    }
}