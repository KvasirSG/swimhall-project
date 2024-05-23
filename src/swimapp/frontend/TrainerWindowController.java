package swimapp.frontend;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TitledPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
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

    private List<Swimmer> currentSwimmers;

    @FXML
    public void initialize() {
        dbManager = new DatabaseManager();

        btn_trnBack.setOnAction(event -> goBack());

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

        listView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) { // Double-click
                String selectedItem = listView.getSelectionModel().getSelectedItem();
                if (selectedItem != null) {
                    int index = listView.getSelectionModel().getSelectedIndex();
                    Swimmer selectedSwimmer = currentSwimmers.get(index);
                    openAddRecordWindow(selectedSwimmer);
                }
            }
        });
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
            currentSwimmers = GuiInterface.getSwimmersByTeam(teamID);
            for (Swimmer swimmer : currentSwimmers) {
                listView.getItems().add(swimmer.getName());
            }
        } catch (NullPointerException e){
            e.printStackTrace();
        }
    }

    private void showTeamGenderMembers(int teamID, Gender gender) {
        try {
            listView.getItems().clear();
            currentSwimmers = GuiInterface.getSwimmersByTeamAndGender(teamID, gender);
            for (Swimmer swimmer : currentSwimmers) {
                listView.getItems().add(swimmer.getName());
            }
        } catch ( NullPointerException e){
            e.printStackTrace();
        }
    }

    private void showDisciplineMembers(int disciplineID) {
        try {
            listView.getItems().clear();
            currentSwimmers = GuiInterface.getSwimmersByDiscipline(disciplineID);
            for (Swimmer swimmer : currentSwimmers) {
                listView.getItems().add(swimmer.getName() + " - Time: " + GuiInterface.getBestRecordForSwimmerByDiscipline(swimmer.getSwimmerID(), disciplineID).getTime());
            }
        } catch (NullPointerException e){
            e.printStackTrace();
        }
    }
    private void openAddRecordWindow(Swimmer swimmer) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("RecordAddWindow.fxml"));
            Parent root = loader.load();

            // Get the controller and set the swimmer and discipline
            RecordAddWindowController controller = loader.getController();
            controller.setSwimmer(swimmer);

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Add Record");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
