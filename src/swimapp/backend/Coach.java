package swimapp.backend;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Coach
{
    private int coachID;
    private String name;
    private int teamId;
    List<Competition> compTeam;

    public Coach(int coachID, String name, int teamId)
    {
        this.coachID = coachID;
        this.name = name;
        this.teamId = teamId;
    }


    public int getCoachID()
    {
        return coachID;
    }
    public void setCoachID(int coachID)
    {
        this.coachID = coachID;
    }
    public String getName()
    {
        return name;
    }
    public int getTeamId()
    {
        return teamId;
    }
    public void setTeamId(int teamId)
    {
        this.teamId = teamId;
    }
    public void setName(String name)
    {
        this.name = name;
    }

    public void selectForCompetition(Competition competition, DatabaseManager dbManager) throws SQLException {
        compTeam.add(competition);
        List<Swimmer>swimmers = new ArrayList<>();
        dbManager.addTeamToCompetition(this.teamId, competition.getCompetitionID());
        for(Swimmer swimmer : swimmers)
        {
            competition.registerSwimmer(swimmer, dbManager);
        }
    }



}
