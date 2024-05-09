import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {

    private Connection connection;
    public DatabaseManager() {
        connect("jdbc:sqlite:swim-db");
    }

    private void connect(String databaseUrl) {
        try {
            this.connection = DriverManager.getConnection(databaseUrl);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addMember(Member member) {
        String sql = "INSERT INTO Members (name, birthday, membershipTypeID) VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, member.getName());
            statement.setDate(2, Date.valueOf(member.getBirthDate()));
            statement.setInt(3, member.getMembershipTypeID());

            int affectedRows = statement.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        member.setMemberID(generatedKeys.getInt(1));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Member getMember(int memberID) {
        String sql = "SELECT Members.memberID, Members.name, Members.birthday, Members.membershipTypeID, " +
                "MembershipTypes.description, MembershipTypes.fee " +
                "FROM Members " +
                "JOIN MembershipTypes ON Members.membershipTypeID = MembershipTypes.typeID " +
                "WHERE Members.memberID = ?";

        try (
             PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, memberID);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                int retrievedID = rs.getInt("memberID");
                String name = rs.getString("name");
                LocalDate birthday = rs.getDate("birthday").toLocalDate();
                int membershipTypeID = rs.getInt("membershipTypeID");
                String description = rs.getString("description");
                double fee = rs.getDouble("fee");

                MembershipType membershipType = new MembershipType(membershipTypeID, description, fee);
                return new Member(retrievedID, name, birthday, membershipType);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }


    public List<Member> getAllMembers() {
        List<Member> members = new ArrayList<>();
        String sql = "SELECT Members.memberID, Members.name, Members.birthday, Members.membershipTypeID, " +
                "MembershipTypes.description, MembershipTypes.fee " +
                "FROM Members " +
                "JOIN MembershipTypes ON Members.membershipTypeID = MembershipTypes.typeID ";

        try (
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int memberID = rs.getInt("memberID");
                String name = rs.getString("name");
                LocalDate birthday = rs.getDate("birthday").toLocalDate();
                int membershipTypeID = rs.getInt("membershipTypeID");
                String description = rs.getString("description");
                double fee = rs.getDouble("fee");
                MembershipType membershipType = new MembershipType(membershipTypeID, description, fee);
                members.add(new Member(memberID, name, birthday, membershipType));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return members;
    }



    // Method to retrieve a MembershipType based on typeID
    public MembershipType getMembershipType(int typeID) {
        String sql = "SELECT typeID, description, fee FROM MembershipTypes WHERE typeID = ?";
        MembershipType membershipType = null;

        try (
             PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, typeID);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                int retrievedTypeID = rs.getInt("typeID");
                String description = rs.getString("description");
                double fee = rs.getDouble("fee");

                membershipType = new MembershipType(retrievedTypeID, description, fee);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return membershipType;
    }

    public MembershipType getMembershipTypeForMember(int memberID) {
        String sql = "SELECT MembershipTypes.typeID, MembershipTypes.description, MembershipTypes.fee " +
                "FROM MembershipTypes " +
                "JOIN Members ON Members.membershipTypeID = MembershipTypes.typeID " +
                "WHERE Members.memberID = ?";

        try (
             PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, memberID);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                int typeID = rs.getInt("typeID");
                String description = rs.getString("description");
                double fee = rs.getDouble("fee");

                return new MembershipType(typeID, description, fee);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }


    // Method to close the database connection
    public void closeConnection() {
        try {
            if (this.connection != null && !this.connection.isClosed()) {
                this.connection.close();
                System.out.println("Connection to SQLite has been closed.");
            }
        } catch (SQLException e) {
            System.out.println("Error closing connection: " + e.getMessage());
        }
    }
}
