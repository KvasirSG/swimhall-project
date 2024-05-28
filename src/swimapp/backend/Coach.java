package swimapp.backend;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a coach in the swim application.
 */
public class Coach {
    private int coachID;
    private String name;
    private int teamId;
    List<Competition> compTeam;

    /**
     * Constructs a Coach with the specified coach ID, name, and team ID.
     *
     * @param coachID the ID of the coach
     * @param name the name of the coach
     * @param teamId the ID of the team
     */
    public Coach(int coachID, String name, int teamId) {
        this.coachID = coachID;
        this.name = name;
        this.teamId = teamId;
    }

    /**
     * Gets the ID of the coach.
     *
     * @return the ID of the coach
     */
    public int getCoachID() {
        return coachID;
    }

    /**
     * Sets the ID of the coach.
     *
     * @param coachID the new ID of the coach
     */
    public void setCoachID(int coachID) {
        this.coachID = coachID;
    }

    /**
     * Gets the name of the coach.
     *
     * @return the name of the coach
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the coach.
     *
     * @param name the new name of the coach
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the ID of the team.
     *
     * @return the ID of the team
     */
    public int getTeamId() {
        return teamId;
    }

    /**
     * Sets the ID of the team.
     *
     * @param teamId the new ID of the team
     */
    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

    /**
     * Selects this coach's team for a competition and registers the swimmers.
     *
     * @param competition the competition to select for
     * @param dbManager the database manager to handle database operations
     * @throws SQLException if a database access error occurs
     */
    public void selectForCompetition(Competition competition, DatabaseManager dbManager) throws SQLException {
        compTeam.add(competition);
        List<Swimmer> swimmers = new ArrayList<>();
        dbManager.addTeamToCompetition(this.teamId, competition.getCompetitionID());
        for (Swimmer swimmer : swimmers) {
            competition.registerSwimmer(swimmer, dbManager);
        }
    }
}
