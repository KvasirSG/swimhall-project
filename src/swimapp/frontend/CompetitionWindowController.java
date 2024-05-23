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
    private TextField tf_CDATE;

    @FXML
    private TextField tf_CLOC;

    @FXML
    private Button btn_CDesM;

    private DatabaseManager dbManager;

    @FXML
    public void initialize() {
        dbManager = new DatabaseManager();

        btn_CBack.setOnAction(event -> goBack());
    }

    private void goBack() {
        try {
            Main.showTrainerWindow();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void createCompetition() {
        try {
            int competitionID = Integer.parseInt(tf_CCID.getText());
            int swimmerID = Integer.parseInt(tf_CSID.getText());
            int disciplineID = Integer.parseInt(tf_CDISC.getText());
            LocalDate dateOfRecord = LocalDate.parse(tf_CDATE.getText(), DateTimeFormatter.ofPattern("yyyy/MM/dd"));

            //Nedstående skal ændres - taget fra RecordADDWINDOWCONTROLLER

            Record newRecord = new Record(swimmerID, disciplineID, newRecordTime, dateOfRecord);
            dbManager.addPerformanceRecord(newRecord);

            // Provide confirmation to the user
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Record Created");
            alert.setHeaderText(null);
            alert.setContentText("New record for swimmer ID " + swimmerID + " has been successfully created.");
            alert.showAndWait();

            // Clear the fields after adding the record
            tf_NrSID.clear();
            tf_NrDoR.clear();
            tf_NrNrt.clear();
            tf_Nr_Disc.clear();

        } catch (Exception e) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Error creating record: " + e.getMessage());
            alert.showAndWait();
        }
    }



}
