package swimapp.backend;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Provides an interface for GUI operations to interact with the database.
 */
public class GuiInterface {

    /**
     * Retrieves all members of a specific membership type.
     *
     * @param membershipTypeID the ID of the membership type
     * @return a list of members with the specified membership type
     */
    public static List<Member> getMembersByType(int membershipTypeID) {
        List<Member> dbAllMembers = getAllMembers();
        List<Member> dbAllMembersType = new ArrayList<>();

        for (Member member : dbAllMembers) {
            if (member.getMembershipTypeID() == membershipTypeID) {
                dbAllMembersType.add(member);
            }
        }
        return dbAllMembersType;
    }

    /**
     * Retrieves all swimmers of a specific team.
     *
     * @param teamID the ID of the team
     * @return a list of swimmers in the specified team
     * @throws SQLException if a database access error occurs
     */
    public static List<Swimmer> getSwimmersByTeam(int teamID) throws SQLException {
        DatabaseManager db = new DatabaseManager();
        List<Swimmer> swimmers = db.getSwimmersByTeam(teamID);
        db.closeConnection();
        return swimmers;
    }

    /**
     * Retrieves all swimmers of a specific team and gender.
     *
     * @param teamID the ID of the team
     * @param gender the gender of the swimmers
     * @return a list of swimmers in the specified team and gender
     */
    public static List<Swimmer> getSwimmersByTeamAndGender(int teamID, Gender gender) {
        DatabaseManager db = new DatabaseManager();
        List<Swimmer> swimmers = db.getSwimmersByTeamAndGender(teamID, gender);
        db.closeConnection();
        return swimmers;
    }

    /**
     * Retrieves all disciplines for a specific swimmer.
     *
     * @param swimmerID the ID of the swimmer
     * @return a list of disciplines for the specified swimmer
     */
    public static List<Discipline> getDisciplinesForSwimmer(int swimmerID) {
        DatabaseManager db = new DatabaseManager();
        List<Discipline> disciplines = db.getDisciplinesForSwimmer(swimmerID);
        db.closeConnection();
        return disciplines;
    }

    /**
     * Adds a discipline to a swimmer.
     *
     * @param swimmer the swimmer to add the discipline to
     * @param discipline the discipline to add
     */
    public static void addSwimmerDiscipline(Swimmer swimmer, Discipline discipline) {
        DatabaseManager db = new DatabaseManager();
        swimmer.addDiscipline(db, discipline);
        db.closeConnection();
    }

    /**
     * Adds a performance record to the database.
     *
     * @param record the performance record to add
     */
    public static void addPerformanceRecord(Record record) {
        DatabaseManager db = new DatabaseManager();
        db.addPerformanceRecord(record);
        db.closeConnection();
    }

    /**
     * Retrieves all disciplines from the database.
     *
     * @return a list of all disciplines
     */
    public static List<Discipline> getAllDisciplines() {
        DatabaseManager db = new DatabaseManager();
        List<Discipline> disciplines = db.getDisciplines();
        db.closeConnection();
        return disciplines;
    }

    /**
     * Retrieves a new instance of the database manager.
     *
     * @return a new instance of the DatabaseManager
     */
    public static DatabaseManager getDbManager() {
        return new DatabaseManager();
    }

    /**
     * Retrieves a swimmer by their member ID.
     *
     * @param memberID the ID of the member
     * @return the swimmer with the specified member ID, or null if not found
     */
    public static Swimmer getSwimmerByMemberID(int memberID) {
        DatabaseManager db = new DatabaseManager();
        List<Swimmer> swimmers = db.getSwimmers();
        db.closeConnection();

        for (Swimmer swimmer : swimmers) {
            if (swimmer.getMemberID() == memberID) {
                return swimmer;
            }
        }
        return null;
    }

    /**
     * Retrieves a member by their ID.
     *
     * @param memberID the ID of the member
     * @return the member with the specified ID, or null if not found
     */
    public static Member getMemberByID(int memberID) {
        DatabaseManager db = new DatabaseManager();
        Member member = db.getMember(memberID);
        db.closeConnection();
        return member;
    }

    /**
     * Retrieves a discipline by its ID.
     *
     * @param disciplineID the ID of the discipline
     * @return the discipline with the specified ID, or null if not found
     */
    public Discipline getDisciplineByID(int disciplineID) {
        DatabaseManager db = new DatabaseManager();
        Discipline discipline = db.getDiscipline(disciplineID);
        db.closeConnection();
        return discipline;
    }

    /**
     * Retrieves all swimmers for a specific discipline.
     *
     * @param disciplineID the ID of the discipline
     * @return a list of swimmers for the specified discipline
     */
    public static List<Swimmer> getSwimmersByDiscipline(int disciplineID) {
        DatabaseManager db = new DatabaseManager();
        List<Swimmer> swimmers = db.getSwimmersByDiscipline(disciplineID);
        db.closeConnection();
        return swimmers;
    }

    /**
     * Retrieves all members from the database.
     *
     * @return a list of all members
     */
    public static List<Member> getAllMembers() {
        DatabaseManager db = new DatabaseManager();
        List<Member> dbAllMembers = db.getAllMembers();
        db.closeConnection();
        return dbAllMembers;
    }

    /**
     * Retrieves all members who are in arrears.
     *
     * @return a list of members in arrears
     */
    public static List<Member> getMembersInArrears() {
        DatabaseManager db = new DatabaseManager();
        Payments payments = new Payments();
        List<Member> dbAllMembers = payments.getMembersInArrears(db);
        db.closeConnection();
        return dbAllMembers;
    }

    /**
     * Adds a new member to the database.
     *
     * @param member the member to add
     */
    public static void addMember(Member member) {
        DatabaseManager db = new DatabaseManager();
        db.addMember(member);
        db.closeConnection();
    }

    /**
     * Retrieves all membership types from the database.
     *
     * @return a list of all membership types
     */
    public static List<MembershipType> getMembershipTypes() {
        DatabaseManager db = new DatabaseManager();
        List<MembershipType> types = db.getMembershipTypes();
        db.closeConnection();
        return types;
    }

    /**
     * Retrieves the best performance record for a swimmer in a specific discipline.
     *
     * @param swimmerID the ID of the swimmer
     * @param disciplineID the ID of the discipline
     * @return the best performance record for the swimmer in the specified discipline, or null if not found
     */
    public static Record getBestRecordForSwimmerByDiscipline(int swimmerID, int disciplineID) {
        DatabaseManager db = new DatabaseManager();
        Record record = null;
        Swimmer swimmer = db.getSwimmer(swimmerID);
        for (Record dbRecord : swimmer.getBestRecordPerDiscipline(db)) {
            if (dbRecord.getDisciplineID() == disciplineID) {
                record = dbRecord;
            }
        }
        db.closeConnection();
        return record;
    }
}
