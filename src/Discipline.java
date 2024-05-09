import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Discipline
{
    private int disciplineID;
    private String name;

    public int getDisciplineID() {
        return disciplineID;
    }
    public String getName() {
        return name;
    }

    public void updateRecord(Swimmer swimmer, double newRecord, LocalDate recordDate)
    {
        Record record = new Record(swimmer.getMemberID(),this.disciplineID,newRecord,recordDate);
    }
}
