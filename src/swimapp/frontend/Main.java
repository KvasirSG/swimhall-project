package swimapp.frontend;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    private static Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws IOException {
        Main.primaryStage = primaryStage;
        showMainWindow();
    }

    public static void showMainWindow() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/swimapp/frontend/MainWindow.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        primaryStage.setTitle("Main Window");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void showKassérWindow() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/swimapp/frontend/KassérWindow.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        primaryStage.setTitle("Kassér Window");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void showAdminWindow() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/swimapp/frontend/AdminWindow.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        primaryStage.setTitle("Admin Window");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void showMemberAddWindow() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/swimapp/frontend/MemberAddWindow.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        primaryStage.setTitle("Add Member");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
