package swimapp.frontend;

import javafx.fxml.FXML;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TitledPane;
import swimapp.backend.DatabaseManager;
import swimapp.backend.Gender;
import swimapp.backend.GuiInterface;
import swimapp.backend.Swimmer;
import swimapp.frontend.Main;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class TrainerWindowController {

    @FXML
    private Button btn_trnBack;
    @FXML
    private Button btn_AddRecord;

    @FXML
    private TitledPane acdn_T1;

    @FXML
    private TitledPane acdn_T2;

    @FXML
    private TitledPane acdn_T1M;

    @FXML
    private TitledPane acdn_T1W;

    @FXML
    private TitledPane acdn_T2M;

    @FXML
    private TitledPane acdn_T2W;

    @FXML
    private Button btn_T1MD1;

    @FXML
    private Button btn_T1MD2;

    @FXML
    private Button btn_T1MD3;

    @FXML
    private Button btn_T1MD4;

    @FXML
    private Button btn_T1MD5;

    @FXML
    private Button btn_T1WD1;

    @FXML
    private Button btn_T1WD2;

    @FXML
    private Button btn_T1WD3;

    @FXML
    private Button btn_T1WD4;

    @FXML
    private Button btn_T1WD5;

    @FXML
    private Button btn_T2MD1;

    @FXML
    private Button btn_T2MD2;

    @FXML
    private Button btn_T2MD3;

    @FXML
    private Button btn_T2MD4;

    @FXML
    private Button btn_T2MD5;

    @FXML
    private Button btn_T2WD1;

    @FXML
    private Button btn_T2WD2;

    @FXML
    private Button btn_T2WD3;

    @FXML
    private Button btn_T2WD4;

    @FXML
    private Button btn_T2WD5;

    @FXML
    private ListView<String> listView;

    private DatabaseManager dbManager;

    @FXML
    public void initialize() {
        dbManager = new DatabaseManager();

        btn_trnBack.setOnAction(event -> goBack());
        btn_AddRecord.setOnAction(event -> showAddRecordWindow());

        acdn_T1.setOnMouseClicked(event -> {
            try {
                showTeamMembers(1);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
        acdn_T2.setOnMouseClicked(event -> {
            try {
                showTeamMembers(2);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });

        acdn_T1M.setOnMouseClicked(event -> showTeamGenderMembers(1, Gender.MALE));
        acdn_T1W.setOnMouseClicked(event -> showTeamGenderMembers(1, Gender.FEMALE));

        acdn_T2M.setOnMouseClicked(event -> showTeamGenderMembers(2, Gender.MALE));
        acdn_T2W.setOnMouseClicked(event -> showTeamGenderMembers(2, Gender.FEMALE));

        btn_T1MD1.setOnAction(event -> showDisciplineMembers(1));
        btn_T1MD2.setOnAction(event -> showDisciplineMembers(2));
        btn_T1MD3.setOnAction(event -> showDisciplineMembers(3));
        btn_T1MD4.setOnAction(event -> showDisciplineMembers(4));
        btn_T1MD5.setOnAction(event -> showDisciplineMembers(5));

        btn_T1WD1.setOnAction(event -> showDisciplineMembers(1));
        btn_T1WD2.setOnAction(event -> showDisciplineMembers(2));
        btn_T1WD3.setOnAction(event -> showDisciplineMembers(3));
        btn_T1WD4.setOnAction(event -> showDisciplineMembers(4));
        btn_T1WD5.setOnAction(event -> showDisciplineMembers(5));

        btn_T2MD1.setOnAction(event -> showDisciplineMembers(1));
        btn_T2MD2.setOnAction(event -> showDisciplineMembers(2));
        btn_T2MD3.setOnAction(event -> showDisciplineMembers(3));
        btn_T2MD4.setOnAction(event -> showDisciplineMembers(4));
        btn_T2MD5.setOnAction(event -> showDisciplineMembers(5));

        btn_T2WD1.setOnAction(event -> showDisciplineMembers(1));
        btn_T2WD2.setOnAction(event -> showDisciplineMembers(2));
        btn_T2WD3.setOnAction(event -> showDisciplineMembers(3));
        btn_T2WD4.setOnAction(event -> showDisciplineMembers(4));
        btn_T2WD5.setOnAction(event -> showDisciplineMembers(5));
    }

    private void goBack() {
        try {
            Main.showMainWindow();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showTeamMembers(int teamID) throws SQLException {
        try {
            listView.getItems().clear();
            List<Swimmer> swimmers = GuiInterface.getSwimmersByTeam(teamID);
            for (Swimmer swimmer : swimmers) {
                listView.getItems().add(swimmer.getName());
            }
        } catch (NullPointerException e){
            e.printStackTrace();
        }
    }

    private void showTeamGenderMembers(int teamID, Gender gender) {
        try {
            listView.getItems().clear();
            List<Swimmer> swimmers = GuiInterface.getSwimmersByTeamAndGender(teamID, gender);
            for (Swimmer swimmer : swimmers) {
                listView.getItems().add(swimmer.getName());
            }
        } catch ( NullPointerException e){
            e.printStackTrace();
        }
    }

    private void showDisciplineMembers(int disciplineID) {
        try {
            listView.getItems().clear();
            List<Swimmer> swimmers = GuiInterface.getSwimmersByDiscipline(disciplineID);
            for (Swimmer swimmer : swimmers) {
                listView.getItems().add(swimmer.getName() + " - Time: " + GuiInterface.getBestRecordForSwimmerByDiscipline(swimmer.getSwimmerID(), disciplineID).getTime());
            }
        } catch (NullPointerException e){
            e.printStackTrace();
        }
    }
    private void showAddRecordWindow() {
        try {
            Main.showAddRecordWindow();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
