package swimapp.frontend;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import swimapp.backend.DatabaseManager;
import swimapp.backend.Discipline;
import swimapp.backend.Record;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class RecordAddWindowController {

    @FXML
    private Button btn_NrBack;

    @FXML
    private Button btn_NrAdd;

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
        //btn_NrAdd.setOnAction(event -> addRecord());
    }

   /** private void addRecord() {
        try {
            int swimmerID = Integer.parseInt(tf_NrSID.getText());
            String disciplineName = tf_Nr_Disc.getText();
            double newRecordTime = Double.parseDouble(tf_NrNrt.getText());
            String dateOfRecordStr = tf_NrDoR.getText();
            LocalDate dateOfRecord = LocalDate.parse(dateOfRecordStr, DateTimeFormatter.ofPattern("yyyy/MM/dd"));

            Discipline discipline = dbManager.getDisciplineByID(disciplineID);
            if (discipline == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Discipline not found: " + disciplineName);
                alert.showAndWait();
                return;
            }

            Record record = new Record(swimmerID, discipline.getDisciplineID(), newRecordTime, dateOfRecord);
            dbManager.addPerformanceRecord(record);

            // Clear the fields after adding the record
            tf_NrSID.clear();
            tf_NrDoR.clear();
            tf_NrNrt.clear();
            tf_Nr_Disc.clear();

            // Provide confirmation to the user
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Record Added");
            alert.setHeaderText(null);
            alert.setContentText("Record for Swimmer ID " + swimmerID + " in discipline " + disciplineName + " has been successfully added.");
            alert.showAndWait();

        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Error adding record: " + e.getMessage());
            alert.showAndWait();
        }
    }**/

    private void goBack() {
        try {
            Main.showTrainerWindow();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
