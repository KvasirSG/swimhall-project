package swimapp.frontend;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import swimapp.backend.*;

import java.time.format.DateTimeFormatter;

/**
 * Controller class for the Member Detail window in the swim application.
 * Handles UI interactions and updates the member detail display.
 */
public class AdmMemberDetailController {

    @FXML
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

    /**
     * Sets the member and displays their details.
     *
     * @param member the member to display details for
     */
    public void setMember(Member member) {
        this.member = member;
        displayMemberDetails();
    }

    /**
     * Initializes the controller class. Sets up event handlers for the buttons.
     */
    @FXML
    public void initialize() {
        btn_toggleMembershipStatus.setOnAction(event -> toggleMembershipStatus());
        btn_addToSwimmer.setOnAction(actionEvent -> toggleSwimmer());
        btn_amdBack.setOnAction(event -> closeWindow());
    }

    /**
     * Displays the member details in the UI.
     */
    private void displayMemberDetails() {
        if (member != null) {
            lbl_name.setText(member.getName());
            lbl_birthday.setText(member.getBirthday().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
            lbl_gender.setText(member.getGender().name());
            lbl_membership.setText(member.getMembershipType().getDescription());

            boolean isCurrentlyPassive = member.getMembershipTypeID() == MembershipType.PASSIVE;
            btn_toggleMembershipStatus.setText(isCurrentlyPassive ? "Set Active" : "Set Passive");

            btn_addToSwimmer.setText(isCurrentlySwimmer() ? "Remove from Swimmer" : "Set as Swimmer");
        }
    }

    /**
     * Toggles the membership status between active and passive.
     */
    private void toggleMembershipStatus() {
        boolean isCurrentlyPassive = member.getMembershipTypeID() == MembershipType.PASSIVE;
        member.setMembershipTypeID(isCurrentlyPassive ? calculateActiveMembershipTypeID(member.getAge()) : MembershipType.PASSIVE);
        member.update(GuiInterface.getDbManager());

        btn_toggleMembershipStatus.setText(isCurrentlyPassive ? "Set Active" : "Set Passive");
    }

    /**
     * Toggles the swimmer status for the member.
     */
    private void toggleSwimmer() {
        Swimmer swimmer = GuiInterface.getSwimmerByMemberID(member.getMemberID());
        if (swimmer != null) {
            swimmer.remove(GuiInterface.getDbManager(), false);
        } else {
            Swimmer swimmer1 = new Swimmer(member.getMemberID(), member.getName(), member.getGender(), member.getBirthday(), member.getMembershipType());
            swimmer1.registerSwimmer(GuiInterface.getDbManager(), false);
        }
        btn_addToSwimmer.setText(isCurrentlySwimmer() ? "Remove from Swimmer" : "Set as Swimmer");
    }

    /**
     * Checks if the member is currently a swimmer.
     *
     * @return true if the member is a swimmer, false otherwise
     */
    private boolean isCurrentlySwimmer() {
        return GuiInterface.getSwimmerByMemberID(member.getMemberID()) != null;
    }

    /**
     * Calculates the active membership type ID based on the member's age.
     *
     * @param age the age of the member
     * @return the membership type ID
     */
    private int calculateActiveMembershipTypeID(int age) {
        if (age >= 60) {
            return MembershipType.SENIOR;
        } else if (age >= 18) {
            return MembershipType.ADULT;
        } else {
            return MembershipType.JUNIOR;
        }
    }

    /**
     * Closes the member detail window.
     */
    private void closeWindow() {
        btn_amdBack.getScene().getWindow().hide();
    }
}
