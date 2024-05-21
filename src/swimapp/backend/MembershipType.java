package swimapp.backend;

public class MembershipType {
    private int typeID;
    private  String description;
    private double fee;

    public static int JUNIOR = 1;
    public static int ADULT = 2;
    public static int SENIOR = 3;
    public static int PASSIVE = 0;
    public MembershipType(int typeID, String description, double fee){
        this.typeID=typeID;
        this.description=description;
        this.fee=fee;
    }

    public int getTypeID() {
        return typeID;
    }

    public double getFee() {
        return fee;
    }

    public String getDescription() {
        return description;
    }

    public void addMemberShipType(){

    }
    public void updateFee(double amount, DatabaseManager dbManager){
        this.fee = amount;
        dbManager.updateMembershipFee(this.typeID,amount);
    }
}
