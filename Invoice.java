import java.util.ArrayList;
import java.util.List;

public class Invoice {

    private int amount;
    private long invoiceID;
    private int memberID;
    private boolean paid; // New attribute to track whether the invoice is paid

    public Invoice(int amount, long invoiceID, int memberID) {
        this.amount = amount;
        this.invoiceID = invoiceID;
        this.memberID = memberID;
        this.paid = false; // Initialize paid status as false
    }

    public int getMemberID() {
        return memberID;
    }

    public void setMemberID(int memberID) {
        this.memberID = memberID;
    }

    public long getInvoiceID() {
        return invoiceID;
    }

    public void setInvoiceID(long invoiceID) {
        this.invoiceID = invoiceID;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }
}
