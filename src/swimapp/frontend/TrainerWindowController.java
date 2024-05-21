package swimapp.frontend;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import swimapp.frontend.Main;

import java.io.IOException;

public class TrainerWindowController {

    @FXML
    private Button btn_Back;

    @FXML
    public void initialize() {
        btn_Back.setOnAction(event -> goBack());
    }

    private void goBack() {
        try {
            Main.showMainWindow();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
