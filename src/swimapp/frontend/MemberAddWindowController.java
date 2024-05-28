package swimapp.frontend;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.text.Text;
import swimapp.backend.*;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Controller class for the Member Add window in the swim application.
 * Handles UI interactions and member creation functionalities.
 */
public class MemberAddWindowController {

    @FXML
    private ComboBox<String> genderComboBox;

    @FXML
    private Button btn_NmBack;

    @FXML
    private Button btn_NmCreate;

    @FXML
    private TextField tf_NmName;

    @FXML
    private TextField tf_NmBday;

    @FXML
    private Text selectedGenderText;

    private Gender selectedGenderToEnum;

    /**
     * Initializes the controller class. Sets up event handlers for the buttons and ComboBox.
     */
    @FXML
    public void initialize() {
        genderComboBox.getItems().addAll(Gender.MALE.toString(), Gender.FEMALE.toString());
        genderComboBox.setOnAction(event -> {
            String selectedGender = genderComboBox.getSelectionModel().getSelectedItem();
            selectedGenderText.setText("Selected Gender: " + selectedGender);
            selectedGenderToEnum = Gender.valueOf(selectedGender);
        });

        btn_NmBack.setOnAction(event -> goBack());
        btn_NmCreate.setOnAction(event -> addMember());
    }

    /**
     * Adds a new member based on the input fields.
     */
    private void addMember() {
        try {
            String name = tf_NmName.getText();
            String birthdayStr = tf_NmBday.getText();
            LocalDate birthday = LocalDate.parse(birthdayStr, DateTimeFormatter.ofPattern("yyyy/MM/dd"));
            boolean isPassive = false; // Adjust this based on your application logic

            DatabaseManager dbManager = new DatabaseManager();
            Member member = new Member(name, selectedGenderToEnum, birthday, isPassive);

            member.registerMember(dbManager);

            // Clear the fields after adding the member
            tf_NmName.clear();
            tf_NmBday.clear();
            genderComboBox.getSelectionModel().clearSelection();
            selectedGenderText.setText("");

            // Provide confirmation to the user
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Member Created");
            alert.setHeaderText(null);
            alert.setContentText("Member " + member.getName() + " has been successfully created.");
            alert.showAndWait();

        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Error adding member: " + e.getMessage());
            alert.showAndWait();
        }
    }

    /**
     * Navigates back to the Admin window.
     */
    private void goBack() {
        try {
            Main.showAdminWindow();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
