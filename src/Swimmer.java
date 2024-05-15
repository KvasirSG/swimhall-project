import java.time.LocalDate;
import java.util.*;

public class Swimmer extends Member {
    private int SwimmerID;
    private int teamID;
    private List<Discipline> disciplines;

    public Swimmer (int memberID, String name, LocalDate birthday,MembershipType membershipType, DatabaseManager dBmanager)
    {
        super(memberID, name, birthday, membershipType);
        if(this.SwimmerID !=0){
            disciplines = dBmanager.getDisciplinesForSwimmer(this.SwimmerID);
        }
    }
    public Swimmer (int swimmerID, int memberID, String name, LocalDate birthday,MembershipType membershipType)
    {
        super(memberID, name, birthday, membershipType);
        this.SwimmerID = swimmerID;
        disciplines = new ArrayList<>();
    }

    public void setSwimmerID(int swimmerID) {
        SwimmerID = swimmerID;
    }

    public int getSwimmerID() {
        return this.SwimmerID;
    }

    public void addDiscipline(DatabaseManager dBManager, Discipline discipline)
    {
        disciplines.add(discipline);
        dBManager.addSwimmerDiscipline(this.getSwimmerID(),discipline.getDisciplineID());
    }

    public void updatePerformance(Discipline discipline, double result, LocalDate date, DatabaseManager dBManager)
    {
        discipline.updateRecord(this, result, date, dBManager);
    }

    public Record getBestResult(DatabaseManager dBManager)
    {
        List<Record> records = dBManager.getPerformanceRecordsForSwimmer(this.SwimmerID);
        Optional<Record> bestResult = records.stream().min(Comparator.comparingDouble(Record::getTime));
        return bestResult.orElse(null);
    }

    public List<Record> getTopResults(int amount, DatabaseManager dbManager){
        List<Record> records = dbManager.getPerformanceRecordsForSwimmer(this.SwimmerID);
        Collections.sort(records, Comparator.comparingDouble(Record::getTime));
        int count = Math.min(amount, records.size());
        List<Record> bestResults = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            bestResults.add(records.get(i));
        }
        return bestResults;
    }

    public void registerSwimmer(DatabaseManager dBManager, Boolean newMember){
        if (newMember){
            dBManager.addNewSwimmer(this,this.teamID);
        }else {
            this.SwimmerID = dBManager.addSwimmerFromExistingMember(this.getMemberID(),this.teamID);
        }

    }

    public void setTeamID(int teamID) {
        this.teamID = teamID;
    }

    public int getTeamID() {
        return teamID;
    }
}
