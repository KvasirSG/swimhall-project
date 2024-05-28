package swimapp.backend;

import java.time.LocalDate;

/**
 * Represents a performance record for a swimmer in a specific discipline.
 */
public class Record {
    private int recordID;
    private int swimmerID;
    private int disciplineID;
    private double time;
    private LocalDate date;

    /**
     * Constructs a Record with the specified swimmer ID, discipline ID, time, and date.
     *
     * @param swimmerID the ID of the swimmer
     * @param disciplineID the ID of the discipline
     * @param time the performance time of the swimmer
     * @param date the date of the performance
     */
    public Record(int swimmerID, int disciplineID, double time, LocalDate date) {
        this.swimmerID = swimmerID;
        this.disciplineID = disciplineID;
        this.time = time;
        this.date = date;
    }

    /**
     * Constructs a Record with the specified record ID, swimmer ID, discipline ID, time, and date.
     *
     * @param recordID the ID of the record
     * @param swimmerID the ID of the swimmer
     * @param disciplineID the ID of the discipline
     * @param time the performance time of the swimmer
     * @param date the date of the performance
     */
    public Record(int recordID, int swimmerID, int disciplineID, double time, LocalDate date) {
        this.recordID = recordID;
        this.swimmerID = swimmerID;
        this.disciplineID = disciplineID;
        this.time = time;
        this.date = date;
    }

    /**
     * Gets the ID of the record.
     *
     * @return the ID of the record
     */
    public int getRecordID() {
        return recordID;
    }

    /**
     * Gets the ID of the swimmer.
     *
     * @return the ID of the swimmer
     */
    public int getSwimmerID() {
        return swimmerID;
    }

    /**
     * Sets the ID of the swimmer.
     *
     * @param swimmerID the new ID of the swimmer
     */
    public void setSwimmerID(int swimmerID) {
        this.swimmerID = swimmerID;
    }

    /**
     * Gets the ID of the discipline.
     *
     * @return the ID of the discipline
     */
    public int getDisciplineID() {
        return disciplineID;
    }

    /**
     * Sets the ID of the discipline.
     *
     * @param disciplineID the new ID of the discipline
     */
    public void setDisciplineID(int disciplineID) {
        this.disciplineID = disciplineID;
    }

    /**
     * Gets the performance time of the swimmer.
     *
     * @return the performance time of the swimmer
     */
    public double getTime() {
        return time;
    }

    /**
     * Gets the date of the performance.
     *
     * @return the date of the performance
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * Registers the performance record in the database.
     *
     * @param dBManager the database manager to handle database operations
     */
    public void registerRecord(DatabaseManager dBManager) {
        dBManager.addPerformanceRecord(this);
    }
}
