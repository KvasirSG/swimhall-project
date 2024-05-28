package swimapp.frontend;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import swimapp.backend.DatabaseManager;
import swimapp.backend.Discipline;
import swimapp.backend.GuiInterface;
import swimapp.backend.Record;
import swimapp.backend.Swimmer;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Controller class for the Record Add window in the swim application.
 * Handles UI interactions and record creation functionalities.
 */
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
    private ComboBox<Discipline> cb_NrDisc;

    private DatabaseManager dbManager;

    private Swimmer swimmer;

    /**
     * Initializes the controller class. Sets up event handlers for the buttons and ComboBox.
     */
    @FXML
    public void initialize() {
        dbManager = new DatabaseManager();
        List<Discipline> disciplines = dbManager.getDisciplines();
        dbManager.closeConnection();
        cb_NrDisc.getItems().addAll(disciplines);

        btn_NrBack.setOnAction(event -> goBack());
        btn_NrAdd.setOnAction(event -> addRecord());
    }

    /**
     * Sets the swimmer and displays their ID.
     *
     * @param swimmer the swimmer to set
     */
    public void setSwimmer(Swimmer swimmer) {
        this.swimmer = swimmer;
        tf_NrSID.setText(String.valueOf(swimmer.getSwimmerID()));
        tf_NrSID.setDisable(true);
    }

    /**
     * Adds a new performance record for the swimmer.
     */
    private void addRecord() {
        try {
            int swimmerID = swimmer.getSwimmerID();
            Discipline selectedDiscipline = cb_NrDisc.getSelectionModel().getSelectedItem();
            if (selectedDiscipline == null) {
                showAlert("Error", "Please select a discipline.");
                return;
            }
            double newRecordTime = Double.parseDouble(tf_NrNrt.getText());
            String dateOfRecordStr = tf_NrDoR.getText();
            LocalDate dateOfRecord = LocalDate.parse(dateOfRecordStr, DateTimeFormatter.ofPattern("yyyy/MM/dd"));

            // Check if the swimmer already has the selected discipline
            boolean hasDiscipline = false;
            for (Discipline discipline : GuiInterface.getDisciplinesForSwimmer(swimmerID)) {
                if (selectedDiscipline.getDisciplineID() == discipline.getDisciplineID()) {
                    hasDiscipline = true;
                    break;
                }
            }
            // If the swimmer doesn't have the discipline, add it
            if (!hasDiscipline) {
                GuiInterface.addSwimmerDiscipline(swimmer, selectedDiscipline);
            }

            // Add the performance record
            Record record = new Record(swimmerID, selectedDiscipline.getDisciplineID(), newRecordTime, dateOfRecord);
            GuiInterface.addPerformanceRecord(record);

            // Clear the fields after adding the record
            tf_NrSID.clear();
            tf_NrDoR.clear();
            tf_NrNrt.clear();
            cb_NrDisc.getSelectionModel().clearSelection();

            // Provide confirmation to the user
            showAlert("Record Added", "Record for Swimmer ID " + swimmerID + " in discipline " + selectedDiscipline.getName() + " has been successfully added.");

        } catch (Exception e) {
            showAlert("Error", "Error adding record: " + e.getMessage());
        }
    }

    /**
     * Closes the Record Add window.
     */
    private void goBack() {
        Stage stage = (Stage) btn_NrBack.getScene().getWindow();
        stage.close();
    }

    /**
     * Displays an alert dialog.
     *
     * @param title   the title of the alert
     * @param content the content text of the alert
     */
    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
