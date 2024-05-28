package swimapp.backend;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Represents a team in the swim club.
 */
public class Team {
    public static int JUNIOR = 1;
    public static int SENIOR = 2;
    private int teamID;
    private String name;
    List<Swimmer> team = new ArrayList<>();

    /**
     * Constructs a Team with the specified team ID and name.
     *
     * @param teamID the ID of the team
     * @param name the name of the team
     */
    public Team(int teamID, String name) {
        this.teamID = teamID;
        this.name = name;
    }

    /**
     * Gets the ID of the team.
     *
     * @return the ID of the team
     */
    public int getTeamID() {
        return teamID;
    }

    /**
     * Sets the ID of the team.
     *
     * @param teamID the new ID of the team
     */
    public void setTeamID(int teamID) {
        this.teamID = teamID;
    }

    /**
     * Gets the name of the team.
     *
     * @return the name of the team
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the team.
     *
     * @param name the new name of the team
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Adds a swimmer to the team.
     *
     * @param swimmer the swimmer to add to the team
     */
    public void addSwimmer(Swimmer swimmer) {
        swimmer.setTeamID(this.teamID);
        team.add(swimmer);
    }

    /**
     * Removes a swimmer from the team.
     *
     * @param swimmer the swimmer to remove from the team
     */
    public void removeSwimmer(Swimmer swimmer) {
        if (swimmer.getTeamID() == this.teamID) {
            swimmer.setTeamID(0);
            team.remove(swimmer);
        }
    }

    /**
     * Gets the top performance records per discipline for the team.
     *
     * @param discipline the discipline to get the top records for
     * @param dbManager the database manager to handle database operations
     * @return a list of the top performance records per discipline for the team
     */
    public List<Record> getTopRecordsPerDiscipline(Discipline discipline, DatabaseManager dbManager) {
        List<Record> records = new ArrayList<>();
        for (Swimmer swimmer : team) {
            List<Record> tempRecords = swimmer.getBestRecordPerDiscipline(dbManager);
            for (Record tempRecord : tempRecords) {
                if (tempRecord.getDisciplineID() == discipline.getDisciplineID()) {
                    records.add(tempRecord);
                }
            }
        }
        Collections.sort(records, Comparator.comparingDouble(Record::getTime));
        int count = Math.min(5, records.size());
        List<Record> bestTimes = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            bestTimes.add(records.get(i));
        }
        return bestTimes;
    }

    /**
     * Gets the top swimmers per discipline for the team.
     *
     * @param discipline the discipline to get the top swimmers for
     * @param dbManager the database manager to handle database operations
     * @return a list of the top swimmers per discipline for the team
     */
    public List<Swimmer> getTopSwimmersPerDiscipline(Discipline discipline, DatabaseManager dbManager) {
        List<Swimmer> bestSwimmers = new ArrayList<>();
        for (Record record : getTopRecordsPerDiscipline(discipline, dbManager)) {
            bestSwimmers.add(dbManager.getSwimmer(record.getSwimmerID()));
        }
        return bestSwimmers;
    }
}
