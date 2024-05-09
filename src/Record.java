import java.time.LocalDate;

public class Record {
    private int recordID;
    private int swimmerID;
    private int disciplineID;
    private double time;
    private LocalDate date;

    // Contructor to make a record
    public Record(int swimmerID, int disciplineID, double time, LocalDate date){
        this.swimmerID = swimmerID;
        this.disciplineID = disciplineID;
        this.time = time;
        this.date = date;
    }
    // constructor to fetch a record from the db
    public Record(int recordID, int swimmerID, int disciplineID, double time, LocalDate date){
        this.recordID = recordID;
        this.swimmerID = swimmerID;
        this.disciplineID = disciplineID;
        this.time = time;
        this.date = date;
    }
    public int getRecordID() {
        return recordID;
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
    public double getTime() {
        return time;
    }

    public LocalDate getDate() {
        return date;
    }
}
