package swimapp.frontend;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import swimapp.backend.DatabaseManager;
import swimapp.backend.Record;
import swimapp.backend.Swimmer;
import swimapp.backend.Discipline;
import javafx.scene.control.Alert.AlertType;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CompetitionWindowController {

    @FXML
    private Button btn_CBack;

    @FXML
    private Button btn_CDesM;

    @FXML
    private TextField tf_CCID;

    @FXML
    private TextField tf_CLOC;

    @FXML
    private TextField tf_CSID;

    @FXML
    private TextField tf_CDISC;

    @FXML
    private TextField tf_CDATE;

    private DatabaseManager dbManager;

    @FXML
    public void initialize() {
        dbManager = new DatabaseManager();

        btn_CBack.setOnAction(event -> goBack());
        btn_CDesM.setOnAction(event -> designateMember());
    }

    private void goBack() {
        try {
            Main.showTrainerWindow();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void designateMember() {
        try {
            int competitionID = Integer.parseInt(tf_CCID.getText());
            int swimmerID = Integer.parseInt(tf_CSID.getText());
            int disciplineID = Integer.parseInt(tf_CDISC.getText());
            String location = tf_CLOC.getText();
            LocalDate date = LocalDate.parse(tf_CDATE.getText(), DateTimeFormatter.ofPattern("yyyy/MM/dd"));

            Swimmer swimmer = dbManager.getSwimmer(swimmerID);
            if (swimmer == null) {
                showAlert(AlertType.ERROR, "Error", "Swimmer not found", "The swimmer ID provided does not exist.");
                return;
            }

            Discipline discipline = dbManager.getDiscipline(disciplineID);
            if (discipline == null) {
                showAlert(AlertType.ERROR, "Error", "Discipline not found", "The discipline ID provided does not exist.");
                return;
            }

            dbManager.registerSwimmerForCompetition(competitionID, swimmerID);
            showAlert(AlertType.INFORMATION, "Success", "Swimmer Registered", "Swimmer " + swimmer.getName() + " has been successfully registered for the competition.");

            // Clear the fields after registering the swimmer
            tf_CCID.clear();
            tf_CLOC.clear();
            tf_CSID.clear();
            tf_CDISC.clear();
            tf_CDATE.clear();

        } catch (Exception e) {
            showAlert(AlertType.ERROR, "Error", "Error registering swimmer", e.getMessage());
        }
    }

    private void showAlert(AlertType alertType, String title, String header, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
