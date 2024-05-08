import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * The Payments class handles payments and arrears for members in a system.
 */
public class Payments {
    private int juniorFee; // Fee for junior members
    private int seniorFee; // Fee for senior members
    private double seniorDiscount; // Discount percentage for senior members
    private int passiveFee; // Fixed fee for passive members

    private List<Integer> memberIDs; // List to store member IDs
    private List<Integer> memberPayments; // List to store payments for members
    private List<Integer> memberArrears;  // List to store arrears for members
    private List<Date> memberCreationDates; // List to store creation dates for members
    private List<Invoice> invoices; // List to store invoices

    /**
     * Constructor to initialize Payments object with fee parameters.
     * @param juniorFee Fee for junior members
     * @param seniorFee Fee for senior members
     * @param seniorDiscount Discount percentage for senior members
     * @param passiveFee Fixed fee for passive members
     */
    public Payments(int juniorFee, int seniorFee, double seniorDiscount, int passiveFee) {
        this.juniorFee = juniorFee;
        this.seniorFee = seniorFee;
        this.seniorDiscount = seniorDiscount;
        this.passiveFee = passiveFee;

        memberIDs = new ArrayList<>();
        memberPayments = new ArrayList<>();
        memberArrears = new ArrayList<>();
        memberCreationDates = new ArrayList<>();
        invoices = new ArrayList<>();
    }

    /**
     * Makes a payment for a member.
     * @param memberID ID of the member
     * @param birthday Birthday of the member
     */
    public void makePayment(int memberID, Date birthday) {
        // Check if member exists
        if (!memberIDs.contains(memberID)) {
            System.out.println("Member does not exist.");
            return;
        }

        // Get member index
        int memberIndex = memberIDs.indexOf(memberID);

        // Calculate fee
        int fee;
        if (isPassiveMember(birthday)) {
            fee = passiveFee;
        } else {
            fee = calculateFee(birthday);
        }

        // Update member payment
        int totalPayment = memberPayments.get(memberIndex);
        memberPayments.set(memberIndex, totalPayment + fee);

        // Create invoice
        long invoiceID = generateInvoiceID();
        Invoice invoice = new Invoice(fee, invoiceID, memberID);
        invoices.add(invoice);

        System.out.println("Payment of " + fee + " kr has been made successfully for member ID: " + memberID +
                ". Invoice ID: " + invoiceID);
    }

    /**
     * Checks the arrears for a member.
     * @param memberID ID of the member
     * @return The arrears amount for the member
     */
    public int checkArrears(int memberID) {
        if (!memberIDs.contains(memberID)) {
            return 0;
        }
        return memberArrears.get(memberIDs.indexOf(memberID));
    }

    /**
     * Updates the arrears for a member.
     * @param memberID ID of the member
     * @param amount The amount to update the arrears by
     */
    public void updateArrears(int memberID, int amount) {
        if (!memberIDs.contains(memberID)) {
            System.out.println("Member does not exist.");
            return;
        }
        int totalArrears = memberArrears.get(memberIDs.indexOf(memberID));
        memberArrears.set(memberIDs.indexOf(memberID), totalArrears + amount);
    }

    /**
     * Calculates the fee for a member based on their birthday.
     * @param birthday Birthday of the member
     * @return The calculated membership fee
     */
    private int calculateFee(Date birthday) {
        // Implementation of calculateFee method
        return 0;
    }

    /**
     * Calculates the age of the member based on their birthday.
     * @param birthday Birthday of the member
     * @return The age of the member
     */
    private int calculateAge(Date birthday) {
        // Implementation of calculateAge method
        return 0;
    }

    /**
     * Checks if a member is a passive member.
     * @param birthday Birthday of the member
     * @return True if the member is passive, false otherwise
     */
    private boolean isPassiveMember(Date birthday) {
        // Implementation of isPassiveMember method
        return true;
    }

    /**
     * Sets the fee for junior members.
     * @param juniorFee The fee for junior members
     */
    public void setJuniorFee(int juniorFee) {
        this.juniorFee = juniorFee;
    }

    /**
     * Sets the fee for senior members.
     * @param seniorFee The fee for senior members
     */
    public void setSeniorFee(int seniorFee) {
        this.seniorFee = seniorFee;
    }

    /**
     * Sets the discount for senior members.
     * @param seniorDiscount The discount for senior members
     */
    public void setSeniorDiscount(double seniorDiscount) {
        this.seniorDiscount = seniorDiscount;
    }

    /**
     * Sets the fee for passive members.
     * @param passiveFee The fee for passive members
     */
    public void setPassiveFee(int passiveFee) {
        this.passiveFee = passiveFee;
    }

    /**
     * Sets the creation date for a member.
     * @param memberID ID of the member
     * @param creationDate The creation date of the member
     */
    public void setMemberCreationDate(int memberID, Date creationDate) {
        memberIDs.add(memberID);
        memberCreationDates.add(creationDate);
        memberPayments.add(0);
        memberArrears.add(0);
    }

    /**
     * Gets the list of invoices.
     * @return The list of invoices
     */
    public List<Invoice> getInvoices() {
        return invoices;
    }

    /**
     * Marks an invoice as paid.
     * @param invoiceID The ID of the invoice to mark as paid
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
     * Gets the list of unpaid invoices.
     * @return The list of unpaid invoices
     */
    public List<Invoice> getUnpaidInvoices() {
        List<Invoice> unpaidInvoices = new ArrayList<>();
        for (Invoice invoice : invoices) {
            if (!invoice.isPaid()) {
                unpaidInvoices.add(invoice);
            }
        }
        return unpaidInvoices;
    }

    /**
     * Generates a unique invoice ID.
     * @return The unique invoice ID
     */
    private long generateInvoiceID() {
        // Generate a unique invoice ID, you can implement your logic here
        return System.currentTimeMillis();
    }
}
