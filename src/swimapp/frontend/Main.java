package swimapp.frontend;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Main class for the swim application. Manages the primary stage and navigation between different windows.
 */
public class Main extends Application {

    private static Stage primaryStage;

    /**
     * Starts the application by showing the main window.
     *
     * @param primaryStage the primary stage for this application
     * @throws IOException if an I/O error occurs during loading the FXML file
     */
    @Override
    public void start(Stage primaryStage) throws IOException {
        Main.primaryStage = primaryStage;
        showMainWindow();
    }

    /**
     * Shows the main window.
     *
     * @throws IOException if an I/O error occurs during loading the FXML file
     */
    public static void showMainWindow() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/swimapp/frontend/MainWindow.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        primaryStage.setTitle("Main Window");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Shows the Kassér window.
     *
     * @throws IOException if an I/O error occurs during loading the FXML file
     */
    public static void showKassérWindow() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/swimapp/frontend/KassérWindow.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        primaryStage.setTitle("Kassér Window");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Shows the Admin window.
     *
     * @throws IOException if an I/O error occurs during loading the FXML file
     */
    public static void showAdminWindow() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/swimapp/frontend/AdminWindow.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        primaryStage.setTitle("Admin Window");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Shows the window for adding a new member.
     *
     * @throws IOException if an I/O error occurs during loading the FXML file
     */
    public static void showMemberAddWindow() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/swimapp/frontend/MemberAddWindow.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        primaryStage.setTitle("Add Member");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Shows the Trainer window.
     *
     * @throws IOException if an I/O error occurs during loading the FXML file
     */
    public static void showTrainerWindow() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/swimapp/frontend/TrainerWindow.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        primaryStage.setTitle("Trainer Window");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Shows the window for adding a new record.
     *
     * @throws IOException if an I/O error occurs during loading the FXML file
     */
    public static void showAddRecordWindow() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/swimapp/frontend/RecordAddWindow.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        primaryStage.setTitle("Add Record Window");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Shows the Competition window.
     *
     * @throws IOException if an I/O error occurs during loading the FXML file
     */
    public static void showCompetitionWindow() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/swimapp/frontend/CompetitionWindow.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        primaryStage.setTitle("Competition Window");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * The main method for launching the application.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
