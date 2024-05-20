package tablesCreation;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import usersClasses.User;

public class UsersCreation {
    private static final String URL = "jdbc:mysql://localhost:3306/laborator";
    private static final String USERNAME = "student";
    private static final String PASSWORD = "student";

    public static void createUsers() {
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
                Statement statement = connection.createStatement()) {

            // CREATE TABLE USERS
            String createUsersTable =
                    "CREATE TABLE IF NOT EXISTS Users ("
                            + "userId INT AUTO_INCREMENT PRIMARY KEY,"
                            + "username VARCHAR(50) UNIQUE NOT NULL,"
                            + "password VARCHAR(50) NOT NULL,"
                            + "email VARCHAR(50) NOT NULL,"
                            + "isAdministrator BOOLEAN DEFAULT FALSE"
                            + ")";
            statement.execute(createUsersTable);
            // System.out.println("Users table created successfully");

        } catch (Exception e) {
            System.out.println("Error creating users table: " + e.getMessage());
        }
    }

    public static void insertUserData() {
        String[][] exampleData = {
            {"jenny567", "password123", "jenny_smith@example.com"},
            {"alex89", "qwerty", "alex_doe@hotmail.com"},
            {"sarah2000", "myp@ssw0rd", "sarah_lee22@yahoo.com"},
            {"chrisBrown", "brownie123", "cbrown@example.org"},
            {"lisa_m", "ilovecoding", "lisa.miller@gmail.com"},
            {"michael87", "mikeiscool", "mike87@hotmail.com"},
            {"emily99", "hello1234", "emily_rose@example.com"},
            {"davidP", "davidpass", "david.parker@yahoo.com"},
            {"sam_cool", "samcool123", "sam.cool@example.org"},
            {"hannah22", "p@ssHannah", "hannah.smith22@gmail.com"},
            {"johnDoe", "doe123", "john.doe@example.com"},
            {"sophia1", "sophi@123", "sophia01@hotmail.com"},
            {"adamSmith", "adam1234", "adam.smith@example.org"},
            {"laura_m", "lauraPassword", "laura.m@example.com"},
            {"steveJobs", "apples123", "steve.jobs@apple.com"},
            {"olivia33", "livPass", "olivia33@gmail.com"},
            {"ryan1988", "ryanpass", "ryan1988@hotmail.com"},
            {"graceW", "gracepass123", "grace.w@example.com"},
            {"nathan99", "nathanp@ss", "nathan99@yahoo.com"},
            {"samantha_s", "sammy123", "samantha_s@example.org"},
            {"peterParker", "spiderman", "peter.parker@example.com"},
            {"elizabeth88", "lizzyPass", "elizabeth88@gmail.com"},
            {"kevinSmith", "kevinpass123", "kevin.smith@example.org"},
            {"rachelG", "rachel1234", "rachel.g@example.com"},
            {"justinTime", "justinpass", "justin.time@hotmail.com"},
            {"amber_w", "amber123", "amber.w@example.org"},
            {"daniel2020", "danielPass", "daniel2020@gmail.com"},
            {"taylor_s", "taytay123", "taylor_s@example.com"},
            {"victoria99", "vickyPass", "victoria99@yahoo.com"}
        };

        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            String sql = "INSERT INTO Users (username, password, email) VALUES (?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);

            // Executarea inserarii pentru fiecare exemplu de date
            for (String[] data : exampleData) {
                statement.setString(1, data[0]);
                statement.setString(2, data[1]);
                statement.setString(3, data[2]);
                statement.executeUpdate();
            }

            // System.out.println("Datele au fost inserate cu succes in baza de date.");
        } catch (SQLException e) {
            System.err.println(
                    "Eroare la conectarea sau interogarea bazei de date: " + e.getMessage());
        }
    }

    public static List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
                Statement statement = connection.createStatement()) {
            String sql = "SELECT * FROM Users";
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                int userId = resultSet.getInt("userId");
                String username = resultSet.getString("username");
                String password = resultSet.getString("password");
                String email = resultSet.getString("email");
                boolean isAdministrator = resultSet.getBoolean("isAdministrator");

                User user = new User(userId, username, password, email, isAdministrator);
                users.add(user);
            }
        } catch (SQLException e) {
            System.err.println(
                    "Eroare la conectarea sau interogarea bazei de date: " + e.getMessage());
        }
        return users;
    }

    public void insertUser(User newUser) {
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            String sql =
                    "INSERT INTO Users (username, password, email, isAdministrator) VALUES (?, ?,"
                            + " ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1, newUser.getUsername());
            statement.setString(2, newUser.getPassword());
            statement.setString(3, newUser.getEmail());
            statement.setBoolean(4, newUser.getIsAdministrator());
            statement.executeUpdate();

            // System.out.println("User " + newUser.getUsername() + " inserted successfully");
        } catch (SQLException e) {
            System.err.println("Error inserting user: " + e.getMessage());
        }
    }

    public void updateUser(User promotedUser) {
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            String sql = "UPDATE Users SET isAdministrator = ? WHERE username = ?";
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setBoolean(1, true);
            statement.setString(2, promotedUser.getUsername());
            statement.executeUpdate();

            // System.out.println("User " + promotedUser.getUsername() + " updated successfully");
        } catch (SQLException e) {
            System.err.println("Error updating user: " + e.getMessage());
        }
    }
}
