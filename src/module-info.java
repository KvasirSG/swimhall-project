module swimapp {
    requires java.sql;
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;

    opens swimapp.frontend to javafx.graphics, javafx.fxml;
    exports swimapp.frontend;
}
