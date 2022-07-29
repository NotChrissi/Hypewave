module com.example.hypewave {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires java.sql;

    opens com.example.hypewave to javafx.fxml;
    exports com.example.hypewave;
}