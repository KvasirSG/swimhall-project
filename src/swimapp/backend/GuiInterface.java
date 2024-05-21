package swimapp.backend;

import java.util.ArrayList;
import java.util.List;

public class GuiInterface {
    public static List<Member> getMembersByType(int membershipTypeID){
        DatabaseManager db = new DatabaseManager();
        List<Member> dbAllMembers= db.getAllMembers();
        db.closeConnection();
        List<Member> dbAllMembersType= new ArrayList<Member>();

        for(Member member : dbAllMembers){
            if(member.getMembershipTypeID() == membershipTypeID){
                dbAllMembersType.add(member);
            }
        }
        return dbAllMembersType;
    }
}
