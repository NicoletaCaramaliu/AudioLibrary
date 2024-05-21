package tablesCreation;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import playlistManager.Playlist;
import songsManager.Song;

public class PlaylistCreation extends DatabaseConnection{

    public static void createPlaylistsTable() {
        try {
            Connection connection = DatabaseConnection.getCurrentConnection();
            assert connection != null;
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
            Connection connection = DatabaseConnection.getCurrentConnection();
            assert connection != null;
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


    public static List<Playlist> getAllPlaylists() {
        List<Playlist> playlists = new ArrayList<>();
        try {
            Connection connection = DatabaseConnection.getCurrentConnection();
            assert connection != null;
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
        try (Connection connection = DatabaseConnection.getCurrentConnection()) {
            String sql = "INSERT INTO Playlists ( playlist_name, user_id) VALUES (?, ?)";
            assert connection != null;
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1, newPlaylist.getName());
            statement.setInt(2, newPlaylist.getUserId());
            statement.executeUpdate();

            System.out.println("Playlist " + newPlaylist.getName() + " inserted successfully");
        } catch (SQLException e) {
            System.out.println("Error inserting playlist: " + e.getMessage());
        }
    }

    public void insertPlaylistSong(int playlistId, int songId) {
        try (Connection connection = DatabaseConnection.getCurrentConnection()) {
            String sql = "INSERT INTO PlaylistSongs (playlist_id, song_id) VALUES (?, ?)";
            assert connection != null;
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, playlistId);
            statement.setInt(2, songId);
            statement.executeUpdate();
            System.out.println("Song with ID " + songId + " added to playlist with ID " + playlistId + " successfully.");
        } catch (SQLException e) {
            System.out.println("Error inserting song into playlist: " + e.getMessage());
        }
    }

    public List<Song> getSongsForPlaylist(int playlistId) {
        List<Song> songs = new ArrayList<>();
        try {
            Connection connection = DatabaseConnection.getCurrentConnection();
            assert connection != null;
            String query = "SELECT s.song_id, s.title, s.artist, s.release_year " +
                    "FROM PlaylistSongs ps " +
                    "JOIN Songs s ON ps.song_id = s.song_id " +
                    "WHERE ps.playlist_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, playlistId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int songId = resultSet.getInt("song_id");
                String title = resultSet.getString("title");
                String artist = resultSet.getString("artist");
                int releaseYear = resultSet.getInt("release_year");
                songs.add(new Song(songId, title, artist, releaseYear));
            }
        } catch (SQLException e) {
            System.out.println("Error getting songs for playlist: " + e.getMessage());
        }
        return songs;
    }

    public List<Playlist> getUserPlaylists(int userId) {
        List<Playlist> playlists = new ArrayList<>();
        try {
            Connection connection = DatabaseConnection.getCurrentConnection();
            assert connection != null;

            // Obținerea playlisturilor utilizatorului
            String playlistQuery = "SELECT * FROM Playlists WHERE user_id = ?";
            PreparedStatement playlistStmt = connection.prepareStatement(playlistQuery);
            playlistStmt.setInt(1, userId);
            ResultSet playlistRS = playlistStmt.executeQuery();

            while (playlistRS.next()) {
                int playlistId = playlistRS.getInt("playlist_id");
                String playlistName = playlistRS.getString("playlist_name");

                Playlist playlist = new Playlist(playlistId, playlistName, userId);

                // Obținerea melodiilor pentru fiecare playlist
                String songQuery = "SELECT s.song_id, s.song_name, s.artist, s.release_year " +
                        "FROM PlaylistSongs ps " +
                        "JOIN Songs s ON ps.song_id = s.song_id " +
                        "WHERE ps.playlist_id = ?";
                PreparedStatement songStmt = connection.prepareStatement(songQuery);
                songStmt.setInt(1, playlistId);
                ResultSet songRS = songStmt.executeQuery();

                while (songRS.next()) {
                    int songId = songRS.getInt("song_id");
                    String title = songRS.getString("song_name");
                    String artist = songRS.getString("artist");
                    int releaseYear = songRS.getInt("release_year");

                    Song song = new Song(songId, title, artist, releaseYear);
                    playlist.addSong(song);
                }

                playlists.add(playlist);
            }
        } catch (SQLException e) {
            System.out.println("Error getting playlists for user: " + e.getMessage());
        }
        return playlists;
    }

}
