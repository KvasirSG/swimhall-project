package swimapp.frontend;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import swimapp.backend.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

/**
 * Controller class for the Kassér window in the swim application.
 * Handles UI interactions and updates the member and invoice-related functionalities.
 */
public class KassérWindowController {

    @FXML
    public Button btn_trsIssInv;

    @FXML
    private Button btn_trsMemShow;

    @FXML
    private Button btn_trsArrShow;

    @FXML
    private Button btn_trsBack;

    @FXML
    private ListView<String> lv_memView;

    private Payments payments;
    private DatabaseManager dbManager;

    /**
     * Initializes the controller class. Sets up event handlers for the buttons and ListView.
     */
    @FXML
    public void initialize() {
        payments = new Payments();
        dbManager = new DatabaseManager();

        btn_trsMemShow.setOnAction(event -> showMembers());
        btn_trsArrShow.setOnAction(event -> showArrears());
        btn_trsBack.setOnAction(event -> goBack());
        btn_trsIssInv.setOnAction(actionEvent -> {
            try {
                openIssueInvoiceWindow();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        lv_memView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                openInvoiceWindow();
            }
        });
    }

    /**
     * Shows all members in the ListView.
     */
    private void showMembers() {
        lv_memView.getItems().clear();
        List<Member> members = dbManager.getAllMembers();
        for (Member member : members) {
            lv_memView.getItems().add(member.toString());
        }
    }

    /**
     * Shows members in arrears in the ListView.
     */
    private void showArrears() {
        lv_memView.getItems().clear();
        payments.fetchMembersFromDB(dbManager);
        payments.fetchInvoicesForMembers(dbManager);
        List<Member> membersInArrears = payments.getMembersInArrears(dbManager);
        for (Member member : membersInArrears) {
            lv_memView.getItems().add(member.toString());
        }
    }

    /**
     * Opens the invoice window for the selected member.
     */
    private void openInvoiceWindow() {
        String selectedItem = lv_memView.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            int memberId = extractMemberIdFromString(selectedItem);
            List<Invoice> invoices = dbManager.getInvoicesForMember(memberId);
            if (!invoices.isEmpty()) {
                Invoice invoice = invoices.get(0); // Assuming you want the first invoice for now
                try {
                    FXMLLoader loader = new FXMLLoader(Main.class.getResource("/swimapp/frontend/InvoiceWindow.fxml"));
                    Stage stage = new Stage();
                    stage.setScene(new Scene(loader.load()));
                    InvoiceWindowController controller = loader.getController();
                    controller.setInvoice(invoice);
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Extracts the member ID from the given member string.
     *
     * @param memberString the string containing member information
     * @return the member ID
     */
    private int extractMemberIdFromString(String memberString) {
        // Assuming the string format is: "Member ID: 1 | Name: John Doe | Gender: MALE | Birthday: 1995-05-05 | Age: 29 | Membership: Adult"
        String[] parts = memberString.split("\\|");
        for (String part : parts) {
            part = part.trim();
            if (part.startsWith("Member ID:")) {
                String[] idPart = part.split(":");
                if (idPart.length == 2) {
                    try {
                        return Integer.parseInt(idPart[1].trim());
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        throw new IllegalArgumentException("Invalid member string format: " + memberString);
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
     * Opens the window to issue a new invoice.
     *
     * @throws IOException if an I/O error occurs
     */
    private void openIssueInvoiceWindow() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/swimapp/frontend/IssueInvoiceWindow.fxml"));
        Parent root = loader.load();

        // Get the controller and set the member
        IssueInvoiceWindowController controller = loader.getController();

        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Issue Invoice");
        stage.setScene(new Scene(root));
        stage.show();
    }
}
