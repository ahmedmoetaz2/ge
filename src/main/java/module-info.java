module org.example.event_plannerrr {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.event_plannerrr to javafx.fxml;
    exports org.example.event_plannerrr;
}