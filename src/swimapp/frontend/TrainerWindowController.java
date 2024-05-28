package swimapp.frontend;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import swimapp.backend.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Controller class for the Trainer window in the swim application.
 * Handles UI interactions, team and discipline member displays, and record addition functionalities.
 */
public class TrainerWindowController {

    @FXML
    public AnchorPane ap_T2M;
    @FXML
    public AnchorPane ap_T2W;
    @FXML
    public AnchorPane ap_T1W;
    @FXML
    public AnchorPane ap_T1M;
    @FXML
    public TitledPane acdn_T1W;
    @FXML
    private Button btn_trnBack;
    @FXML
    private Button btn_CnShow;
    @FXML
    private TitledPane acdn_T1;
    @FXML
    private TitledPane acdn_T2;
    @FXML
    private ListView<String> listView;

    private List<Swimmer> currentSwimmers;

    /**
     * Initializes the controller class. Sets up event handlers for the buttons and accordion panes.
     */
    @FXML
    public void initialize() {

        btn_trnBack.setOnAction(event -> goBack());
        btn_CnShow.setOnAction(event -> showCompetitionWindow());

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

        List<Discipline> disciplines = GuiInterface.getAllDisciplines();

        for (Discipline discipline : disciplines) {
            Button buttonT1M = new Button(discipline.getName());
            Button buttonT1W = new Button(discipline.getName());
            Button buttonT2M = new Button(discipline.getName());
            Button buttonT2W = new Button(discipline.getName());
            buttonT1M.setOnAction(e -> showDisciplineMembers(discipline.getDisciplineID(), 1, Gender.MALE));
            buttonT1W.setOnAction(e -> showDisciplineMembers(discipline.getDisciplineID(), 1, Gender.FEMALE));
            buttonT2M.setOnAction(e -> showDisciplineMembers(discipline.getDisciplineID(), 2, Gender.MALE));
            buttonT2W.setOnAction(e -> showDisciplineMembers(discipline.getDisciplineID(), 2, Gender.FEMALE));
            ap_T1M.getChildren().add(buttonT1M);
            ap_T1W.getChildren().add(buttonT1W);
            ap_T2M.getChildren().add(buttonT2M);
            ap_T2W.getChildren().add(buttonT2W);
        }

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

    /**
     * Navigates back to the main window.
     */
    private void goBack() {
        try {
            Main.showMainWindow();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Displays the members of a specified team.
     *
     * @param teamID the ID of the team
     * @throws SQLException if an SQL error occurs
     */
    private void showTeamMembers(int teamID) throws SQLException {
        try {
            listView.getItems().clear();
            currentSwimmers = GuiInterface.getSwimmersByTeam(teamID);
            for (Swimmer swimmer : currentSwimmers) {
                listView.getItems().add(swimmer.getName());
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    /**
     * Displays the members of a specified team and gender.
     *
     * @param teamID the ID of the team
     * @param gender the gender of the swimmers
     */
    private void showTeamGenderMembers(int teamID, Gender gender) {
        try {
            listView.getItems().clear();
            currentSwimmers = GuiInterface.getSwimmersByTeamAndGender(teamID, gender);
            for (Swimmer swimmer : currentSwimmers) {
                listView.getItems().add(swimmer.getName());
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    /**
     * Displays the members of a specified discipline, team, and gender.
     *
     * @param disciplineID the ID of the discipline
     * @param teamID       the ID of the team
     * @param gender       the gender of the swimmers
     */
    private void showDisciplineMembers(int disciplineID, int teamID, Gender gender) {
        try {
            listView.getItems().clear();
            List<Swimmer> disciplineSwimmers = GuiInterface.getSwimmersByDiscipline(disciplineID);
            List<Swimmer> teamNGenderSwimmers = GuiInterface.getSwimmersByTeamAndGender(teamID, gender);
            currentSwimmers = new ArrayList<>();
            for (Swimmer swimmer : teamNGenderSwimmers) {
                for (Swimmer dSwimmer : disciplineSwimmers) {
                    if (swimmer.getSwimmerID() == dSwimmer.getSwimmerID()) {
                        currentSwimmers.add(swimmer);
                    }
                }
            }

            for (Swimmer swimmer : currentSwimmers) {
                listView.getItems().add(swimmer.getName() + " - Time: " + GuiInterface.getBestRecordForSwimmerByDiscipline(swimmer.getSwimmerID(), disciplineID).getTime());
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    /**
     * Opens the window to add a new record for a selected swimmer.
     *
     * @param swimmer the selected swimmer
     */
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

    /**
     * Opens the competition window.
     */
    private void showCompetitionWindow() {
        try {
            Main.showCompetitionWindow();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
