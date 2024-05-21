package swimapp.frontend;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import swimapp.backend.DatabaseManager;
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
    private Button btn_Back;

    @FXML
    private Button btn_admMbActive;

    @FXML
    private ListView<String> listView;

    private DatabaseManager dbManager;

    @FXML
    public void initialize() {
        dbManager = new DatabaseManager();

        btn_admMbShow.setOnAction(event -> showMembers());
        btn_admMbPassive.setOnAction(event -> showPassiveMembers());
        btn_admMbAdd.setOnAction(event -> openAddMemberWindow());
        btn_admMbActive.setOnAction(event -> showActiveMembers());
        btn_Back.setOnAction(event -> goBack());
    }

    private void showMembers() {
        listView.getItems().clear();
        List<Member> members = dbManager.getAllMembers();
        for (Member member : members) {
            listView.getItems().add(member.toString());
        }
    }

    private void showPassiveMembers() {
        listView.getItems().clear();
        List<Member> members = dbManager.getMembersByType(MembershipType.PASSIVE);
        for (Member member : members) {
            listView.getItems().add(member.toString());
        }
    }

    private void showActiveMembers() {
        listView.getItems().clear();
        List<Member> members = dbManager.getMembersByType(MembershipType.ADULT);
        members.addAll(dbManager.getMembersByType(MembershipType.JUNIOR));
        members.addAll(dbManager.getMembersByType(MembershipType.SENIOR));
        for (Member member : members) {
            listView.getItems().add(member.toString());
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
}
