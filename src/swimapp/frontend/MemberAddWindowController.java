package swimapp.frontend;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.text.Text;
import swimapp.backend.*;
import swimapp.frontend.Main;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class MemberAddWindowController {

    public ComboBox combobox_NmMtype;
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
    private ToggleButton btn_NmTypeToggle;

    @FXML
    private Text selectedGenderText;

    private Gender selectedGenderToEnum;

    @FXML
    public void initialize() {
        genderComboBox.getItems().addAll(Gender.MALE.name(), Gender.FEMALE.name());
        genderComboBox.setOnAction(event -> {
            String selectedGender = genderComboBox.getSelectionModel().getSelectedItem();
            selectedGenderText.setText("Selected Gender: " + selectedGender);
            selectedGenderToEnum = Gender.valueOf(selectedGender);
        });

        List<String>types = new ArrayList<>();
        for (MembershipType membershipType: GuiInterface.getMembershipTypes()){

        }
        combobox_NmMtype.getItems().addAll();
        btn_NmBack.setOnAction(event -> goBack());
        btn_NmCreate.setOnAction(event -> addMember());
    }

    private void addMember() {
        try {
            String name = tf_NmName.getText();
            String birthdayStr = tf_NmBday.getText();
            LocalDate birthday = LocalDate.parse(birthdayStr, DateTimeFormatter.ofPattern("yyyy/MM/dd"));
            boolean isPassive = !btn_NmTypeToggle.isSelected();

            Member member = new Member(name, selectedGenderToEnum,birthday,isPassive);
            GuiInterface.addMember(member);

            // Clear the fields after adding the member
            tf_NmName.clear();
            tf_NmBday.clear();
            btn_NmTypeToggle.setSelected(false);

            // Provide confirmation to the user
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Member Created");
            alert.setHeaderText(null);
            alert.setContentText("Member " + member.getName() + " has been successfully created.");
            alert.showAndWait();

        } catch (Exception e) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Error adding member: " + e.getMessage());
            alert.showAndWait();
        }
    }

    private void goBack() {
        try {
            Main.showAdminWindow();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
