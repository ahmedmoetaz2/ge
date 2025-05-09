package entities;

import java.time.LocalDateTime;
import java.math.BigDecimal;

public class Reservation {
    private int reservationId; // ID unique de la réservation
    private int eventId; // ID de l'événement associé
    private String workshops; // Workshops sélectionnés (sous forme de chaîne séparée par des virgules)
    private int nbWorkshops; // Nombre de workshops
    private LocalDateTime reservationDate; // Date de réservation
    private int numberOfTickets; // Nombre de billets
    private BigDecimal totalPrice; // Prix total
    private String paymentMethod; // Mode de paiement ("Cash" ou "Credit Card")
    private String creditCardNumber; // Numéro de carte bancaire (optionnel)
    private LocalDateTime paymentDate; // Date de paiement

    // Constructeur vide
    public Reservation() {}

    // Constructeur complet
    public Reservation(int reservationId, int eventId, String workshops, int nbWorkshops,
                       LocalDateTime reservationDate, int numberOfTickets, BigDecimal totalPrice,
                       String paymentMethod, String creditCardNumber, LocalDateTime paymentDate) {
        this.reservationId = reservationId;
        this.eventId = eventId;
        this.workshops = workshops;
        this.nbWorkshops = nbWorkshops;
        this.reservationDate = reservationDate;
        this.numberOfTickets = numberOfTickets;
        this.totalPrice = totalPrice;
        this.paymentMethod = paymentMethod;
        this.creditCardNumber = creditCardNumber;
        this.paymentDate = paymentDate;
    }

    // Constructeur sans reservationId (pour les nouvelles réservations)
    public Reservation(int eventId, String workshops, int nbWorkshops,
                       LocalDateTime reservationDate, int numberOfTickets, BigDecimal totalPrice,
                       String paymentMethod, String creditCardNumber, LocalDateTime paymentDate) {
        this.eventId = eventId;
        this.workshops = workshops;
        this.nbWorkshops = nbWorkshops;
        this.reservationDate = reservationDate;
        this.numberOfTickets = numberOfTickets;
        this.totalPrice = totalPrice;
        this.paymentMethod = paymentMethod;
        this.creditCardNumber = creditCardNumber;
        this.paymentDate = paymentDate;
    }

    // Getters & Setters
    public int getReservationId() { return reservationId; }
    public void setReservationId(int reservationId) { this.reservationId = reservationId; }

    public int getEventId() { return eventId; }
    public void setEventId(int eventId) { this.eventId = eventId; }

    public String getWorkshops() { return workshops; }
    public void setWorkshops(String workshops) { this.workshops = workshops; }

    public int getNbWorkshops() { return nbWorkshops; }
    public void setNbWorkshops(int nbWorkshops) { this.nbWorkshops = nbWorkshops; }

    public LocalDateTime getReservationDate() { return reservationDate; }
    public void setReservationDate(LocalDateTime reservationDate) { this.reservationDate = reservationDate; }

    public int getNumberOfTickets() { return numberOfTickets; }
    public void setNumberOfTickets(int numberOfTickets) { this.numberOfTickets = numberOfTickets; }

    public BigDecimal getTotalPrice() { return totalPrice; }
    public void setTotalPrice(BigDecimal totalPrice) { this.totalPrice = totalPrice; }

    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }

    public String getCreditCardNumber() { return creditCardNumber; }
    public void setCreditCardNumber(String creditCardNumber) { this.creditCardNumber = creditCardNumber; }

    public LocalDateTime getPaymentDate() { return paymentDate; }
    public void setPaymentDate(LocalDateTime paymentDate) { this.paymentDate = paymentDate; }

    // Méthode toString pour afficher les informations de la réservation
    @Override
    public String toString() {
        return "Reservation{" +
                "reservationId=" + reservationId +
                ", eventId=" + eventId +
                ", workshops='" + workshops + '\'' +
                ", nbWorkshops=" + nbWorkshops +
                ", reservationDate=" + reservationDate +
                ", numberOfTickets=" + numberOfTickets +
                ", totalPrice=" + totalPrice +
                ", paymentMethod='" + paymentMethod + '\'' +
                ", creditCardNumber='" + creditCardNumber + '\'' +
                ", paymentDate=" + paymentDate +
                '}';
    }
}