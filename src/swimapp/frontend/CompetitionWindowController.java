package swimapp.frontend;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import swimapp.backend.DatabaseManager;
import swimapp.backend.Record;
import swimapp.frontend.Main;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CompetitionWindowController
{
    @FXML
    private Button btn_CBack;

    @FXML
    private TextField tf_CCID;

    @FXML
    private TextField tf_CSID;

    @FXML
    private TextField tf_CDISC;

    @FXML
    private DatePicker tf_CNRT;

    @FXML
    private TextField tf_CLOC;

    @FXML
    private Button btn_CDesM;

    private DatabaseManager dbManager;



}
