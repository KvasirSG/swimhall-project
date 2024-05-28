package swimapp.backend;

import java.time.LocalDate;

/**
 * Represents a swimming discipline.
 */
public class Discipline {
    private int disciplineID;
    private String name;

    /**
     * Constructs a Discipline with the specified discipline ID and name.
     *
     * @param disciplineID the ID of the discipline
     * @param name the name of the discipline
     */
    public Discipline(int disciplineID, String name) {
        this.disciplineID = disciplineID;
        this.name = name;
    }

    /**
     * Constructs a Discipline with the specified name.
     *
     * @param name the name of the discipline
     */
    public Discipline(String name) {
        this.name = name;
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
     * Gets the name of the discipline.
     *
     * @return the name of the discipline
     */
    public String getName() {
        return name;
    }

    /**
     * Updates the record for a swimmer in this discipline.
     *
     * @param swimmer the swimmer whose record is to be updated
     * @param newRecord the new record time
     * @param recordDate the date of the record
     * @param dBManager the database manager to handle database operations
     */
    public void updateRecord(Swimmer swimmer, double newRecord, LocalDate recordDate, DatabaseManager dBManager) {
        Record record = new Record(swimmer.getMemberID(), this.disciplineID, newRecord, recordDate);
        record.registerRecord(dBManager);
    }

    /**
     * Registers this discipline in the database.
     *
     * @param dBManager the database manager to handle database operations
     */
    public void registerDiscipline(DatabaseManager dBManager) {
        dBManager.addDiscipline(this.getName());
    }

    /**
     * Returns a string representation of the discipline.
     *
     * @return the name of the discipline
     */
    @Override
    public String toString() {
        return getName();
    }
}
