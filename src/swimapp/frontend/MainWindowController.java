package swimapp.frontend;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import java.io.IOException;

/**
 * Controller class for the Main window in the swim application.
 * Handles UI interactions and navigation to different windows.
 */
public class MainWindowController {

    @FXML
    private Button btn_ksr;

    @FXML
    private Button btn_adm;

    @FXML
    private Button btn_trn;

    /**
     * Initializes the controller class. Sets up event handlers for the buttons.
     */
    @FXML
    public void initialize() {
        btn_ksr.setOnAction(event -> {
            try {
                Main.showKassérWindow();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        btn_adm.setOnAction(event -> {
            try {
                Main.showAdminWindow();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        btn_trn.setOnAction(event -> {
            try {
                Main.showTrainerWindow();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
