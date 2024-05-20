import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Competition
{
    private int competitionID;
    private LocalDate date;
    private String location;
    private List<Swimmer> swimmers;
    private String compName;

public Competition(int competitionID, LocalDate date, String location, String compName)
{
    this.competitionID = competitionID;
    this.date = date;
    this.location = location;
    this.compName = compName;
    this.swimmers = new ArrayList<>();
}

    public Competition(LocalDate date, String location, String compName)
    {
        this.date = date;
        this.location = location;
        this.compName = compName;
    }
public int getCompetitionID()
    {
        return competitionID;
    }
    public void setCompetitionID()
    {
        this.competitionID = competitionID;
    }
    public LocalDate getDate()
    {
        return date;
    }
    public void setDate()
    {
        this.date= date;
    }
    public String getLocation()
    {
        return location;
    }
    public void setLocation()
    {
        this.location = location;
    }
public void registerSwimmer(Swimmer swimmer)
{
   swimmers.add(swimmer);
}
public void recordResult(Swimmer swimmer, int placement, double time, DatabaseManager dbManager) throws SQLException {
    Result result = new Result(swimmer.getSwimmerID(),this.getCompetitionID(),placement,time);
    dbManager.recordResult(this.getCompetitionID(), swimmer.getSwimmerID(), placement, time);
}

    public void registerSwimmer(Swimmer swimmer, DatabaseManager dbManager) throws SQLException {
        swimmers.add(swimmer);
        dbManager.registerSwimmerForCompetition(this.competitionID, swimmer.getSwimmerID());
    }

}