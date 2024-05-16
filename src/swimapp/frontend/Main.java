package swimapp.frontend;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
public void start(Stage primaryStage) throws IOException {
FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MainWindow.fxml"));
Scene scene = new Scene(fxmlLoader.load(), 800, 600);
primaryStage.setTitle("Delfinen");
primaryStage.setScene(scene);
primaryStage.show();
}
    public static void main(String[] args) {
    launch();
    }
}
