import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Swimmer extends Member {
    private int SwimmerID;
    //TODO: Team class here
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

    public int getSwimmerID() {
        return this.SwimmerID;
    }

    public void addDiscipline(DatabaseManager dBManager, Discipline discipline)
    {
        disciplines.add(discipline);
        dBManager.addSwimmerDiscipline(this.getSwimmerID(),discipline.getDisciplineID());
    }

    public void updatePerformance(Discipline discipline, double result, LocalDate date)
    {
        discipline.updateRecord(this, result, date);
    }

    private Map getBestResults()
    {
        HashMap<Discipline, Record> bestResult = new HashMap<>();
        return bestResult;
    }

}
