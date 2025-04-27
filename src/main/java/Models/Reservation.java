package Models;

import java.time.LocalDateTime;
import java.math.BigDecimal;

public class Reservation {
    private int reservationId;
    private int eventId;
    private String workshops;
    private int nbWorkshops;
    private LocalDateTime reservationDate;
    private int numberOfTickets;
    private BigDecimal totalPrice;
    private String paymentMethod;
    private LocalDateTime paymentDate;

    public Reservation() {}

    // Full-parameter constructor
    public Reservation(int reservationId, int eventId, String workshops, int nbWorkshops,
                       LocalDateTime reservationDate, int numberOfTickets, BigDecimal totalPrice,
                       String paymentMethod, LocalDateTime paymentDate) {
        this.reservationId = reservationId;
        this.eventId = eventId;
        this.workshops = workshops;
        this.nbWorkshops = nbWorkshops;
        this.reservationDate = reservationDate;
        this.numberOfTickets = numberOfTickets;
        this.totalPrice = totalPrice;
        this.paymentMethod = paymentMethod;
        this.paymentDate = paymentDate;
    }

    // Constructor without reservationId (for new reservations)
    public Reservation(int eventId, String workshops, int nbWorkshops,
                       LocalDateTime reservationDate, int numberOfTickets, BigDecimal totalPrice,
                       String paymentMethod, LocalDateTime paymentDate) {
        this.eventId = eventId;
        this.workshops = workshops;
        this.nbWorkshops = nbWorkshops;
        this.reservationDate = reservationDate;
        this.numberOfTickets = numberOfTickets;
        this.totalPrice = totalPrice;
        this.paymentMethod = paymentMethod;
        this.paymentDate = paymentDate;
    }

    // Getters & Setters
    public int getReservationId() {
        return reservationId;
    }

    public void setReservationId(int reservationId) {
        this.reservationId = reservationId;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public String getWorkshops() {
        return workshops;
    }

    public void setWorkshops(String workshops) {
        this.workshops = workshops;
    }

    public int getNbWorkshops() {
        return nbWorkshops;
    }

    public void setNbWorkshops(int nbWorkshops) {
        this.nbWorkshops = nbWorkshops;
    }

    public LocalDateTime getReservationDate() {
        return reservationDate;
    }

    public void setReservationDate(LocalDateTime reservationDate) {
        this.reservationDate = reservationDate;
    }

    public int getNumberOfTickets() {
        return numberOfTickets;
    }

    public void setNumberOfTickets(int numberOfTickets) {
        this.numberOfTickets = numberOfTickets;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public LocalDateTime getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDateTime paymentDate) {
        this.paymentDate = paymentDate;
    }

    // toString() includes all fields in declaration order
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
                ", paymentDate=" + paymentDate +
                '}';
    }
}