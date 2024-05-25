package tablesCreation;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import users.Roles;
import users.User;

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
                            + "role VARCHAR(20) NOT NULL" // Modificați tipul câmpului
                            // isAdministrator
                            + ")";
            statement.execute(createUsersTable);
            // System.out.println("Users table created successfully");

        } catch (Exception e) {
            System.out.println("Error creating users table: " + e.getMessage());
        }
    }

    public static void insertUserData() {
        String[][] exampleData = {
            {"jenny567", "password123", "jenny_smith@example.com", "ADMINISTRATOR"},
            {"alex89", "qwerty", "alex_doe@hotmail.com", "USER"},
            {"sarah2000", "myp@ssw0rd", "sarah_lee22@yahoo.com", "USER"},
            {"chrisBrown", "brownie123", "cbrown@example.org", "USER"},
            {"lisa_m", "ilovecoding", "lisa.miller@gmail.com", "USER"},
            {"michael87", "mikeiscool", "mike87@hotmail.com", "USER"},
            {"emily99", "hello1234", "emily_rose@example.com", "USER"},
            {"davidP", "davidpass", "david.parker@yahoo.com", "USER"},
            {"sam_cool", "samcool123", "sam.cool@example.org", "USER"},
            {"hannah22", "p@ssHannah", "hannah.smith22@gmail.com", "USER"},
            {"johnDoe", "doe123", "john.doe@example.com", "USER"},
            {"sophia1", "sophi@123", "sophia01@hotmail.com", "USER"},
            {"adamSmith", "adam1234", "adam.smith@example.org", "USER"},
            {"laura_m", "lauraPassword", "laura.m@example.com", "USER"},
            {"steveJobs", "apples123", "steve.jobs@apple.com", "USER"},
            {"olivia33", "livPass", "olivia33@gmail.com", "USER"},
            {"ryan1988", "ryanpass", "ryan1988@hotmail.com", "USER"},
            {"graceW", "gracepass123", "grace.w@example.com", "USER"},
            {"nathan99", "nathanp@ss", "nathan99@yahoo.com", "USER"},
            {"samantha_s", "sammy123", "samantha_s@example.org", "USER"},
            {"peterParker", "spiderman", "peter.parker@example.com", "USER"},
            {"elizabeth88", "lizzyPass", "elizabeth88@gmail.com", "USER"},
            {"kevinSmith", "kevinpass123", "kevin.smith@example.org", "USER"},
            {"rachelG", "rachel1234", "rachel.g@example.com", "USER"},
            {"justinTime", "justinpass", "justin.time@hotmail.com", "USER"},
            {"amber_w", "amber123", "amber.w@example.org", "USER"},
            {"daniel2020", "danielPass", "daniel2020@gmail.com", "USER"},
            {"taylor_s", "taytay123", "taylor_s@example.com", "USER"},
            {"victoria99", "vickyPass", "victoria99@yahoo.com", "USER"}
        };

        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            String sql = "INSERT INTO Users (username, password, email, role) VALUES (?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);

            // Execute the insertion for each set of example data
            for (String[] data : exampleData) {
                statement.setString(1, data[0]);
                statement.setString(2, data[1]);
                statement.setString(3, data[2]);
                statement.setString(4, data[3]); // Set the user's role
                statement.executeUpdate();
            }

            // System.out.println("Data inserted successfully into the database.");
        } catch (SQLException e) {
            System.err.println("Error connecting to or querying the database: " + e.getMessage());
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
                Roles role =
                        Roles.valueOf(
                                resultSet.getString("role")); // Get the role from the database
                User user = new User(userId, username, password, email, role);
                users.add(user);
            }
        } catch (SQLException e) {
            System.err.println("Error connecting to or querying the database: " + e.getMessage());
        }
        return users;
    }

    public void insertUser(User newUser) {
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            String sql = "INSERT INTO Users (username, password, email, role) VALUES (?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1, newUser.getUsername());
            statement.setString(2, newUser.getPassword());
            statement.setString(3, newUser.getEmail());
            statement.setString(4, newUser.getRole().name());
            statement.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Error inserting user: " + e.getMessage());
        }
    }

    public void updateUser(User promotedUser) {
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            String sql = "UPDATE Users SET role = ? WHERE username = ?";
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1, Roles.ADMINISTRATOR.name()); // Update the user's role
            statement.setString(2, promotedUser.getUsername());
            statement.executeUpdate();

            // System.out.println("User " + promotedUser.getUsername() + " updated successfully");
        } catch (SQLException e) {
            System.err.println("Error updating user: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        createUsers();
        insertUserData();
    }
}
