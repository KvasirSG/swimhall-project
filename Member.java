import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.Date;

public class Member {
    private int memberID;
    private String name;
    private LocalDate birthday;
    private int age;
    private boolean isActive;
    private MembershipType membershipType;
    private boolean isCompetitive;

    public Member(int memberID, String name, LocalDate birthday, boolean isActive, boolean isCompetitive)
    {
        this.memberID = memberID;
        this.name = name;
        this.isActive = isActive;
        this.birthday = birthday;
        this.age = calculateAge();
        this.isCompetitive = isCompetitive;
        this.membershipType = calculateMembershipType();
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

    /**
     * gets the membership status.
     * <p>
     * returns the active or passive memberstatus.
     */
    public boolean getIsActive() {
        return isActive;
    }

    /**
     * Sets the if a member is active.
     *
     * @param isActive sets membership status for members.
     */
    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    private int calculateAge() {
        LocalDate today = LocalDate.now();
        Period age = Period.between(birthday, today);
        return age.getYears();

    }
    private MembershipType calculateMembershipType()
    {
        if(age >= 60)
        {
            return MembershipType.SUPERSENIOR;
        }
            else if (age >= 18)
            {
                return MembershipType.SENIOR;
            }
        else
        {
            return MembershipType.JUNIOR;
        }
    }
    public MembershipType getMembershipType()
    {
        return membershipType;
    }
}
