import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Invoice {

    private double amount;
    private long invoiceID;
    private int memberID;
    private boolean paid; // New attribute to track whether the invoice is paid
    private LocalDate dueDate;

    public Invoice(double amount, long invoiceID, int memberID, boolean isPaid) {
        this.amount = amount;
        this.invoiceID = invoiceID;
        this.memberID = memberID;
        this.paid = isPaid;
    }
    public Invoice(double amount, int memberID) {
        this.amount = amount;
        this.invoiceID = 0;
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
        if (invoiceID <= 0) {
            this.invoiceID = getInvoiceID();
        } else {
            this.invoiceID = invoiceID;
        }
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate){
        this.dueDate = dueDate;
    }
}
