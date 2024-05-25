package tablesCreation;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import songRepository.Song;

public class SongsCreation {
    private static final String URL = "jdbc:mysql://localhost:3306/laborator";
    private static final String USERNAME = "student";
    private static final String PASSWORD = "student";

    public static void createSongsTable() {
        try {
            Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            Statement statement = connection.createStatement();
            String createTableSQL =
                    "CREATE TABLE IF NOT EXISTS Songs ("
                            + "song_id INT PRIMARY KEY,"
                            + "song_name VARCHAR(100) NOT NULL,"
                            + "artist VARCHAR(100) NOT NULL,"
                            + "release_year INT NOT NULL,"
                            + "UNIQUE(song_name, artist)"
                            + ")";
            statement.execute(createTableSQL);
        } catch (SQLException e) {
            System.out.println("Error creating table: " + e.getMessage());
        }
    }

    public static void insertSongData() {
        try {
            Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            Statement statement = connection.createStatement();

            String insertDataSQL =
                    "INSERT INTO Songs (song_id,song_name, artist, release_year) VALUES "
                            + "(1,'Shape of You', 'Ed Sheeran', 2017),"
                            + "(2,'Perfect', 'Ed Sheeran', 2017),"
                            + "(3,'Thinking Out Loud', 'Ed Sheeran', 2014),"
                            + "(4,'Billie Jean', 'Michael Jackson', 1983),"
                            + "(5,'Beat It', 'Michael Jackson', 1982),"
                            + "(6,'Smooth Criminal', 'Michael Jackson', 1987),"
                            + "(7,'Bohemian Rhapsody', 'Queen', 1975),"
                            + "(8,'We Will Rock You', 'Queen', 1977),"
                            + "(9,'Under Pressure', 'Queen', 1981),"
                            + "(10,'Hey Jude', 'The Beatles', 1968),"
                            + "(11,'Let It Be', 'The Beatles', 1970),"
                            + "(12,'Sweet Child o'' Mine', 'Guns N'' Roses', 1987),"
                            + "(13,'November Rain', 'Guns N'' Roses', 1992),"
                            + "(14,'Welcome to the Jungle', 'Guns N'' Roses', 1987),"
                            + "(15,'Hotel California', 'Eagles', 1977),"
                            + "(16,'Take It Easy', 'Eagles', 1972),"
                            + "(17,'Rolling in the Deep', 'Adele', 2010),"
                            + "(18,'Someone Like You', 'Adele', 2011),"
                            + "(19,'Set Fire to the Rain', 'Adele', 2011),"
                            + "(20,'Smells Like Teen Spirit', 'Nirvana', 1991),"
                            + "(21,'Come as You Are', 'Nirvana', 1991),"
                            + "(22,'Heart-Shaped Box', 'Nirvana', 1993),"
                            + "(23,'Like a Rolling Stone', 'Bob Dylan', 1965),"
                            + "(24,'Blowin'' in the Wind', 'Bob Dylan', 1963),"
                            + "(25,'Hurt', 'Johnny Cash', 2002),"
                            + "(26,'Ring of Fire', 'Johnny Cash', 1963),"
                            + "(27,'I Walk the Line', 'Johnny Cash', 1956),"
                            + "(28,'Wonderwall', 'Oasis', 1995),"
                            + "(29,'Champagne Supernova', 'Oasis', 1995),"
                            + "(30,'Don''t Look Back in Anger', 'Oasis', 1996),"
                            + "(31,'Stairway to Heaven', 'Led Zeppelin', 1971),"
                            + "(32,'Whole Lotta Love', 'Led Zeppelin', 1969),"
                            + "(33,'Kashmir', 'Led Zeppelin', 1975),"
                            + "(34,'Every Breath You Take', 'The Police', 1983),"
                            + "(35,'Roxanne', 'The Police', 1978),"
                            + "(36,'Message in a Bottle', 'The Police', 1979),"
                            + "(37,'Yellow', 'Coldplay', 2000),"
                            + "(38,'Clocks', 'Coldplay', 2002),"
                            + "(39,'Fix You', 'Coldplay', 2005)";

            statement.executeUpdate(insertDataSQL);
            System.out.println("Data inserted successfully.");
        } catch (SQLException e) {
            System.out.println("Error inserting data: " + e.getMessage());
        }
    }

    public static List<Song> getAllSongs() {
        List<Song> songs = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
                Statement statement = connection.createStatement()) {
            String sql = "SELECT * FROM Songs";
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                int songId = resultSet.getInt("song_id");
                String songName = resultSet.getString("song_name");
                String artist = resultSet.getString("artist");
                int releaseYear = resultSet.getInt("release_year");

                Song song = new Song(songId, songName, artist, releaseYear);
                songs.add(song);
            }
        } catch (SQLException e) {
            System.err.println(
                    "Error connecting to database or querying database: " + e.getMessage());
        }
        return songs;
    }

    // CREATE NEW SONG
    public void insertSong(Song newSong) {
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            String sql =
                    "INSERT INTO Songs (song_id, song_name, artist, release_year) VALUES (?,?, ?,"
                            + " ?)";
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setInt(1, newSong.songId());
            statement.setString(2, newSong.title());
            statement.setString(3, newSong.artist());
            statement.setInt(4, newSong.releaseYear());
            statement.executeUpdate();

            System.out.println("Song " + newSong.title() + " inserted successfully");
        } catch (SQLException e) {
            System.err.println("Error inserting song: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        createSongsTable();
        insertSongData();

    }
}
