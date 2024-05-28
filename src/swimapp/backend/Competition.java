package swimapp.backend;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a competition in the swim application.
 */
public class Competition {
    private int competitionID;
    private LocalDate date;
    private String location;
    private List<Swimmer> swimmers;
    private String compName;

    /**
     * Constructs a Competition with the specified competition ID, date, location, and competition name.
     *
     * @param competitionID the ID of the competition
     * @param date the date of the competition
     * @param location the location of the competition
     * @param compName the name of the competition
     */
    public Competition(int competitionID, LocalDate date, String location, String compName) {
        this.competitionID = competitionID;
        this.date = date;
        this.location = location;
        this.compName = compName;
        this.swimmers = new ArrayList<>();
    }

    /**
     * Constructs a Competition with the specified date, location, and competition name.
     *
     * @param date the date of the competition
     * @param location the location of the competition
     * @param compName the name of the competition
     */
    public Competition(LocalDate date, String location, String compName) {
        this.date = date;
        this.location = location;
        this.compName = compName;
        this.swimmers = new ArrayList<>();
    }

    /**
     * Gets the ID of the competition.
     *
     * @return the ID of the competition
     */
    public int getCompetitionID() {
        return competitionID;
    }

    /**
     * Sets the ID of the competition.
     */
    public void setCompetitionID(int competitionID) {
        this.competitionID = competitionID;
    }

    /**
     * Gets the date of the competition.
     *
     * @return the date of the competition
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * Sets the date of the competition.
     *
     * @param date the new date of the competition
     */
    public void setDate(LocalDate date) {
        this.date = date;
    }

    /**
     * Gets the location of the competition.
     *
     * @return the location of the competition
     */
    public String getLocation() {
        return location;
    }

    /**
     * Sets the location of the competition.
     *
     * @param location the new location of the competition
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * Registers a swimmer for this competition.
     *
     * @param swimmer the swimmer to be registered
     */
    public void registerSwimmer(Swimmer swimmer) {
        swimmers.add(swimmer);
    }

    /**
     * Records the result of a swimmer in this competition.
     *
     * @param swimmer the swimmer whose result is to be recorded
     * @param placement the placement of the swimmer
     * @param time the time of the swimmer
     * @param dbManager the database manager to handle database operations
     * @throws SQLException if a database access error occurs
     */
    public void recordResult(Swimmer swimmer, int placement, double time, DatabaseManager dbManager) throws SQLException {
        Result result = new Result(swimmer.getSwimmerID(), this.getCompetitionID(), placement, time);
        dbManager.recordResult(this.getCompetitionID(), swimmer.getSwimmerID(), placement, time);
    }

    /**
     * Registers a swimmer for this competition and updates the database.
     *
     * @param swimmer the swimmer to be registered
     * @param dbManager the database manager to handle database operations
     * @throws SQLException if a database access error occurs
     */
    public void registerSwimmer(Swimmer swimmer, DatabaseManager dbManager) throws SQLException {
        swimmers.add(swimmer);
        dbManager.registerSwimmerForCompetition(this.competitionID, swimmer.getSwimmerID());
    }
}
