import static readFileData.CreateFile.createFile;

import authentication.*;
import exceptions.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import playlistManager.*;
import programView.Program;
import programView.ProgramRunner;
import readFileData.JsonUtility;
import readFileData.XmlUtility;
import songRepository.Song;
import songsManager.*;
import tablesCreation.SongsCreation;
import tablesCreation.UsersCreation;
import users.User;

public class Main {
    public static void main(String[] args) {
        createFile("users.json");
        createFile("songs.xml");

        List<User> users = new ArrayList<>(UsersCreation.getAllUsers());
        List<Song> songs = new ArrayList<>(SongsCreation.getAllSongs());

        try {
            JsonUtility<User> jsonUserUtility = new JsonUtility<>(User.class);
            jsonUserUtility.writeData(users, "users.json");
        } catch (IOException e) {
            System.out.println(
                    "Error occurred while writing users data to file: " + e.getMessage());
        }

        XmlUtility<Song> xmlUtility = new XmlUtility<>(Song.class);

        String filename = "songs.xml";

        try {
            xmlUtility.writeData(songs, filename);
        } catch (IOException e) {
            System.err.println(
                    "Error occurred while writing songs data to file: " + e.getMessage());
        }

        ProgramRunner program = new Program();
        program.start();
    }
}
