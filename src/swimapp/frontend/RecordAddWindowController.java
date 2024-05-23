package swimapp.frontend;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import swimapp.backend.DatabaseManager;
import swimapp.backend.Record;
import swimapp.frontend.Main;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class RecordAddWindowController {

    @FXML
    private Button btn_NrBack;

    @FXML
    private TextField tf_NrSID;

    @FXML
    private TextField tf_NrDoR;

    @FXML
    private TextField tf_NrNrt;

    @FXML
    private TextField tf_Nr_Disc;

    private DatabaseManager dbManager;

    @FXML
    public void initialize() {
        dbManager = new DatabaseManager();

        btn_NrBack.setOnAction(event -> goBack());
    }

    @FXML
    private void createRecord() {
        try {
            int swimmerID = Integer.parseInt(tf_NrSID.getText());
            LocalDate dateOfRecord = LocalDate.parse(tf_NrDoR.getText(), DateTimeFormatter.ofPattern("yyyy/MM/dd"));
            double newRecordTime = Double.parseDouble(tf_NrNrt.getText());
            int disciplineID = Integer.parseInt(tf_Nr_Disc.getText());

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

    private void goBack() {
        try {
            Main.showTrainerWindow();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
