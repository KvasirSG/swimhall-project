import java.util.List;

public class Team
{
    private int teamID;
    private String name;

    List<Swimmer> team;

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

    public Team(int teamID, String name)
    {
        this.teamID = teamID;
        this.name = name;
    }

    public void addSwimmer(Swimmer swimmer)
    {
        team.add(swimmer);
    }

    public void removeSwimmer(Swimmer swimmer)
    {
        team.remove(swimmer);
    }

}
