package swimapp.backend;

/**
 * Represents a membership type in the swim application.
 */
public class MembershipType {
    private int typeID;
    private String description;
    private double fee;

    public static int JUNIOR = 1;
    public static int ADULT = 2;
    public static int SENIOR = 3;
    public static int PASSIVE = 0;

    /**
     * Constructs a MembershipType with the specified type ID, description, and fee.
     *
     * @param typeID the ID of the membership type
     * @param description the description of the membership type
     * @param fee the fee of the membership type
     */
    public MembershipType(int typeID, String description, double fee) {
        this.typeID = typeID;
        this.description = description;
        this.fee = fee;
    }

    /**
     * Gets the ID of the membership type.
     *
     * @return the ID of the membership type
     */
    public int getTypeID() {
        return typeID;
    }

    /**
     * Gets the fee of the membership type.
     *
     * @return the fee of the membership type
     */
    public double getFee() {
        return fee;
    }

    /**
     * Gets the description of the membership type.
     *
     * @return the description of the membership type
     */
    public String getDescription() {
        return description;
    }

    /**
     * Adds a new membership type to the database.
     */
    public void addMemberShipType() {
        // Implementation for adding membership type
    }

    /**
     * Updates the fee of the membership type in the database.
     *
     * @param amount the new fee amount
     * @param dbManager the database manager to handle database operations
     */
    public void updateFee(double amount, DatabaseManager dbManager) {
        this.fee = amount;
        dbManager.updateMembershipFee(this.typeID, amount);
    }

    /**
     * Returns a string representation of the membership type.
     *
     * @return the description of the membership type
     */
    @Override
    public String toString() {
        return description;
    }
}
