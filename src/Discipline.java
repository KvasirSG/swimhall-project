import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Discipline
{
    private int disciplineID;
    private String name;

    public Discipline(int disciplineID, String name){
        this.disciplineID = disciplineID;
        this.name = name;
    }

    public Discipline(String name){
        this.name = name;
    }

    public int getDisciplineID() {
        return disciplineID;
    }
    public String getName() {
        return name;
    }

    public void updateRecord(Swimmer swimmer, double newRecord, LocalDate recordDate, DatabaseManager dBManager)
    {
        Record record = new Record(swimmer.getMemberID(),this.disciplineID,newRecord,recordDate);
        record.registerRecord(dBManager);
    }

    public void registerDiscipline(DatabaseManager dBManager){
        dBManager.addDiscipline(this.getName());
    }
}
