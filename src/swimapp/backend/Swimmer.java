package swimapp.backend;

import java.time.LocalDate;
import java.util.*;

/**
 * Represents a swimmer who is also a member of the swim club.
 */
public class Swimmer extends Member {
    private int SwimmerID;
    private int teamID;
    private List<Discipline> disciplines;

    /**
     * Constructs a Swimmer from an existing member.
     *
     * @param memberID the ID of the member
     * @param name the name of the swimmer
     * @param gender the gender of the swimmer
     * @param birthday the birthday of the swimmer
     * @param membershipType the membership type of the swimmer
     */
    public Swimmer(int memberID, String name, Gender gender, LocalDate birthday, MembershipType membershipType) {
        super(memberID, name, gender, birthday, membershipType);
        disciplines = new ArrayList<>();
        calculateTeam();
    }

    /**
     * Constructs a new Swimmer and member.
     *
     * @param name the name of the swimmer
     * @param gender the gender of the swimmer
     * @param birthday the birthday of the swimmer
     * @param isPassive the passive status of the swimmer
     */
    public Swimmer(String name, Gender gender, LocalDate birthday, Boolean isPassive) {
        super(name, gender, birthday, isPassive);
        disciplines = new ArrayList<>();
        calculateTeam();
    }

    /**
     * Constructs a Swimmer from the database.
     *
     * @param swimmerID the ID of the swimmer
     * @param memberID the ID of the member
     * @param name the name of the swimmer
     * @param gender the gender of the swimmer
     * @param birthday the birthday of the swimmer
     * @param membershipType the membership type of the swimmer
     */
    public Swimmer(int swimmerID, int memberID, String name, Gender gender, LocalDate birthday, MembershipType membershipType) {
        super(memberID, name, gender, birthday, membershipType);
        DatabaseManager dbManager = new DatabaseManager();
        this.SwimmerID = swimmerID;
        if (this.SwimmerID != 0) {
            disciplines = dbManager.getDisciplinesForSwimmer(this.SwimmerID);
            dbManager.closeConnection();
        }
        dbManager.closeConnection();
        calculateTeam();
    }

    /**
     * Calculates the team based on the swimmer's age.
     */
    private void calculateTeam() {
        if (this.getAge() >= 18) {
            setTeamID(Team.SENIOR);
        } else {
            setTeamID(Team.JUNIOR);
        }
    }

    /**
     * Sets the ID of the swimmer.
     *
     * @param swimmerID the new ID of the swimmer
     */
    public void setSwimmerID(int swimmerID) {
        SwimmerID = swimmerID;
    }

    /**
     * Gets the ID of the swimmer.
     *
     * @return the ID of the swimmer
     */
    public int getSwimmerID() {
        return this.SwimmerID;
    }

    /**
     * Adds a discipline to the swimmer's list of disciplines.
     *
     * @param dBManager the database manager to handle database operations
     * @param discipline the discipline to add
     */
    public void addDiscipline(DatabaseManager dBManager, Discipline discipline) {
        disciplines.add(discipline);
        dBManager.addSwimmerDiscipline(this.getSwimmerID(), discipline.getDisciplineID());
    }

    /**
     * Gets the list of disciplines for the swimmer.
     *
     * @param dBManager the database manager to handle database operations
     * @return the list of disciplines for the swimmer
     */
    public List<Discipline> getDisciplines(DatabaseManager dBManager) {
        List<Discipline> disciplineList = dBManager.getDisciplinesForSwimmer(this.SwimmerID);
        disciplines = disciplineList;
        return disciplineList;
    }

    /**
     * Updates the performance record for the swimmer in a specific discipline.
     *
     * @param discipline the discipline to update the record for
     * @param result the result time
     * @param date the date of the performance
     * @param dBManager the database manager to handle database operations
     */
    public void updatePerformance(Discipline discipline, double result, LocalDate date, DatabaseManager dBManager) {
        discipline.updateRecord(this, result, date, dBManager);
    }

    /**
     * Gets the best performance record for the swimmer.
     *
     * @param dBManager the database manager to handle database operations
     * @return the best performance record for the swimmer
     */
    public Record getBestResult(DatabaseManager dBManager) {
        List<Record> records = dBManager.getPerformanceRecordsForSwimmer(this.SwimmerID);
        Optional<Record> bestResult = records.stream().min(Comparator.comparingDouble(Record::getTime));
        return bestResult.orElse(null);
    }

    /**
     * Gets the top performance records for the swimmer.
     *
     * @param amount the number of top records to retrieve
     * @param dbManager the database manager to handle database operations
     * @return the list of top performance records for the swimmer
     */
    public List<Record> getTopResults(int amount, DatabaseManager dbManager) {
        List<Record> records = dbManager.getPerformanceRecordsForSwimmer(this.SwimmerID);
        Collections.sort(records, Comparator.comparingDouble(Record::getTime));
        int count = Math.min(amount, records.size());
        List<Record> bestResults = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            bestResults.add(records.get(i));
        }
        return bestResults;
    }

    /**
     * Gets the best performance record per discipline for the swimmer.
     *
     * @param databaseManager the database manager to handle database operations
     * @return the list of best performance records per discipline for the swimmer
     */
    public List<Record> getBestRecordPerDiscipline(DatabaseManager databaseManager) {
        List<Record> allRecords = databaseManager.getPerformanceRecordsForSwimmer(this.SwimmerID);
        getDisciplines(databaseManager);
        List<Record> bestRecords = new ArrayList<>();
        for (Discipline discipline : disciplines) {
            List<Record> tempRecords = new ArrayList<>();
            for (Record record : allRecords) {
                if (record.getDisciplineID() == discipline.getDisciplineID()) {
                    tempRecords.add(record);
                }
            }
            Optional<Record> bestTime = tempRecords.stream().min(Comparator.comparingDouble(Record::getTime));
            bestRecords.add(bestTime.orElse(null));
        }
        return bestRecords;
    }

    /**
     * Registers the swimmer in the database.
     *
     * @param dbManager the database manager to handle database operations
     * @param newMember whether the swimmer is a new member
     */
    public void registerSwimmer(DatabaseManager dbManager, Boolean newMember) {
        if (newMember) {
            //dbManager.addNewSwimmer(this,this.teamID);
        } else {
            this.SwimmerID = dbManager.addSwimmerFromExistingMember(this.getMemberID(), this.teamID);
        }
        dbManager.closeConnection();
    }

    /**
     * Removes the swimmer from the database.
     *
     * @param dbManager the database manager to handle database operations
     * @param asMember whether to remove the swimmer as a member as well
     */
    public void remove(DatabaseManager dbManager, boolean asMember) {
        if (asMember) {
            dbManager.removeSwimmer(this.getSwimmerID());
            dbManager.deleteMember(this.getMemberID());
        } else {
            dbManager.removeSwimmer(this.getSwimmerID());
        }
        dbManager.closeConnection();
    }

    /**
     * Sets the team ID of the swimmer.
     *
     * @param teamID the new team ID of the swimmer
     */
    public void setTeamID(int teamID) {
        this.teamID = teamID;
    }

    /**
     * Gets the team ID of the swimmer.
     *
     * @return the team ID of the swimmer
     */
    public int getTeamID() {
        return teamID;
    }
}
