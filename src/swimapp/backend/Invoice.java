package swimapp.backend;

import java.time.LocalDate;

/**
 * Represents an invoice in the swim application.
 */
public class Invoice {

    private double amount;
    private long invoiceID;
    private int memberID;
    private boolean paid; // New attribute to track whether the invoice is paid
    private LocalDate dueDate;

    /**
     * Constructs an Invoice with the specified amount, invoice ID, member ID, paid status, and due date.
     *
     * @param amount the amount of the invoice
     * @param invoiceID the ID of the invoice
     * @param memberID the ID of the member associated with the invoice
     * @param isPaid the paid status of the invoice
     * @param dueDate the due date of the invoice
     */
    public Invoice(double amount, long invoiceID, int memberID, boolean isPaid, LocalDate dueDate) {
        this.amount = amount;
        this.invoiceID = invoiceID;
        this.memberID = memberID;
        this.paid = isPaid;
        this.dueDate = dueDate;
    }

    /**
     * Constructs an Invoice with the specified amount, member ID, and due date.
     *
     * @param amount the amount of the invoice
     * @param memberID the ID of the member associated with the invoice
     * @param dueDate the due date of the invoice
     */
    public Invoice(double amount, int memberID, LocalDate dueDate) {
        this.amount = amount;
        this.invoiceID = 0;
        this.memberID = memberID;
        this.paid = false; // Initialize paid status as false
        this.dueDate = dueDate;
    }

    /**
     * Gets the ID of the member associated with the invoice.
     *
     * @return the ID of the member
     */
    public int getMemberID() {
        return memberID;
    }

    /**
     * Sets the ID of the member associated with the invoice.
     *
     * @param memberID the new ID of the member
     */
    public void setMemberID(int memberID) {
        this.memberID = memberID;
    }

    /**
     * Gets the ID of the invoice.
     *
     * @return the ID of the invoice
     */
    public long getInvoiceID() {
        return invoiceID;
    }

    /**
     * Sets the ID of the invoice.
     *
     * @param invoiceID the new ID of the invoice
     */
    public void setInvoiceID(long invoiceID) {
        if (invoiceID <= 0) {
            this.invoiceID = getInvoiceID();
        } else {
            this.invoiceID = invoiceID;
        }
    }

    /**
     * Gets the amount of the invoice.
     *
     * @return the amount of the invoice
     */
    public double getAmount() {
        return amount;
    }

    /**
     * Sets the amount of the invoice.
     *
     * @param amount the new amount of the invoice
     */
    public void setAmount(double amount) {
        this.amount = amount;
    }

    /**
     * Gets the paid status of the invoice.
     *
     * @return true if the invoice is paid, false otherwise
     */
    public boolean isPaid() {
        return paid;
    }

    /**
     * Sets the paid status of the invoice.
     *
     * @param paid the new paid status of the invoice
     */
    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    /**
     * Gets the due date of the invoice.
     *
     * @return the due date of the invoice
     */
    public LocalDate getDueDate() {
        return dueDate;
    }

    /**
     * Sets the due date of the invoice.
     *
     * @param dueDate the new due date of the invoice
     */
    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    /**
     * Registers the invoice in the database.
     *
     * @param dbManager the database manager to handle database operations
     */
    public void registerInvoice(DatabaseManager dbManager) {
        dbManager.addInvoice(this);
        dbManager.closeConnection();
    }
}
