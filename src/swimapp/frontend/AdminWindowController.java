package swimapp.frontend;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import swimapp.backend.DatabaseManager;
import swimapp.backend.GuiInterface;
import swimapp.backend.Member;
import swimapp.backend.MembershipType;
import swimapp.frontend.Main;

import java.io.IOException;
import java.util.List;

public class AdminWindowController {

    @FXML
    private Button btn_admMbShow;

    @FXML
    private Button btn_admMbPassive;

    @FXML
    private Button btn_admMbAdd;

    @FXML
    private Button btn_admBack;

    @FXML
    private Button btn_admMbActive;

    @FXML
    private ListView<Member> lst_admMemList;

    @FXML
    public void initialize() {
        btn_admMbShow.setOnAction(event -> showMembers());
        btn_admMbPassive.setOnAction(event -> showPassiveMembers());
        btn_admMbAdd.setOnAction(event -> openAddMemberWindow());
        btn_admMbActive.setOnAction(event -> showActiveMembers());
        btn_admBack.setOnAction(event -> goBack());
    }

    private void showMembers() {
        lst_admMemList.getItems().clear();

        List<Member> members = GuiInterface.getAllMembers();
        for (Member member : members) {
            lst_admMemList.getItems().add(member);
        }

        // Add event listener for ListView selection changes
        lst_admMemList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                try {
                    openMemberDetailWindow(newValue);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });


    }

    private void showPassiveMembers() {
        lst_admMemList.getItems().clear();
        List<Member> members = GuiInterface.getMembersByType(MembershipType.PASSIVE);
        for (Member member : members) {
            lst_admMemList.getItems().add(member);
        }
    }

    private void showActiveMembers() {
        lst_admMemList.getItems().clear();
        List<Member> members = GuiInterface.getMembersByType(MembershipType.ADULT);
        members.addAll(GuiInterface.getMembersByType(MembershipType.JUNIOR));
        members.addAll(GuiInterface.getMembersByType(MembershipType.SENIOR));
        for (Member member : members) {
            lst_admMemList.getItems().add(member);
        }
    }

    private void openAddMemberWindow() {
        try {
            Main.showMemberAddWindow();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void goBack() {
        try {
            Main.showMainWindow();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void openMemberDetailWindow(Member member) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/swimapp/frontend/AdmMemberDetail.fxml"));
        Parent root = loader.load();

        // Get the controller and set the member
        AdmMemberDetailController controller = loader.getController();
        controller.setMember(member);

        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Member Details");
        stage.setScene(new Scene(root));
        stage.show();
    }
}
