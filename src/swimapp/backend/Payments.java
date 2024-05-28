package swimapp.backend;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Manages payment operations including creating invoices, marking payments, and tracking arrears.
 */
public class Payments {
    private List<Member> members;
    private List<Invoice> invoices;
    private List<Invoice> arrears;

    /**
     * Constructs a Payments object with empty member, invoice, and arrears lists.
     */
    public Payments() {
        members = new ArrayList<>();
        invoices = new ArrayList<>();
        arrears = new ArrayList<>();
    }

    /**
     * Marks an invoice as paid and updates the database.
     *
     * @param invoice the invoice to mark as paid
     * @param dbManager the database manager to handle database operations
     */
    public void makePayment(Invoice invoice, DatabaseManager dbManager) {
        invoice.setPaid(true);
        dbManager.markInvoiceAsPaid(invoice.getInvoiceID());
    }

    /**
     * Creates an invoice for a member with the specified amount and due date 30 days from now.
     *
     * @param memberID the ID of the member to create the invoice for
     * @param amount the amount of the invoice
     */
    public void createInvoice(int memberID, double amount) {
        Invoice invoice = new Invoice(amount, memberID, LocalDate.now().plusDays(30));
        invoices.add(invoice); // Added to the list of invoices
    }

    /**
     * Retrieves a list of members who are in arrears.
     *
     * @param dbManager the database manager to handle database operations
     * @return a list of members who have unpaid invoices past their due date
     */
    public List<Member> getMembersInArrears(DatabaseManager dbManager) {
        List<Member> memberList = new ArrayList<>();
        for (Invoice invoice : invoices) {
            if (!invoice.isPaid() && invoice.getDueDate().isBefore(LocalDate.now())) {
                Member member = dbManager.getMember(invoice.getMemberID());
                if (member != null) {
                    memberList.add(member);
                }
            }
        }
        return memberList;
    }

    /**
     * Populates the arrears list with invoices that are unpaid and past their due date.
     */
    public void getInvoicesInArrears() {
        arrears.clear();
        for (Invoice invoice : invoices) {
            if (!invoice.isPaid() && invoice.getDueDate().isBefore(LocalDate.now())) {
                arrears.add(invoice);
            }
        }
    }

    /**
     * Calculates the fee for a membership type based on the member's birthday.
     *
     * @param birthday the birthday of the member
     * @param membershipType the membership type
     * @return the calculated fee
     */
    private double calculateFee(Date birthday, MembershipType membershipType) {
        // Implement fee calculation logic based on membership type and age if needed
        // For simplicity, let's assume the fee is directly fetched from the membership type
        return membershipType.getFee();
    }

    /**
     * Generates a unique invoice ID.
     *
     * @return a unique invoice ID
     */
    private long generateInvoiceID() {
        // Implement logic to generate a unique invoice ID
        return System.currentTimeMillis();
    }

    /**
     * Gets the list of members.
     *
     * @return the list of members
     */
    public List<Member> getMembers() {
        return members;
    }

    /**
     * Gets the list of invoices.
     *
     * @return the list of invoices
     */
    public List<Invoice> getInvoices() {
        return invoices;
    }

    /**
     * Marks an invoice as paid based on the invoice ID.
     *
     * @param invoiceID the ID of the invoice to mark as paid
     */
    public void markInvoiceAsPaid(long invoiceID) {
        for (Invoice invoice : invoices) {
            if (invoice.getInvoiceID() == invoiceID) {
                invoice.setPaid(true);
                return;
            }
        }
    }

    /**
     * Gets the list of invoices in arrears.
     *
     * @return the list of invoices in arrears
     */
    public List<Invoice> getArrears() {
        List<Invoice> arrears = new ArrayList<>();
        for (Invoice invoice : invoices) {
            if (!invoice.isPaid()) {
                arrears.add(invoice);
            }
        }
        return arrears;
    }

    /**
     * Sets the list of members.
     *
     * @param members the list of members to set
     */
    public void setMembers(List<Member> members) {
        this.members = members;
    }

    /**
     * Sets the list of invoices.
     *
     * @param invoices the list of invoices to set
     */
    public void setInvoices(List<Invoice> invoices) {
        this.invoices = invoices;
    }

    /**
     * Fetches the list of members from the database.
     *
     * @param dbManager the database manager to handle database operations
     */
    public void fetchMembersFromDB(DatabaseManager dbManager) {
        this.members = dbManager.getAllMembers();
    }

    /**
     * Fetches the invoices for all members from the database.
     *
     * @param dbManager the database manager to handle database operations
     */
    public void fetchInvoicesForMembers(DatabaseManager dbManager) {
        invoices.clear();
        for (Member member : members) {
            List<Invoice> tempvoices = dbManager.getInvoicesForMember(member.getMemberID());
            invoices.addAll(tempvoices);
        }
    }
}
