package Services;

import Models.Evenement;
import Utils.MyDataBase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EvenementService {
    private Connection cnx;

    public EvenementService() {
        this.cnx = MyDataBase.getInstance().getConnection();
        if (this.cnx == null) {
            throw new RuntimeException("La connexion à la base de données est nulle !");
        }
    }

    // Méthode pour ajouter un événement
    public void ajouter(Evenement event) throws SQLException {
        String query = "INSERT INTO evenement (nameEvent, categorieEvent, description, price, dateD, dateF, lieuId, userId, atelierId, workshopsAvailable, numberOfWorkshop) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = cnx.prepareStatement(query)) {
            ps.setString(1, event.getNameEvent());
            ps.setString(2, event.getCategorieEvent());
            ps.setString(3, event.getDescription());
            ps.setFloat(4, event.getPrice());
            ps.setDate(5, java.sql.Date.valueOf(event.getDateD()));
            ps.setDate(6, java.sql.Date.valueOf(event.getDateF()));
            ps.setInt(7, event.getLieuId());
            ps.setInt(8, event.getUserId());
            ps.setInt(9, event.getAtelierId());
            ps.setString(10, event.getWorkshopsAvailable());
            ps.setInt(11, event.getNumberOfWorkshop());

            ps.executeUpdate();
        }
    }

    // Méthode pour modifier un événement
    public void modifier(Evenement event) throws SQLException {
        String query = "UPDATE evenement SET nameEvent = ?, categorieEvent = ?, description = ?, price = ?, dateD = ?, dateF = ?, lieuId = ?, userId = ?, atelierId = ?, workshopsAvailable = ?, numberOfWorkshop = ? WHERE idEvent = ?";
        try (PreparedStatement ps = cnx.prepareStatement(query)) {
            ps.setString(1, event.getNameEvent());
            ps.setString(2, event.getCategorieEvent());
            ps.setString(3, event.getDescription());
            ps.setFloat(4, event.getPrice());
            ps.setDate(5, java.sql.Date.valueOf(event.getDateD()));
            ps.setDate(6, java.sql.Date.valueOf(event.getDateF()));
            ps.setInt(7, event.getLieuId());
            ps.setInt(8, event.getUserId());
            ps.setInt(9, event.getAtelierId());
            ps.setString(10, event.getWorkshopsAvailable());
            ps.setInt(11, event.getNumberOfWorkshop());
            ps.setInt(12, event.getIdEvent());

            ps.executeUpdate();
        }
    }

    // Méthode pour supprimer un événement
    public void supprimer(Evenement event) throws SQLException {
        String query = "DELETE FROM evenement WHERE idEvent = ?";
        try (PreparedStatement ps = cnx.prepareStatement(query)) {
            ps.setInt(1, event.getIdEvent());
            ps.executeUpdate();
        }
    }

    // Méthode pour afficher tous les événements
    public List<Evenement> afficher() throws SQLException {
        List<Evenement> evenements = new ArrayList<>();
        String query = "SELECT * FROM evenement";

        try (PreparedStatement ps = cnx.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Evenement event = new Evenement(
                        rs.getInt("idEvent"),
                        rs.getString("nameEvent"),
                        rs.getString("categorieEvent"),
                        rs.getString("description"),
                        rs.getFloat("price"),
                        rs.getDate("dateD").toLocalDate(),
                        rs.getDate("dateF").toLocalDate(),
                        rs.getInt("lieuId"),
                        rs.getInt("userId"),
                        rs.getInt("atelierId"),
                        rs.getString("workshopsAvailable"),
                        rs.getInt("numberOfWorkshop")
                );
                evenements.add(event);
            }
        }
        return evenements;
    }
}