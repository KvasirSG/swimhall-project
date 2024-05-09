import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {

    private Connection connection;
    private String databaseUrl = "jdbc:sqlite:swim-db";
    public DatabaseManager() {
        connect(databaseUrl);
    }

    private void connect(String databaseUrl) {
        try {
            this.connection = DriverManager.getConnection(databaseUrl);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean getAutoCommit() throws SQLException {
        return connection.getAutoCommit();
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

    public int addNewSwimmer(Swimmer swimmer, int teamID){
        int swimmerID = 0;
        String memberSql = "INSERT INTO Members (name, birthday, membershipTypeID) VALUES (?, ?, ?)";
        String swimmerSql = "INSERT INTO Swimmers (memberID, teamID) VALUES (?, ?)";


        PreparedStatement memberStmt = null;
        PreparedStatement swimmerStmt = null;
        try{
            connection.setAutoCommit(false); // Start transaction
            memberStmt = connection.prepareStatement(memberSql,Statement.RETURN_GENERATED_KEYS);
            memberStmt.setString(1, swimmer.getName());
            memberStmt.setDate(2, Date.valueOf(swimmer.getBirthDate()));
            memberStmt.setInt(3, swimmer.getMembershipTypeID());
            int memberAffected = memberStmt.executeUpdate();

            if(memberAffected == 1){
                ResultSet rs = memberStmt.getGeneratedKeys();
                if (rs.next()) {
                    int memberID = rs.getInt(1);

                    swimmerStmt = connection.prepareStatement(swimmerSql,Statement.RETURN_GENERATED_KEYS);
                    swimmerStmt.setInt(1, memberID);
                    swimmerStmt.setInt(2,teamID);
                    int swimmerAffected = swimmerStmt.executeUpdate();

                    if (swimmerAffected==1){
                        ResultSet srs = swimmerStmt.getGeneratedKeys();
                        if (srs.next()){
                            swimmerID = srs.getInt(1);
                        }
                    }
                }
            }
            connection.commit(); // Commit transaction
        }catch (SQLException e){
            if(connection !=null){
                try{
                    connection.rollback(); // Rollback transaction on error
                }catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                }
            }
            System.out.println(e.getMessage());
        }
        try{
            connection.setAutoCommit(true);
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return swimmerID;
    }

    public void addDiscipline(String name){
        String sql = "INSERT INTO Disciplines (name) VALUES (?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, name);
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("A new discipline was added successfully.");
            } else {
                System.out.println("A new discipline could not be added.");
            }
        } catch (SQLException e) {
            System.out.println("Error adding new discipline: " + e.getMessage());
        }
    }

    public void addSwimmerDiscipline(int swimmerID, int disciplineID){
        String sql = "INSERT INTO SwimmerDisciplines (swimmerID, disciplineID) VALUES (?, ?)";
        try(PreparedStatement pstmt = connection.prepareStatement(sql)){
            pstmt.setInt(1, swimmerID);
            pstmt.setInt(2, disciplineID);
            pstmt.executeUpdate();
        }catch (SQLException e){
            System.out.println("Error adding discipline to swimmer: " + e.getMessage());
        }
    }

    // Method to get all disciplines for a specific swimmer
    public List<Discipline> getDisciplinesForSwimmer(int swimmerID) {
        List<Discipline> disciplines = new ArrayList<>();
        String sql = "SELECT d.disciplineID, d.name FROM Disciplines d " +
                "JOIN SwimmerDisciplines sd ON d.disciplinesID = sd.disciplineID " +
                "WHERE sd.swimmerID = ?";

        try(PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1,swimmerID);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()){
                int disciplineID = rs.getInt("disciplineID");
                String name = rs.getString("name");
                disciplines.add(new Discipline(disciplineID, name));
            }
        }catch (SQLException e){
            System.out.println("Error retrieving disciplines: " + e.getMessage());
        }
        return disciplines;
    }

    // Method to add a new performance record
    public void addPerformanceRecord(Record record) {
        String sql = "INSERT INTO PerformanceRecords (swimmerID, disciplineID, time, date) VALUES (?, ?, ?, ?)";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, record.getSwimmerID());
            pstmt.setInt(2, record.getDisciplineID());
            pstmt.setDouble(3, record.getTime());
            pstmt.setDate(4, Date.valueOf(record.getDate()));
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("A new performance record was added successfully.");
            } else {
                System.out.println("Failed to add the performance record.");
            }
        } catch (SQLException e) {
            System.out.println("Error adding new performance record: " + e.getMessage());
        }
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

    public void reconnect(){
        try{
            if (this.connection != null && this.connection.isClosed()){
                this.connect(databaseUrl);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
