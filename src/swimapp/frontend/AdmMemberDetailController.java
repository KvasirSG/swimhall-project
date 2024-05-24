package swimapp.frontend;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import swimapp.backend.DatabaseManager;
import swimapp.backend.Member;
import swimapp.backend.MembershipType;

import java.time.format.DateTimeFormatter;

public class AdmMemberDetailController {

    @FXML
    private Button btn_amdBack;
    @FXML
    private Label lbl_name;
    @FXML
    private Label lbl_birthday;
    @FXML
    private Label lbl_gender;
    @FXML
    private Label lbl_membership;
    @FXML
    private Button btn_toggleMembershipStatus;

    private DatabaseManager dbManager;
    private Member member;

    public void setMember(Member member) {
        this.member = member;
        displayMemberDetails();
    }

    @FXML
    public void initialize() {
        dbManager = new DatabaseManager();

        // Add event listener for the back button
        btn_amdBack.setOnAction(event -> closeWindow());
    }

    private void displayMemberDetails() {
        if (member != null) {
            lbl_name.setText(member.getName());
            lbl_birthday.setText(member.getBirthday().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
            lbl_gender.setText(member.getGender().name());
            lbl_membership.setText(member.getMembershipType().getDescription());

            boolean isCurrentlyPassive = member.getMembershipTypeID() == MembershipType.PASSIVE;
            btn_toggleMembershipStatus.setText(isCurrentlyPassive ? "Set Active" : "Set Passive");
        }
    }

    private void toggleMembershipStatus() {
        boolean isCurrentlyPassive = member.getMembershipTypeID() == MembershipType.PASSIVE;
        member.setMembershipTypeID(isCurrentlyPassive ? calculateActiveMembershipTypeID(member.getAge()) : MembershipType.PASSIVE);
        dbManager.updateMember(member);
    }

    private int calculateActiveMembershipTypeID(int age) {
        if (age >= 60) {
            return MembershipType.SENIOR;
        } else if (age >= 18) {
            return MembershipType.ADULT;
        } else {
            return MembershipType.JUNIOR;
        }
    }

    private void closeWindow() {
        // Code to close the window
        btn_amdBack.getScene().getWindow().hide();
    }
}
