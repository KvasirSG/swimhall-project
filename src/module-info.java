module swimapp {
    requires java.sql;
    requires javafx.controls;
    requires javafx.fxml;

    opens swimapp.frontend to javafx.fxml;
    exports swimapp.frontend;
}