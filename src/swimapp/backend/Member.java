package swimapp.backend;

import java.time.LocalDate;
import java.time.Period;

public class Member {
    private int memberID;
    private String name;
    private LocalDate birthday;
    private int age;
    private int membershipTypeID;
    private MembershipType membershipType;

    private Gender gender;

    public Member(String name, Gender gender , LocalDate birthday, boolean isPassive)
    {
        this.name = name;
        this.gender = gender;
        this.birthday = birthday;
        this.age = calculateAge();
        this.membershipTypeID = calculateMembershipTypeID(isPassive);

    }

    public Member(int memberID, String name, Gender gender, LocalDate birthday,MembershipType membershipType){
        this.memberID = memberID;
        this.name = name;
        this.gender = gender;
        this.birthday = birthday;
        this.age = calculateAge();
        this.membershipType = membershipType;
        this.membershipTypeID = membershipType.getTypeID();
    }
    /**
     * Gets the members's ID .
     *
     * @return The members unique identifier.
     */
    public int getMemberID() {
        return memberID;
    }

    /**
     * Sets the ID of the member.
     *
     * @param memberID The new ID for the member.
     */
    public void setMemberID(int memberID) {
        this.memberID = memberID;
    }

    /**
     * Gets the members's name .
     *
     * @return The members unique name.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the member.
     *
     * @param name The new name for the member.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * gets the  birthday of the member.
     *
     * @return birthday used to decide membership.
     */
    public LocalDate getBirthday() {
        return birthday;
    }

    /**
     * Sets the birthday of the member.
     *
     * @param birthday sets date for members birthday.
     */
    public void setDate(LocalDate birthday) {
        this.birthday = birthday;
    }

    /**
     * gets the  age of the member.
     * <p>
     * returns the age of the member.
     */
    public int getAge() {
        return age;
    }

    private int calculateAge() {
        LocalDate today = LocalDate.now();
        Period age = Period.between(birthday, today);
        return age.getYears();

    }
    private int calculateMembershipTypeID(boolean isPassive)
    {
        if (!isPassive) {
            if (age >= 60) {
                return MembershipType.SENIOR;
            } else if (age >= 18) {
                return MembershipType.ADULT;
            } else {
                return MembershipType.JUNIOR;
            }
        }else {return MembershipType.PASSIVE;}

    }
    private MembershipType calculateMembershipType(){
        DatabaseManager dbm = new DatabaseManager();
        MembershipType temp = dbm.getMembershipType(membershipTypeID);
        dbm.closeConnection();
        return temp;
    }
    public MembershipType getMembershipType()
    {
        return this.membershipType;
    }

    public int getMembershipTypeID() {
        return this.membershipTypeID;
    }

    public LocalDate getBirthDate() {
        return this.birthday;
    }
    public void registerMember(DatabaseManager dbManager) {
        dbManager.addMember(this);
    }
    public void delete(DatabaseManager dbManager){
        dbManager.deleteMember(this.memberID);
    }

    public void update(DatabaseManager dbManager){
        dbManager.updateMember(this);
        dbManager.closeConnection();
    }

    public Gender getGender() {
        return gender;
    }

    public String toString() {
        return String.format("Member ID: %d | Name: %s | Gender: %s | Birthday: %s | Age: %d | Membership: %s",
                memberID, name, gender, birthday, age, (membershipType != null ? membershipType.getDescription() : "None"));
    }

    public void setMembershipTypeID(int membershipTypeID) {
        this.membershipTypeID = membershipTypeID;
        // Update the membershipType based on the ID (optional, depending on your application logic)
        DatabaseManager dbManager = new DatabaseManager();
        this.membershipType = dbManager.getMembershipType(membershipTypeID);
        dbManager.closeConnection();
    }
}


