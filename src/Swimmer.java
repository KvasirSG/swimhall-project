import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Swimmer extends Member {
    //TODO: Team class here
    private List<Discipline> disciplines;

    public Swimmer (int swimmerID, String name, LocalDate birthday,MembershipType membershipType)
    {
        super(swimmerID, name, birthday, membershipType);
    }

    public void addDiscipline(Discipline discipline)
    {
        disciplines.add(discipline);
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
