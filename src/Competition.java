import java.time.LocalDate;
import java.util.List;

public class Competition
{
    private int competitionID;
    private LocalDate date;
    private String location;
    private List<Swimmer> swimmers;

public Competition(int competitionID, LocalDate date, String location)
{
    this.competitionID = competitionID;
    this.date = date;
    this.location = location;
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
public void recordResult(Swimmer swimmer, int placement, double time)
{
    Result result = new Result(swimmer.getSwimmerID(),this.getCompetitionID(),placement,time);
}






}