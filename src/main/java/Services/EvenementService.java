package Services;

import Utils.MyDataBase;
import entities.Evenement;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EvenementService {

    private Connection connection;

    public EvenementService() {
        // Use the singleton database connection
        this.connection = MyDataBase.getInstance().getCnx();
        if (this.connection == null) {
            throw new RuntimeException("Failed to initialize database connection!");
        }
    }

    // Récupérer tous les événements
    public List<Evenement> getAll() throws SQLException {
        List<Evenement> events = new ArrayList<>();
        String query = "SELECT * FROM evenements";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                events.add(new Evenement(
                        rs.getInt("idEvent"),
                        rs.getString("nameEvent"),
                        rs.getString("categorieEvent"),
                        rs.getString("workshopsAvailable"),
                        rs.getInt("numberOfWorkshop"),
                        rs.getString("description"),
                        rs.getFloat("price"),
                        rs.getString("lieuName"),
                        rs.getDate("dateD").toLocalDate(),
                        rs.getDate("dateF").toLocalDate(),
                        rs.getInt("lieuld"),
                        rs.getInt("userid"),
                        rs.getInt("atelierId"),
                        rs.getString("imagePath")
                ));
            }
        }
        return events;
    }

    // Ajouter un événement
    public void add(Evenement event) throws SQLException {
        String query = "INSERT INTO evenements (nameEvent, categorieEvent, workshopsAvailable, numberOfWorkshop, description, price, lieuName, dateD, dateF, lieuld, userid, atelierId, imagePath) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, event.getNameEvent());
            stmt.setString(2, event.getCategorieEvent());
            stmt.setString(3, event.getWorkshopsAvailable());
            stmt.setInt(4, event.getNumberOfWorkshop());
            stmt.setString(5, event.getDescription());
            stmt.setFloat(6, event.getPrice());
            stmt.setString(7, event.getLieuName());
            stmt.setDate(8, Date.valueOf(event.getDateD()));
            stmt.setDate(9, Date.valueOf(event.getDateF()));
            stmt.setInt(10, event.getLieuld());
            stmt.setInt(11, event.getUserid());
            stmt.setInt(12, event.getAtelierId());
            stmt.setString(13, event.getImagePath());
            stmt.executeUpdate();
        }
    }

    // Mettre à jour un événement
    public void update(Evenement event) throws SQLException {
        String query = "UPDATE evenements SET nameEvent = ?, categorieEvent = ?, workshopsAvailable = ?, numberOfWorkshop = ?, description = ?, price = ?, lieuName = ?, dateD = ?, dateF = ?, lieuld = ?, userid = ?, atelierId = ?, imagePath = ? WHERE idEvent = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, event.getNameEvent());
            stmt.setString(2, event.getCategorieEvent());
            stmt.setString(3, event.getWorkshopsAvailable());
            stmt.setInt(4, event.getNumberOfWorkshop());
            stmt.setString(5, event.getDescription());
            stmt.setFloat(6, event.getPrice());
            stmt.setString(7, event.getLieuName());
            stmt.setDate(8, Date.valueOf(event.getDateD()));
            stmt.setDate(9, Date.valueOf(event.getDateF()));
            stmt.setInt(10, event.getLieuld());
            stmt.setInt(11, event.getUserid());
            stmt.setInt(12, event.getAtelierId());
            stmt.setString(13, event.getImagePath());
            stmt.setInt(14, event.getIdEvent());
            stmt.executeUpdate();
        }
    }

    // Supprimer un événement
    public void delete(Integer eventId) throws SQLException {
        String query = "DELETE FROM evenements WHERE idEvent = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, eventId);
            stmt.executeUpdate();
        }
    }

    // Supprimer un événement (surcharge avec objet Evenement)
    public void delete(Evenement event) throws SQLException {
        delete(event.getIdEvent());
    }

    // Rechercher des événements par nom
    public List<Evenement> searchByName(String query) throws SQLException {
        List<Evenement> events = new ArrayList<>();
        String sql = "SELECT * FROM evenements WHERE nameEvent LIKE ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, "%" + query + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                events.add(new Evenement(
                        rs.getInt("idEvent"),
                        rs.getString("nameEvent"),
                        rs.getString("categorieEvent"),
                        rs.getString("workshopsAvailable"),
                        rs.getInt("numberOfWorkshop"),
                        rs.getString("description"),
                        rs.getFloat("price"),
                        rs.getString("lieuName"),
                        rs.getDate("dateD").toLocalDate(),
                        rs.getDate("dateF").toLocalDate(),
                        rs.getInt("lieuld"),
                        rs.getInt("userid"),
                        rs.getInt("atelierId"),
                        rs.getString("imagePath")
                ));
            }
        }
        return events;
    }

    // Trouver un événement par ID
    public Evenement findById(Integer eventId) throws SQLException {
        String query = "SELECT * FROM evenements WHERE idEvent = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, eventId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Evenement(
                        rs.getInt("idEvent"),
                        rs.getString("nameEvent"),
                        rs.getString("categorieEvent"),
                        rs.getString("workshopsAvailable"),
                        rs.getInt("numberOfWorkshop"),
                        rs.getString("description"),
                        rs.getFloat("price"),
                        rs.getString("lieuName"),
                        rs.getDate("dateD").toLocalDate(),
                        rs.getDate("dateF").toLocalDate(),
                        rs.getInt("lieuld"),
                        rs.getInt("userid"),
                        rs.getInt("atelierId"),
                        rs.getString("imagePath")
                );
            }
        }
        return null;
    }
}