package swimapp.backend;

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
