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

    /* ============================================================
    MEMBER MANAGEMENT
     =============================================================*/
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

    /* ============================================================
    MEMBERSHIP MANAGEMENT
     =============================================================*/

    public void updateMembershipFee(int typeID, double newFee) {
        String sql = "UPDATE MembershipTypes SET fee = ? WHERE typeID = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setDouble(1, newFee);
            pstmt.setInt(2, typeID);

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Membership fee updated successfully for typeID: " + typeID);
            } else {
                System.out.println("No membership type found with typeID: " + typeID);
            }
        } catch (SQLException e) {
            System.out.println("Error updating membership fee: " + e.getMessage());
        }
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

    /* ============================================================
    SWIMMER MANAGEMENT
     =============================================================*/

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
            connection.setAutoCommit(true); // reset the state after end transaction
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return swimmerID;
    }

    public void addSwimmerFromExistingMember(int memberID,int teamID){
        String sql = "INSERT INTO Swimmers (memberID, teamID) VALUES (?, ?)";
        try(PreparedStatement pstmt = connection.prepareStatement(sql)){
            pstmt.setInt(1, memberID);
            pstmt.setInt(2, teamID);
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows>0){
                System.out.println("New swimmer added successfully for memberID: " + memberID);
            }
            else System.out.println("Failed to add new swimmer for memberID: " + memberID);
        }catch (SQLException e){
            System.out.println("Error adding new swimmer: " + e.getMessage());
        }
    }

    public Swimmer getSwimmer(int swimmerID){
        String sql = "SELECT Swimmers.swimmerID, Swimmers.teamID, Members.memberID, Members.name, Members.birthday, Members.membershipTypeID, MembershipTypes.description, MembershipTypes.fee " +
                "FROM Swimmers " +
                "JOIN Members ON Swimmers.memberID = Members.memberID" +
                "LEFT JOIN MembershipTypes ON Members.membershipTypeID = MembershipTypes.membershipTypeID" +
                "WHERE Swimmers.swimmerID = ?";
        Swimmer swimmer = null;
        try(PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1,swimmerID);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()){
                int teamID = rs.getInt("teamID");
                int memberID = rs.getInt("memberID");
                String name = rs.getString("name");
                LocalDate birthday = rs.getDate("birthday").toLocalDate();
                int membershipTypeID = rs.getInt("membershipTypeID");
                String description = rs.getString("description");
                double fee = rs.getDouble("fee");

                swimmer = new Swimmer(swimmerID,memberID,name,birthday,new MembershipType(membershipTypeID,description,fee));
            }
        }catch (SQLException e){
            System.out.println("Error retrieving swimmer: " + e.getMessage());
        }
        return swimmer;
    }

    public List<Swimmer> getSwimmers(){
        String sql = "SELECT Swimmers.swimmerID, Swimmers.teamID, Members.memberID, Members.name, Members.birthday, Members.membershipTypeID, MembershipTypes.description, MembershipTypes.fee " +
                "FROM Swimmers " +
                "JOIN Members ON Swimmers.memberID = Members.memberID" +
                "LEFT JOIN MembershipTypes ON Members.membershipTypeID = MembershipTypes.membershipTypeID";
        List<Swimmer> swimmers = new ArrayList<>();
        try(PreparedStatement pstmt = connection.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()){
                int swimmerID = rs.getInt("swimmerID");
                int teamID = rs.getInt("teamID");
                int memberID = rs.getInt("memberID");
                String name = rs.getString("name");
                LocalDate birthday = rs.getDate("birthday").toLocalDate();
                int membershipTypeID = rs.getInt("membershipTypeID");
                String description = rs.getString("description");
                double fee = rs.getDouble("fee");

                Swimmer swimmer = new Swimmer(swimmerID,memberID,name,birthday,new MembershipType(membershipTypeID,description,fee));
                swimmers.add(swimmer);

            }
        }catch (SQLException e){
            System.out.println("Error retrieving swimmers: " + e.getMessage());
        }
        return swimmers;
    }

    /* ============================================================
    DISCIPLINE MANAGEMENT
     =============================================================*/

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

    public Discipline getDiscipline(int disciplineID){
        String sql = "SELECT d.disciplineID, d.name FROM Disciplines d " +
                "WHERE d.disciplineID = ?";
        Discipline discipline = null;
        try(PreparedStatement pstmt = connection.prepareStatement(sql)){
            pstmt.setInt(1, disciplineID);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()){
                String name = rs.getString("name");
                discipline = new Discipline(disciplineID,name);

            }
        }catch (SQLException e){
            System.out.println("Error retrieving discipline: " + e.getMessage());
        }
        return discipline;
    }

    public List<Discipline> getDisciplines(){
        String sql = "SELECT d.disciplineID, d.name FROM Disciplines d";
        List<Discipline> disciplines = new ArrayList<>();
        try(PreparedStatement pstmt = connection.prepareStatement(sql)){
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()){
                int disciplineID = rs.getInt("disciplineID");
                String name = rs.getString("name");
                Discipline discipline = new Discipline(disciplineID,name);
                disciplines.add(discipline);

            }
        }catch (SQLException e){
            System.out.println("Error retrieving disciplines: " + e.getMessage());
        }
        return disciplines;
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

    /* ============================================================
    RECORD MANAGEMENT
     =============================================================*/

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

    public List<Record> getPerformanceRecordsForSwimmer(int swimmerID) {
        List<Record> records = new ArrayList<>();
        String sql = "SELECT pr.time, pr.date, pr.disciplineID " +
                "FROM PerformanceRecords pr " +
                "WHERE pr.swimmerID = ?";
        try(PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, swimmerID);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                int disciplineID = rs.getInt("disciplineID ");
                double time = rs.getDouble("time");
                LocalDate date = rs.getDate("date").toLocalDate();
                Record record = new Record(swimmerID,disciplineID,time,date);
                records.add(record);
            }
        }catch (SQLException e){
            System.out.println("Error retrieving performance records: " + e.getMessage());
        }
        return records;
    }

    /* ============================================================
    DB CONNECTION MANAGEMENT
     =============================================================*/

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
