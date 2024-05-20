package tablesCreation;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import playlistManager.Playlist;

public class PlaylistCreation {
    private static final String URL = "jdbc:mysql://localhost:3306/laborator";
    private static final String USERNAME = "student";
    private static final String PASSWORD = "student";

    public static void createPlaylistsTable() {
        try {
            Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            Statement statement = connection.createStatement();
            String createTableSQL =
                    "CREATE TABLE IF NOT EXISTS Playlists ("
                            + "playlist_id INT AUTO_INCREMENT PRIMARY KEY,"
                            + "playlist_name VARCHAR(100) NOT NULL,"
                            + "user_id INT NOT NULL,"
                            + "FOREIGN KEY (user_id) REFERENCES Users(userId),"
                            + "UNIQUE(playlist_name, user_id)"
                            + ")";
            statement.execute(createTableSQL);
        } catch (SQLException e) {
            System.out.println("Error creating playlists table: " + e.getMessage());
        }
    }

    public static void createPlaylistSongsTable() {
        try {
            Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            Statement statement = connection.createStatement();
            String createTableSQL =
                    "CREATE TABLE IF NOT EXISTS PlaylistSongs ("
                            + "playlist_id INT,"
                            + "song_id INT,"
                            + "PRIMARY KEY (playlist_id, song_id),"
                            + "FOREIGN KEY (playlist_id) REFERENCES Playlists(playlist_id),"
                            + "FOREIGN KEY (song_id) REFERENCES Songs(song_id)"
                            + ")";
            statement.execute(createTableSQL);
        } catch (SQLException e) {
            System.out.println("Error creating playlist songs table: " + e.getMessage());
        }
    }

    public static void createPlaylist(String playlistName, int userId) {
        try {
            Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            String insertSQL = "INSERT INTO Playlists (playlist_name, user_id) VALUES (?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(insertSQL);
            preparedStatement.setString(1, playlistName);
            preparedStatement.setInt(2, userId);
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Playlist " + playlistName + " was created successfully!");
            } else {
                System.out.println("Failed to create playlist " + playlistName);
            }
        } catch (SQLException e) {
            System.out.println("Error creating playlist: " + e.getMessage());
        }
    }

    public static List<Playlist> getAllPlaylists() {
        List<Playlist> playlists = new ArrayList<>();
        try {
            Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            String query = "SELECT * FROM Playlists";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int playlistId = resultSet.getInt("playlist_id");
                String playlistName = resultSet.getString("playlist_name");
                int userId = resultSet.getInt("user_id");
                Playlist playlist = new Playlist(playlistId, playlistName, userId);
                playlists.add(playlist);
            }
        } catch (SQLException e) {
            System.out.println("Error getting playlists: " + e.getMessage());
        }
        return playlists;
    }

    public void insertPlaylist(Playlist newPlaylist) {
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            String sql = "INSERT INTO Playlists ( playlist_name, user_id) VALUES (?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1, newPlaylist.getName());
            statement.setInt(2, newPlaylist.getUserId());
            statement.executeUpdate();

            System.out.println("Playlist " + newPlaylist.getName() + " inserted successfully");
        } catch (SQLException e) {
            System.err.println("Error inserting playlist: " + e.getMessage());
        }
    }
}
