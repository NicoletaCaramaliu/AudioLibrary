package tablesCreation;

import auditManager.Audit;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class AuditCreation extends DatabaseConnection {

    public static void createAuditTable() {
        try {
            Connection connection = DatabaseConnection.getCurrentConnection();
            assert connection != null;
            Statement statement = connection.createStatement();
            String createTableAudit =
                    "CREATE TABLE IF NOT EXISTS Audit ("
                            + "audit_id INT PRIMARY KEY AUTO_INCREMENT,"
                            + "username VARCHAR(50) NOT NULL,"
                            + "command VARCHAR(50) NOT NULL,"
                            + "success BOOLEAN NOT NULL"
                            + ")";
            statement.execute(createTableAudit);
        } catch (Exception e) {
            System.out.println("Error creating audit table: " + e.getMessage());
        }
    }

    public static List<Audit> getAudits() {
        List<Audit> audits = new ArrayList<>();
        try {
            Connection connection = DatabaseConnection.getCurrentConnection();
            assert connection != null;
            Statement statement = connection.createStatement();
            String getAuditsSQL = "SELECT * FROM Audit";
            ResultSet resultSet = statement.executeQuery(getAuditsSQL);
            while (resultSet.next()) {
                int auditId = resultSet.getInt("audit_id");
                String username = resultSet.getString("username");
                String command = resultSet.getString("command");
                boolean success = resultSet.getBoolean("success");
                Audit audit = new Audit(auditId, username, command, success);
                audits.add(audit);
            }
        } catch (Exception e) {
            System.out.println("Error getting audits: " + e.getMessage());
        }
        return audits;
    }

    public static void insertAudit(String username, String command, boolean success) {
        try {
            Connection connection = DatabaseConnection.getCurrentConnection();
            String insertAuditSQL =
                    "INSERT INTO Audit (username, command, success) VALUES (?, ?, ?)";
            assert connection != null;
            PreparedStatement preparedStatement = connection.prepareStatement(insertAuditSQL);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, command);
            preparedStatement.setBoolean(3, success);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error inserting audit record: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        createAuditTable();
    }
}
