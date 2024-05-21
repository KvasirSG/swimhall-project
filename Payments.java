import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Payments {
    private int juniorFee;
    private int seniorFee;
    private double seniorDiscount;
    private int passiveFee;

    private List<Integer> memberIDs;
    private List<Integer> memberPayments;
    private List<Integer> memberArrears;
    private List<Date> memberCreationDates;
    private List<Invoice> invoices;

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

    public void makePayment(int memberID, Date birthday) {
        if (!memberIDs.contains(memberID)) {
            System.out.println("Member does not exist.");
            return;
        }

        int memberIndex = memberIDs.indexOf(memberID);

        int fee;
        if (isPassiveMember(birthday)) {
            fee = passiveFee;
        } else {
            fee = calculateFee(birthday);
        }

        int totalPayment = memberPayments.get(memberIndex);
        memberPayments.set(memberIndex, totalPayment + fee);

        Invoice invoice = new Invoice(fee, memberID);
        invoices.add(invoice);

    }

    public int checkArrears(int memberID) {
        if (!memberIDs.contains(memberID)) {
            return 0;
        }
        return memberArrears.get(memberIDs.indexOf(memberID));
    }

    public void updateArrears(int memberID, int amount) {
        if (!memberIDs.contains(memberID)) {
            System.out.println("Member does not exist.");
            return;
        }
        int totalArrears = memberArrears.get(memberIDs.indexOf(memberID));
        memberArrears.set(memberIDs.indexOf(memberID), totalArrears + amount);
    }

    private int calculateFee(Date birthday) {
        return 0;
    }

    private int calculateAge(Date birthday) {
        return 0;
    }

    private boolean isPassiveMember(Date birthday) {
        return true;
    }

    public void setJuniorFee(int juniorFee) {
        this.juniorFee = juniorFee;
    }

    public void setSeniorFee(int seniorFee) {
        this.seniorFee = seniorFee;
    }

    public void setSeniorDiscount(double seniorDiscount) {
        this.seniorDiscount = seniorDiscount;
    }

    public void setPassiveFee(int passiveFee) {
        this.passiveFee = passiveFee;
    }

    public void setMemberCreationDate(int memberID, Date creationDate) {
        memberIDs.add(memberID);
        memberCreationDates.add(creationDate);
        memberPayments.add(0);
        memberArrears.add(0);
    }

    public List<Invoice> getInvoices() {
        return invoices;
    }

    public void markInvoiceAsPaid(long invoiceID) {
        for (Invoice invoice : invoices) {
            if (invoice.getInvoiceID() == invoiceID) {
                invoice.setPaid(true);
                return;
            }
        }
    }

    public List<Invoice> getUnpaidInvoices() {
        List<Invoice> unpaidInvoices = new ArrayList<>();
        for (Invoice invoice : invoices) {
            if (!invoice.isPaid()) {
                unpaidInvoices.add(invoice);
            }
        }
        return unpaidInvoices;
    }
}