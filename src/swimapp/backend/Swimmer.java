package swimapp.backend;

import java.time.LocalDate;
import java.util.*;

public class Swimmer extends Member {
    private int SwimmerID;
    private int teamID;
    private List<Discipline> disciplines;

    // Constructor to add swimmer from a member
    public Swimmer (int memberID, String name, Gender gender,  LocalDate birthday,MembershipType membershipType)
    {
        super(memberID, name,gender, birthday, membershipType);
        disciplines = new ArrayList<>();
        calculateTeam();
    }

    //Constructor to add new swimmer and member
    public Swimmer(String name, Gender gender,  LocalDate birthday, Boolean isPassive){
        super(name,gender,birthday, isPassive);
        disciplines = new ArrayList<>();
        calculateTeam();
    }

    //Constructor to import swimmer from the DB
    public Swimmer (int swimmerID, int memberID, String name, Gender gender, LocalDate birthday,MembershipType membershipType)
    {
        super(memberID, name, gender , birthday, membershipType);
        DatabaseManager dbManager = new DatabaseManager();
        this.SwimmerID = swimmerID;
        if(this.SwimmerID !=0){
            disciplines = dbManager.getDisciplinesForSwimmer(this.SwimmerID);
            dbManager.closeConnection();
        }
        dbManager.closeConnection();
        calculateTeam();

    }

    private void calculateTeam(){
        if (this.getAge()>=18){
            setTeamID(Team.SENIOR);
        }else {
            setTeamID(Team.JUNIOR);
        }
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

    public List<Discipline> getDisciplines(DatabaseManager dBManager){
        List<Discipline> disciplineList = dBManager.getDisciplinesForSwimmer(this.SwimmerID);
        disciplines = disciplineList;
        return disciplineList;
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

    public List<Record> getBestRecordPerDiscipline(DatabaseManager databaseManager){
        List<Record> allRecords = databaseManager.getPerformanceRecordsForSwimmer(this.SwimmerID);
        getDisciplines(databaseManager);
        List<Record> bestRecords = new ArrayList<>();
        for (Discipline discipline : disciplines){
            List<Record> tempRecords = new ArrayList<>();
            for (Record record : allRecords){
                if (record.getDisciplineID()== discipline.getDisciplineID()){
                    tempRecords.add(record);
                }
            }
            Optional<Record> bestTime = tempRecords.stream().min(Comparator.comparingDouble(Record::getTime));
            bestRecords.add(bestTime.orElse(null));
        }
        return bestRecords;
    }

    public void registerSwimmer(DatabaseManager dbManager, Boolean newMember){
        if (newMember){
            //dbManager.addNewSwimmer(this,this.teamID);
        }else {
            this.SwimmerID = dbManager.addSwimmerFromExistingMember(this.getMemberID(),this.teamID);
        }
        dbManager.closeConnection();

    }

    public void remove(DatabaseManager dbManager, boolean asMember){
        if (asMember){
            dbManager.removeSwimmer(this.getSwimmerID());
            dbManager.deleteMember(this.getMemberID());
        } else {
            dbManager.removeSwimmer(this.getSwimmerID());
        }
        dbManager.closeConnection();
    }

    public void setTeamID(int teamID) {
        this.teamID = teamID;
    }

    public int getTeamID() {
        return teamID;
    }
}
