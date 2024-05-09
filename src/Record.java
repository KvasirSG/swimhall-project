import java.time.LocalDate;

public class Record {
    private int recordID;
    private int swimmerID;
    private int disciplineID;
    private double result;
    private LocalDate recordDate;

    // Contructor to make a record
    public Record(int swimmerID, int disciplineID, double result, LocalDate recordDate){
        this.swimmerID = swimmerID;
        this.disciplineID = disciplineID;
        this.result = result;
        this.recordDate = recordDate;
    }
    // constructor to fetch a record from the db
    public Record(int recordID, int swimmerID, int disciplineID, double result, LocalDate recordDate){
        this.recordID = recordID;
        this.swimmerID = swimmerID;
        this.disciplineID = disciplineID;
        this.result = result;
        this.recordDate = recordDate;
    }
    public int getRecordID() {
        return recordID;
    }
    public void setRecordID(int recordID) {
        this.recordID = recordID;
    }
    public int getSwimmerID() {
        return swimmerID;
    }
    public void setSwimmerID(int swimmerID) {
        this.swimmerID = swimmerID;
    }
    public int getDisciplineID() {
        return disciplineID;
    }
    public void setDisciplineID(int disciplineID) {
        this.disciplineID = disciplineID;
    }
    public double getResult() {
        return result;
    }
    public void setResult(double result) {
        this.result = result;
    }
    public LocalDate getRecordDate() {
        return recordDate;
    }
    public void setRecordDate(LocalDate recordDate) {
        this.recordDate = recordDate;
    }
}
