package swimapp.frontend;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import swimapp.backend.DatabaseManager;
import swimapp.backend.Invoice;

public class InvoiceWindowController {

    @FXML
    private Button btn_invBack;

    @FXML
    private Label lbl_invName;

    @FXML
    private Label lbl_invAmount;

    @FXML
    private Label lbl_invNumber;

    @FXML
    private Label lbl_issueDate;

    @FXML
    private Button btn_makePayment;

    private DatabaseManager dbManager;
    private Invoice invoice;

    @FXML
    public void initialize() {
        dbManager = new DatabaseManager();

        btn_invBack.setOnAction(event -> closeWindow());
        btn_makePayment.setOnAction(event -> makePayment());
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
        lbl_invName.setText("Member ID: " + invoice.getMemberID()); // Adjust this line to show member name if needed
        lbl_invAmount.setText(String.valueOf(invoice.getAmount()));
        lbl_invNumber.setText(String.valueOf(invoice.getInvoiceID()));
        lbl_issueDate.setText(String.valueOf(invoice.getDueDate()));
    }

    private void makePayment() {
        if (invoice != null) {
            invoice.setPaid(true);
            dbManager.markInvoiceAsPaid(invoice.getInvoiceID());
            lbl_invAmount.setText("Paid");
        }
    }

    private void closeWindow() {
        btn_invBack.getScene().getWindow().hide();
    }
}
