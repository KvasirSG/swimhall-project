package swimapp.backend;

import java.time.LocalDate;
import java.time.Period;

/**
 * Represents a member in the swim application.
 */
public class Member {
    private int memberID;
    private String name;
    private LocalDate birthday;
    private int age;
    private int membershipTypeID;
    private MembershipType membershipType;
    private Gender gender;

    /**
     * Constructs a Member with the specified name, gender, birthday, and passive status.
     *
     * @param name the name of the member
     * @param gender the gender of the member
     * @param birthday the birthday of the member
     * @param isPassive the passive status of the member
     */
    public Member(String name, Gender gender, LocalDate birthday, boolean isPassive) {
        this.name = name;
        this.gender = gender;
        this.birthday = birthday;
        this.age = calculateAge();
        this.membershipTypeID = calculateMembershipTypeID(isPassive);
        this.membershipType = calculateMembershipType();
    }

    /**
     * Constructs a Member with the specified ID, name, gender, birthday, and membership type.
     *
     * @param memberID the ID of the member
     * @param name the name of the member
     * @param gender the gender of the member
     * @param birthday the birthday of the member
     * @param membershipType the membership type of the member
     */
    public Member(int memberID, String name, Gender gender, LocalDate birthday, MembershipType membershipType) {
        this.memberID = memberID;
        this.name = name;
        this.gender = gender;
        this.birthday = birthday;
        this.age = calculateAge();
        this.membershipType = membershipType;
        this.membershipTypeID = membershipType.getTypeID();
    }

    /**
     * Gets the ID of the member.
     *
     * @return the ID of the member
     */
    public int getMemberID() {
        return memberID;
    }

    /**
     * Sets the ID of the member.
     *
     * @param memberID the new ID of the member
     */
    public void setMemberID(int memberID) {
        this.memberID = memberID;
    }

    /**
     * Gets the name of the member.
     *
     * @return the name of the member
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the member.
     *
     * @param name the new name of the member
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the birthday of the member.
     *
     * @return the birthday of the member
     */
    public LocalDate getBirthday() {
        return birthday;
    }

    /**
     * Sets the birthday of the member.
     *
     * @param birthday the new birthday of the member
     */
    public void setDate(LocalDate birthday) {
        this.birthday = birthday;
    }

    /**
     * Gets the age of the member.
     *
     * @return the age of the member
     */
    public int getAge() {
        return age;
    }

    /**
     * Calculates the age of the member based on the current date.
     *
     * @return the age of the member
     */
    private int calculateAge() {
        LocalDate today = LocalDate.now();
        Period age = Period.between(birthday, today);
        return age.getYears();
    }

    /**
     * Calculates the membership type ID based on the age and passive status of the member.
     *
     * @param isPassive the passive status of the member
     * @return the membership type ID
     */
    private int calculateMembershipTypeID(boolean isPassive) {
        if (!isPassive) {
            if (age >= 60) {
                return MembershipType.SENIOR;
            } else if (age >= 18) {
                return MembershipType.ADULT;
            } else {
                return MembershipType.JUNIOR;
            }
        } else {
            return MembershipType.PASSIVE;
        }
    }

    /**
     * Calculates the membership type based on the membership type ID.
     *
     * @return the membership type
     */
    private MembershipType calculateMembershipType() {
        DatabaseManager dbm = new DatabaseManager();
        MembershipType temp = dbm.getMembershipType(membershipTypeID);
        dbm.closeConnection();
        return temp;
    }

    /**
     * Gets the membership type of the member.
     *
     * @return the membership type of the member
     */
    public MembershipType getMembershipType() {
        return this.membershipType;
    }

    /**
     * Gets the membership type ID of the member.
     *
     * @return the membership type ID of the member
     */
    public int getMembershipTypeID() {
        return this.membershipTypeID;
    }

    /**
     * Gets the birth date of the member.
     *
     * @return the birth date of the member
     */
    public LocalDate getBirthDate() {
        return this.birthday;
    }

    /**
     * Registers the member in the database.
     *
     * @param dbManager the database manager to handle database operations
     */
    public void registerMember(DatabaseManager dbManager) {
        dbManager.addMember(this);
    }

    /**
     * Deletes the member from the database.
     *
     * @param dbManager the database manager to handle database operations
     */
    public void delete(DatabaseManager dbManager) {
        dbManager.deleteMember(this.memberID);
    }

    /**
     * Updates the member details in the database.
     *
     * @param dbManager the database manager to handle database operations
     */
    public void update(DatabaseManager dbManager) {
        dbManager.updateMember(this);
        dbManager.closeConnection();
    }

    /**
     * Gets the gender of the member.
     *
     * @return the gender of the member
     */
    public Gender getGender() {
        return gender;
    }

    /**
     * Returns a string representation of the member.
     *
     * @return a string representation of the member
     */
    @Override
    public String toString() {
        return String.format("Member ID: %d | Name: %s | Gender: %s | Birthday: %s | Age: %d | Membership: %s",
                memberID, name, gender, birthday, age, (membershipType != null ? membershipType.getDescription() : "None"));
    }

    /**
     * Sets the membership type ID of the member and updates the membership type.
     *
     * @param membershipTypeID the new membership type ID
     */
    public void setMembershipTypeID(int membershipTypeID) {
        this.membershipTypeID = membershipTypeID;
        // Update the membership type based on the ID
        DatabaseManager dbManager = new DatabaseManager();
        this.membershipType = dbManager.getMembershipType(membershipTypeID);
        dbManager.closeConnection();
    }
}
