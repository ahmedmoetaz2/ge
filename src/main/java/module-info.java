module org.example.event_plannerrr {
    // Modules JavaFX requis
    requires javafx.controls;
    requires javafx.fxml;

    // Module MySQL Connector (non modulaire)
    requires static mysql.connector.j;

    // Module SQL (pour l'accès aux bases de données)
    requires java.sql;
    requires java.desktop;

    // Exporter les packages nécessaires
    exports org.example.event_plannerrr;
    exports Controller;
    exports entities;
    exports Services;
    exports Utils;

    // Ouvrir les packages FXML pour permettre l'accès par JavaFX
    opens org.example.event_plannerrr to  javafx.graphics, javafx.fxml;
    opens Controller to javafx.fxml;

}