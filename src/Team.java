import java.util.Comparator;
import java.util.List;

public class Team
{
    private int teamID;
    private String name;
    private int competitionID;
    List<Swimmer> team;

    public Team(int teamID, String name, int)
    {
        this.teamID = teamID;
        this.name = name;
    }

    public int getTeamID()
    {
        return teamID;
    }

    public void setTeamID(int teamID)
    {
        this.teamID = teamID;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void addSwimmer(Swimmer swimmer)
    {
        swimmer.setTeamID(this.teamID);
        team.add(swimmer);
    }

    public void removeSwimmer(Swimmer swimmer)
    {
        if (swimmer.getTeamID() == this.teamID){
            swimmer.setTeamID(0);
            team.remove(swimmer);
        }

    }

}
