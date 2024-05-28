package swimapp.frontend;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import swimapp.backend.*;

import java.time.format.DateTimeFormatter;

public class AdmMemberDetailController {

    public Button btn_addToSwimmer;
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

    private Member member;

    public void setMember(Member member) {
        this.member = member;
        displayMemberDetails();
    }

    @FXML
    public void initialize() {

        btn_toggleMembershipStatus.setOnAction(event -> toggleMembershipStatus());
        btn_addToSwimmer.setOnAction(actionEvent -> toggleSwimmer());

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

            btn_addToSwimmer.setText( isCurrentlySwimmer() ? "Remove from Swimmer":"Set as Swimmer");
        }
    }

    private void toggleMembershipStatus() {
        boolean isCurrentlyPassive = member.getMembershipTypeID() == MembershipType.PASSIVE;
        member.setMembershipTypeID(isCurrentlyPassive ? calculateActiveMembershipTypeID(member.getAge()) : MembershipType.PASSIVE);
        member.update(GuiInterface.getDbManager());

        btn_toggleMembershipStatus.setText(isCurrentlyPassive ? "Set Active" : "Set Passive");
    }

    private void toggleSwimmer(){
        Swimmer swimmer =  GuiInterface.getSwimmerByMemberID(member.getMemberID());
        if (swimmer != null){
            swimmer.remove(GuiInterface.getDbManager(),false);
        }else{
            Swimmer swimmer1 = new Swimmer(member.getMemberID(),member.getName(),member.getGender(),member.getBirthday(),member.getMembershipType());
            swimmer1.registerSwimmer(GuiInterface.getDbManager(),false);
        }
        btn_addToSwimmer.setText( isCurrentlySwimmer() ? "Remove from Swimmer":"Set as Swimmer");
    }
    private boolean isCurrentlySwimmer(){
        return GuiInterface.getSwimmerByMemberID(member.getMemberID()) != null;
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
