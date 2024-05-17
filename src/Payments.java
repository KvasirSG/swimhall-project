import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Payments {
    private List<Member> members;
    private List<Invoice> invoices;
    private List<Invoice> arrears;

    public Payments() {
        members = new ArrayList<>();
        invoices = new ArrayList<>();
        arrears = new ArrayList<>();
    }

    public void makePayment(Invoice invoice, DatabaseManager dbManager) {
        invoice.setPaid(true);
        dbManager.markInvoiceAsPaid(invoice.getInvoiceID());

    }

    public void createInvoice(int memberID, double amount){
        Invoice invoice = new Invoice(amount, memberID, LocalDate.now().plusDays(30));
    }

    public List<Member> getMembersInArrears(DatabaseManager dbManager){
        List<Member> memberList = new ArrayList<>();
        for (Invoice invoice:invoices){
            if (!invoice.isPaid() && invoice.getDueDate().isBefore(LocalDate.now())){
                Member member = dbManager.getMember(invoice.getMemberID());
                if (member!=null){
                    memberList.add(member);
                }
            }
        }
        return memberList;
    }

    public void getInvoicesInArrears(){
        for (Invoice invoice:invoices){
            if (!invoice.isPaid() && invoice.getDueDate().isBefore(LocalDate.now())){
                arrears.add(invoice);
                
            }
        }
    }

    private double calculateFee(Date birthday, MembershipType membershipType) {
        // Implement fee calculation logic based on membership type and age if needed
        // For simplicity, let's assume the fee is directly fetched from the membership type
        return membershipType.getFee();
    }

    private long generateInvoiceID() {
        // Implement logic to generate a unique invoice ID
        return System.currentTimeMillis();
    }

    public List<Member> getMembers() {
        return members;
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

        public List<Invoice> getArrears() {
            List<Invoice> arrears = new ArrayList<>();
            for (Invoice invoice : invoices) {
                if (!invoice.isPaid()) {
                    arrears.add(invoice);
                }
            }
            return arrears;
        }
    public void setMembers(List<Member> members) {
        this.members = members;
    }

    public void setInvoices(List<Invoice> invoices) {
        this.invoices = invoices;
    }

    public void fetchMembersFromDB(DatabaseManager dbManager){
        this.members = dbManager.getAllMembers();
    }

    public void fetchInvoicesForMembers(DatabaseManager dbManager){
        for (Member member:members){
            List<Invoice> tempvoices = dbManager.getInvoicesForMember(member.getMemberID());
            invoices.addAll(tempvoices);
        }
    }
}
