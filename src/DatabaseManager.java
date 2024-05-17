import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Manages database operations for a swimming club management system.
 * Handles connections, and operations related to members, swimmers, memberships, disciplines, and performance records.
 */
public class DatabaseManager {

    private Connection connection;
    private String databaseUrl = "jdbc:sqlite:swim-db";

    /**
     * Constructs a DatabaseManager and initializes a connection to the database.
     */
    public DatabaseManager() {
        connect(databaseUrl);
    }

    /**
     * Establishes a connection to the specified database URL.
     * @param databaseUrl the URL of the database to connect to
     */
    private void connect(String databaseUrl) {
        try {
            this.connection = DriverManager.getConnection(databaseUrl);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Retrieves the auto-commit mode for the current database connection.
     * @return true if the connection is in auto-commit mode, false otherwise.
     * @throws SQLException if a database access error occurs
     */
    public boolean getAutoCommit() throws SQLException {
        return connection.getAutoCommit();
    }

    // Member Management

    /**
     * Adds a new member to the database.
     * @param member the member to add
     */
    public void addMember(Member member) {
        int memberID = 0;
        String sql = "INSERT INTO Members (name, gender, birthday, membershipTypeID) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, member.getName());
            statement.setString(2,member.getGender().name());
            statement.setDate(3, Date.valueOf(member.getBirthDate()));
            statement.setInt(4, member.getMembershipTypeID());

            int affectedRows = statement.executeUpdate();
            if (affectedRows > 0) {
                ResultSet rs = statement.getGeneratedKeys();
                if (rs.next()){
                    memberID = rs.getInt(1);
                    member.setMemberID(memberID);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Retrieves a member from the database based on member ID.
     * @param memberID the ID of the member to retrieve
     * @return the retrieved member, or null if not found
     */
    public Member getMember(int memberID) {
        String sql = "SELECT Members.memberID, Members.name, Members.gender, Members.birthday, Members.membershipTypeID, " +
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
                Gender gender = Gender.valueOf(rs.getString("gender"));
                LocalDate birthday = rs.getDate("birthday").toLocalDate();
                int membershipTypeID = rs.getInt("membershipTypeID");
                String description = rs.getString("description");
                double fee = rs.getDouble("fee");

                MembershipType membershipType = new MembershipType(membershipTypeID, description, fee);
                return new Member(retrievedID, name, gender, birthday, membershipType);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
     * Retrieves all members from the database.
     * @return a list of all members
     */
    public List<Member> getAllMembers() {
        List<Member> members = new ArrayList<>();
        String sql = "SELECT Members.memberID, Members.name, Members.gender, Members.birthday, Members.membershipTypeID, " +
                "MembershipTypes.description, MembershipTypes.fee " +
                "FROM Members " +
                "JOIN MembershipTypes ON Members.membershipTypeID = MembershipTypes.typeID ";

        try (
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int memberID = rs.getInt("memberID");
                String name = rs.getString("name");
                Gender gender = Gender.valueOf(rs.getString("gender"));
                LocalDate birthday = rs.getDate("birthday").toLocalDate();
                int membershipTypeID = rs.getInt("membershipTypeID");
                String description = rs.getString("description");
                double fee = rs.getDouble("fee");
                MembershipType membershipType = new MembershipType(membershipTypeID, description, fee);
                members.add(new Member(memberID, name, gender, birthday, membershipType));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return members;
    }

    public void deleteMember(int memberID){
        String sql = "DELETE FROM Members WHERE memberID = ?";

        try(PreparedStatement pstmt = connection.prepareStatement(sql)){
            pstmt.setInt(1, memberID);
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Member deleted successfuly!");
            } else {
                System.out.println("Could not find member with ID: " + memberID);
            }

        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public void updateMember(Member member){
        String sql = "UPDATE Members SET name = ?, gender = ?, birthday = ?, membershipTypeID = ? WHERE memberID = ?";

        try(PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setString(1, member.getName());
            statement.setString(2, member.getGender().name());
            statement.setDate(3, Date.valueOf(member.getBirthday()));
            statement.setInt(4, member.getMembershipTypeID());
            statement.setInt(5, member.getMemberID());

            int affectedRows = statement.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Member updated successfuly!");
            } else {
                System.out.println("Could not find member with ID: " + member.getMemberID());
            }

        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }


    // Membership Management

    /**
     * Updates the membership fee for a specific membership type.
     * @param typeID the ID of the membership type to update
     * @param newFee the new fee amount to set
     */
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

    /**
     * Retrieves a membership type from the database based on its type ID.
     * @param typeID the ID of the membership type to retrieve
     * @return the retrieved membership type, or null if not found
     */
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

    /**
     * Retrieves the membership type for a specific member.
     * @param memberID the ID of the member whose membership type is to be retrieved
     * @return the membership type of the specified member, or null if not found
     */
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

    // Swimmer Management

    /**
     * Adds a new swimmer to the database and assigns them to a team.
     * @param swimmer the swimmer to add
     * @param teamID the ID of the team to which the swimmer will be assigned
     */
    public void addNewSwimmer(Swimmer swimmer, int teamID){

        String sql = "INSERT INTO Swimmers (memberID, teamID) VALUES (?, ?)";

        Member member = new Member(swimmer.getName(),swimmer.getGender(),swimmer.getBirthday(),false);
        addMember(member);
        int memberID = member.getMemberID();
        swimmer.setMemberID(memberID);

        try{
            if (memberID!= 0){
                PreparedStatement pstmt = connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
                pstmt.setInt(1, memberID);
                pstmt.setInt(2,teamID);
                int swimmerAffected = pstmt.executeUpdate();

                if (swimmerAffected==1){
                    ResultSet rs = pstmt.getGeneratedKeys();
                    if (rs.next()){
                        swimmer.setSwimmerID(rs.getInt(1));
                    }
                }
            } else throw new SQLException("Member could not be added");


        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    /**
     * Adds a swimmer to the database from an existing member and assigns them to a team.
     * @param memberID the ID of the member who is to be added as a swimmer
     * @param teamID the ID of the team to which the swimmer will be assigned
     * @return the ID of the newly added swimmer, or 0 if the operation fails
     */
    public int addSwimmerFromExistingMember(int memberID,int teamID){
        String sql = "INSERT INTO Swimmers (memberID, teamID) VALUES (?, ?)";
        int swimmerID=0;
        try(PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
            pstmt.setInt(1, memberID);
            pstmt.setInt(2, teamID);
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows>0){
                ResultSet rs = pstmt.getGeneratedKeys();
                if (rs.next()){
                    swimmerID = rs.getInt(1);
                }

                System.out.println("New swimmer added successfully for memberID: " + memberID);
            }
            else System.out.println("Failed to add new swimmer for memberID: " + memberID);
        }catch (SQLException e){
            System.out.println("Error adding new swimmer: " + e.getMessage());
        }
        return swimmerID;
    }

    /**
     * Retrieves a swimmer from the database based on the swimmer ID.
     * @param swimmerID the ID of the swimmer to retrieve
     * @return the retrieved swimmer, or null if not found
     */
    public Swimmer getSwimmer(int swimmerID){
        String sql = "SELECT Swimmers.swimmerID, Swimmers.teamID, Members.memberID, Members.name, Members.gender, Members.birthday, Members.membershipTypeID, MembershipTypes.description, MembershipTypes.fee " +
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
                Gender gender = Gender.valueOf(rs.getString("gender"));
                LocalDate birthday = rs.getDate("birthday").toLocalDate();
                int membershipTypeID = rs.getInt("membershipTypeID");
                String description = rs.getString("description");
                double fee = rs.getDouble("fee");

                swimmer = new Swimmer(swimmerID,memberID,name, gender,birthday,new MembershipType(membershipTypeID,description,fee));
            }
        }catch (SQLException e){
            System.out.println("Error retrieving swimmer: " + e.getMessage());
        }
        return swimmer;
    }

    /**
     * Retrieves all swimmers from the database.
     * @return a list of all swimmers
     */
    public List<Swimmer> getSwimmers(){
        String sql = "SELECT Swimmers.swimmerID, Swimmers.teamID, Members.memberID, Members.name, Members.gender, Members.birthday, Members.membershipTypeID, MembershipTypes.description, MembershipTypes.fee " +
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
                Gender gender = Gender.valueOf(rs.getString("gender"));
                LocalDate birthday = rs.getDate("birthday").toLocalDate();
                int membershipTypeID = rs.getInt("membershipTypeID");
                String description = rs.getString("description");
                double fee = rs.getDouble("fee");

                Swimmer swimmer = new Swimmer(swimmerID,memberID,name, gender,birthday,new MembershipType(membershipTypeID,description,fee));
                swimmers.add(swimmer);

            }
        }catch (SQLException e){
            System.out.println("Error retrieving swimmers: " + e.getMessage());
        }
        return swimmers;
    }

    public void removeSwimmer(int swimmerID){
        String sql = "DELETE FROM Swimmers WHERE swimmerID = ?";

        try(PreparedStatement pstmt = connection.prepareStatement(sql)){
            pstmt.setInt(1, swimmerID);
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Swimmer deleted successfuly!");
            } else {
                System.out.println("Could not find swimmer with ID: " + swimmerID);
            }

        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    // Discipline Management

    /**
     * Adds a new discipline to the database.
     * @param name the name of the discipline to add
     */
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

    /**
     * Adds a discipline to a specific swimmer in the database.
     * @param swimmerID the ID of the swimmer
     * @param disciplineID the ID of the discipline to be added
     */
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

    public void removeSwimmerDiscipline(int swimmerID, int disciplineID){
        String sql ="DELETE FROM SwimmersDisciplines " +
                "WHERE swimmerID = ? AND disciplineID = ?";
        try(PreparedStatement pstmt = connection.prepareStatement(sql)){
            pstmt.setInt(1,swimmerID);
            pstmt.setInt(2, disciplineID);
            pstmt.executeUpdate();

        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    /**
     * Retrieves a discipline from the database based on the discipline ID.
     * @param disciplineID the ID of the discipline to retrieve
     * @return the retrieved discipline, or null if not found
     */
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

    /**
     * Retrieves all disciplines from the database.
     * @return a list of all disciplines
     */
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

    /**
     * Retrieves all disciplines for a specific swimmer.
     * @param swimmerID the ID of the swimmer whose disciplines are to be retrieved
     * @return a list of disciplines associated with the specified swimmer
     */
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

    // Record Management

    /**
     * Adds a new performance record to the database.
     * @param record the performance record to add
     */
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

    /**
     * Retrieves all performance records for a specific swimmer.
     * @param swimmerID the ID of the swimmer whose records are to be retrieved
     * @return a list of performance records for the specified swimmer
     */
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

    // Invoice Management

    public void addInvoice(Invoice invoice){
        String sql = "INSERT INTO Invoice (amount, memberID, paid, dueDate) VALUES (?, ?, ?, ?)";
        try(PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setDouble(1, invoice.getAmount());
            pstmt.setInt(2,invoice.getMemberID());
            pstmt.setBoolean(3,invoice.isPaid());
            pstmt.setDate(4,Date.valueOf(invoice.getDueDate()));
            int Affected = pstmt.executeUpdate();
            if (Affected==1){
                ResultSet rs = pstmt.getGeneratedKeys();
                if (rs.next()){
                    invoice.setInvoiceID(rs.getInt(1));
                }
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }

    }

    public List<Invoice> getInvoicesForMember(int memberID){
        List<Invoice> invoices = new ArrayList<>();
        String sql = "SELECT * FROM Invoice WHERE memberID = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, memberID);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                long invoiceID = resultSet.getLong("invoiceID");
                double amount = resultSet.getDouble("amount");
                boolean paid = resultSet.getBoolean("paid");
                LocalDate dueDate = resultSet.getDate("dueDate").toLocalDate();
                invoices.add(new Invoice(amount, invoiceID, memberID, paid, dueDate));
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return invoices;
    }

    public void markInvoiceAsPaid(long invoiceID){
        String sql = "UPDATE Invoice SET paid = ? WHERE invoiceID = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setBoolean(1, true);
            statement.setLong(2, invoiceID);
            statement.executeUpdate();
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    // DB Connection Management

    /**
     * Closes the database connection if it is open.
     */
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

    /**
     * Reconnects to the database if the connection has been closed.
     */
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
