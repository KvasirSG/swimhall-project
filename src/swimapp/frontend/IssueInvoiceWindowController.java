package swimapp.frontend;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import swimapp.backend.GuiInterface;
import swimapp.backend.Invoice;
import swimapp.backend.Member;

import java.time.LocalDate;

/**
 * Controller class for the Issue Invoice window in the swim application.
 * Handles UI interactions and updates the invoice creation functionalities.
 */
public class IssueInvoiceWindowController {

    @FXML
    public Button btn_iiBack;

    @FXML
    public TextField tf_iiMemID;

    @FXML
    public Button btn_iiCreate;

    /**
     * Initializes the controller class. Sets up event handlers for the buttons.
     */
    @FXML
    public void initialize() {
        btn_iiBack.setOnAction(actionEvent -> closeWindow());
        btn_iiCreate.setOnAction(actionEvent -> addInvoice());
    }

    /**
     * Adds a new invoice for the member based on the member ID input.
     */
    private void addInvoice() {
        int memberID = Integer.parseInt(tf_iiMemID.getText());
        Member member = GuiInterface.getMemberByID(memberID);
        Invoice invoice = new Invoice(member.getMembershipType().getFee(), member.getMemberID(), LocalDate.now());
        invoice.registerInvoice(GuiInterface.getDbManager());
        closeWindow();
    }

    /**
     * Closes the invoice window.
     */
    private void closeWindow() {
        btn_iiBack.getScene().getWindow().hide();
    }
}
