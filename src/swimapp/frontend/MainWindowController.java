package swimapp.frontend;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import swimapp.frontend.Main;

import java.io.IOException;

public class MainWindowController {

    @FXML
    private Button btn_ksr;

    @FXML
    public void initialize() {
        btn_ksr.setOnAction(event -> {
            try {
                Main.showKassérWindow();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
