package swimapp.frontend;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import swimapp.frontend.Main;

import java.io.IOException;

public class MainWindowController {

    @FXML
    private Button btn_ksr;

    @FXML
    private Button btn_adm;

    @FXML
    private Button btn_trn;

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
