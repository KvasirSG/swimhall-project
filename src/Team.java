import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Team
{
    private int teamID;
    private String name;
    private int competitionID;
    List<Swimmer> team;

    public Team(int teamID, String name)
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

    public List<Record> getTopRecordsPerDiscipline(Discipline discipline, DatabaseManager dbManager){
        List<Record> records = new ArrayList<>();
        for (Swimmer swimmer : team){
            List<Record> tempRecords = swimmer.getBestRecordPerDiscipline(dbManager);
            for (Record tempRecord:tempRecords){
                if (tempRecord.getDisciplineID()==discipline.getDisciplineID()){
                    records.add(tempRecord);
                }
            }
        }
        Collections.sort(records, Comparator.comparingDouble(Record::getTime));
        int count = Math.min(5,records.size());
        List<Record> bestTimes = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            bestTimes.add(records.get(i));
        }
        return bestTimes;
    }

    public List<Swimmer> getTopSwimmersPerDiscipline(Discipline discipline, DatabaseManager dbManager){
        List<Swimmer> bestSwimmers = new ArrayList<>();
        for (Record record:getTopRecordsPerDiscipline(discipline, dbManager)){
            bestSwimmers.add(dbManager.getSwimmer(record.getSwimmerID()));
        }
        return bestSwimmers;
    }

}
