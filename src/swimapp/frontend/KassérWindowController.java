package swimapp.frontend;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import swimapp.backend.Payments;
import swimapp.backend.DatabaseManager;
import swimapp.backend.Member;
import swimapp.backend.Invoice;
import swimapp.frontend.Main;

import java.io.IOException;
import java.util.List;

public class KassérWindowController {

    @FXML
    private Button btn_trsMemShow;

    @FXML
    private Button btn_trsArrShow;

    @FXML
    private Button btn_trsPay;

    @FXML
    private Button btn_trnBack;

    @FXML
    private ListView<String> listView;

    private Payments payments;
    private DatabaseManager dbManager;

    @FXML
    public void initialize() {
        payments = new Payments();
        dbManager = new DatabaseManager(); // Assuming you have a default constructor

        btn_trsMemShow.setOnAction(event -> showMembers());
        btn_trsArrShow.setOnAction(event -> showArrears());
        btn_trsPay.setOnAction(event -> makePayment());
        btn_trnBack.setOnAction(event -> goBack());
    }

    private void showMembers() {
        listView.getItems().clear();
        List<Member> members = payments.getMembers();
        for (Member member : members) {
            listView.getItems().add(member.toString());
        }
    }

    private void showArrears() {
        listView.getItems().clear();
        List<Member> membersInArrears = payments.getMembersInArrears(dbManager);
        for (Member member : membersInArrears) {
            listView.getItems().add(member.toString());
        }
    }

    private void makePayment() {
        List<Invoice> arrears = payments.getArrears();
        if (!arrears.isEmpty()) {
            Invoice invoiceToPay = arrears.get(0);
            payments.markInvoiceAsPaid(invoiceToPay.getInvoiceID());
            listView.getItems().clear();
            listView.getItems().add("Paid Invoice ID: " + invoiceToPay.getInvoiceID());
        } else {
            listView.getItems().clear();
            listView.getItems().add("No arrears to pay.");
        }
    }

    private void goBack() {
        try {
            Main.showMainWindow();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

