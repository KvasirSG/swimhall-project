package swimapp.backend;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GuiInterface {
    public static List<Member> getMembersByType(int membershipTypeID){
        List<Member> dbAllMembers= getAllMembers();
        List<Member> dbAllMembersType= new ArrayList<Member>();

        for(Member member : dbAllMembers){
            if(member.getMembershipTypeID() == membershipTypeID){
                dbAllMembersType.add(member);
            }
        }
        return dbAllMembersType;
    }

    public static List<Swimmer> getSwimmersByTeam(int teamID) throws SQLException {
        DatabaseManager db = new DatabaseManager();
        List<Swimmer> swimmers = db.getSwimmersByTeam(teamID);
        db.closeConnection();
        return swimmers;
    }

    public static List<Swimmer> getSwimmersByTeamAndGender(int teamID, Gender gender) {
        DatabaseManager db = new DatabaseManager();
        List<Swimmer> swimmers = db.getSwimmersByTeamAndGender(teamID,gender);
        db.closeConnection();
        return swimmers;
    }

    public static List<Discipline> getDisciplinesForSwimmer(int swimmerID) {
        DatabaseManager db = new DatabaseManager();
        List<Discipline> disciplines = db.getDisciplinesForSwimmer(swimmerID);
        db.closeConnection();
        return disciplines;
    }

    public static void addSwimmerDiscipline(Swimmer swimmer, Discipline discipline) {
        DatabaseManager db = new DatabaseManager();
        swimmer.addDiscipline(db,discipline);
        db.closeConnection();
    }

    public static void addPerformanceRecord(Record record) {
        DatabaseManager db = new DatabaseManager();
        db.addPerformanceRecord(record);
        db.closeConnection();
    }

    public Discipline getDisciplineByID(int disciplineID){
        DatabaseManager db = new DatabaseManager();
        Discipline discipline = db.getDiscipline(disciplineID);
        db.closeConnection();
        return discipline;
    }

    public static List<Swimmer> getSwimmersByDiscipline(int disciplineID){
        DatabaseManager db = new DatabaseManager();
        List<Swimmer> swimmers = db.getSwimmersByDiscipline(disciplineID);
        db.closeConnection();
        return swimmers;
    }

    public static List<Member> getAllMembers(){
        DatabaseManager db = new DatabaseManager();
        List<Member> dbAllMembers= db.getAllMembers();
        db.closeConnection();
        return dbAllMembers;
    }

    public static List<Member> getMembersInArrears(){
        DatabaseManager db = new DatabaseManager();
        Payments payments = new Payments();
        List<Member> dbAllMembers= payments.getMembersInArrears(db);
        db.closeConnection();
        return dbAllMembers;
    }

    public static void addMember(Member member){
        DatabaseManager db = new DatabaseManager();
        db.addMember(member);
        db.closeConnection();
    }

    public static List<MembershipType> getMembershipTypes(){
        DatabaseManager db = new DatabaseManager();
        List<MembershipType> types = db.getMembershipTypes();
        db.closeConnection();
        return types;
    }

    public static Record getBestRecordForSwimmerByDiscipline(int swimmerID, int disciplineID){
        DatabaseManager db = new DatabaseManager();
        Record record=null;
        Swimmer swimmer = db.getSwimmer(swimmerID);
        for (Record dbRecord:swimmer.getBestRecordPerDiscipline(db)){
            if (dbRecord.getDisciplineID() == disciplineID){
                record = dbRecord;
            }
        }
        return record;
    }

}
