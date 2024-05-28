package swimapp.frontend;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import swimapp.backend.GuiInterface;
import swimapp.backend.Invoice;
import swimapp.backend.Member;

import java.time.LocalDate;

public class IssueInvoiceWindowController {

    @FXML
    public Button btn_iiBack;

    @FXML
    public TextField tf_iiMemID;

    @FXML
    public Button btn_iiCreate;

    @FXML
    public void initialize() {
        btn_iiBack.setOnAction(actionEvent -> closeWindow());
        btn_iiCreate.setOnAction(actionEvent -> addInvoice());
    }

    private void addInvoice() {
        int memberID = Integer.parseInt(tf_iiMemID.getText());
        Member member = GuiInterface.getMemberByID(memberID);
        Invoice invoice = new Invoice(member.getMembershipType().getFee(), member.getMemberID(), LocalDate.now());
        invoice.registerInvoice(GuiInterface.getDbManager());
        closeWindow();
    }

    private void closeWindow() {
        // Code to close the window
        btn_iiBack.getScene().getWindow().hide();
    }
}
