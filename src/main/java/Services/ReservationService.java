package Services;

import entities.Reservation;
import Utils.MyDataBase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReservationService {
    private final Connection cnx;

    public ReservationService() throws SQLException {
        cnx = MyDataBase.getInstance().getCnx();
    }

    public void ajouter(Reservation r) throws SQLException {
        String sql = "INSERT INTO reservation (event_id, workshops, nb_workshops, reservation_date, number_of_tickets, total_price, payment_method, credit_card_number, payment_date) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pst = cnx.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pst.setInt(1, r.getEventId());
            pst.setString(2, r.getWorkshops());
            pst.setInt(3, r.getNbWorkshops());
            pst.setTimestamp(4, Timestamp.valueOf(r.getReservationDate()));
            pst.setInt(5, r.getNumberOfTickets());
            pst.setBigDecimal(6, r.getTotalPrice());
            pst.setString(7, r.getPaymentMethod());
            pst.setString(8, r.getCreditCardNumber()); // Numéro de carte bancaire
            pst.setTimestamp(9, r.getPaymentDate() != null ?
                    Timestamp.valueOf(r.getPaymentDate()) : null);

            pst.executeUpdate();

            try (ResultSet generatedKeys = pst.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    r.setReservationId(generatedKeys.getInt(1));
                }
            }
        }
    }

    public void modifier(Reservation r) throws SQLException {
        String sql = "UPDATE reservation SET event_id = ?, workshops = ?, nb_workshops = ?, reservation_date = ?, "
                + "number_of_tickets = ?, total_price = ?, payment_method = ?, credit_card_number = ?, payment_date = ? "
                + "WHERE reservation_id = ?";
        try (PreparedStatement pst = cnx.prepareStatement(sql)) {
            pst.setInt(1, r.getEventId());
            pst.setString(2, r.getWorkshops());
            pst.setInt(3, r.getNbWorkshops());
            pst.setTimestamp(4, Timestamp.valueOf(r.getReservationDate()));
            pst.setInt(5, r.getNumberOfTickets());
            pst.setBigDecimal(6, r.getTotalPrice());
            pst.setString(7, r.getPaymentMethod());
            pst.setString(8, r.getCreditCardNumber()); // Numéro de carte bancaire
            pst.setTimestamp(9, r.getPaymentDate() != null ?
                    Timestamp.valueOf(r.getPaymentDate()) : null);
            pst.setInt(10, r.getReservationId());

            pst.executeUpdate();
        }
    }

    public void supprimer(Reservation reservation) throws SQLException {
        String sql = "DELETE FROM reservation WHERE reservation_id = ?";
        try (PreparedStatement pst = cnx.prepareStatement(sql)) {
            pst.setInt(1, reservation.getReservationId());
            pst.executeUpdate();
        }
    }

    public List<Reservation> afficher() throws SQLException {
        List<Reservation> reservations = new ArrayList<>();
        String sql = "SELECT * FROM reservation";
        try (Statement st = cnx.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                Reservation r = new Reservation(
                        rs.getInt("reservation_id"),
                        rs.getInt("event_id"),
                        rs.getString("workshops"),
                        rs.getInt("nb_workshops"),
                        rs.getTimestamp("reservation_date").toLocalDateTime(),
                        rs.getInt("number_of_tickets"),
                        rs.getBigDecimal("total_price"),
                        rs.getString("payment_method"),
                        rs.getString("credit_card_number"), // Numéro de carte bancaire
                        rs.getTimestamp("payment_date") != null ?
                                rs.getTimestamp("payment_date").toLocalDateTime() : null
                );
                reservations.add(r);
            }
        }
        return reservations;
    }
}