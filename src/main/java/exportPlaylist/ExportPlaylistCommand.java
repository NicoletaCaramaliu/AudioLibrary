package exportPlaylist;

import authentication.Command;
import authentication.SessionManager;
import exceptions.InvalidExportFormatException;
import exceptions.InvalidPlaylistException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;
import playlistRepository.Playlist;
import sessionVerification.BaseCommand;
import songRepository.Song;
import tablesCreation.PlaylistCreation;

public class ExportPlaylistCommand extends BaseCommand implements Command {
    private final int currentUserId;
    private final PlaylistCreation playlistCreation;
    private final String currentUserName;
    private final SessionManager session;

    public ExportPlaylistCommand(
            int currentUserId,
            PlaylistCreation playlistCreation,
            String currentUserName,
            SessionManager session) {
        super(session);
        this.currentUserId = currentUserId;
        this.playlistCreation = playlistCreation;
        this.currentUserName = currentUserName;
        this.session = session;
    }

    @Override
    public void execute() {
        Scanner scanner = new Scanner(System.in);
        requireLoggedIn();

        System.out.print("Enter playlist name or ID: ");
        String playlistIdentifier = scanner.nextLine();

        System.out.print("Enter export format (CSV/JSON): ");
        String formatString = scanner.nextLine().toUpperCase();

        ExportFormat format;
        try {
            format = ExportFormat.valueOf(formatString);
        } catch (IllegalArgumentException e) {
            throw new InvalidExportFormatException(
                    "Invalid format specified. Please enter CSV or JSON.");
        }

        Playlist playlist = getPlaylist(playlistIdentifier);
        if (playlist == null) {
            throw new InvalidPlaylistException(
                    "Playlist " + playlistIdentifier + " does not exist!");
        }

        try {
            String filename = generateFilename(playlist.getName(), format);
            switch (format) {
                case CSV:
                    exportToCSV(playlist, filename);
                    break;
                case JSON:
                    exportToJSON(playlist, filename);
                    break;
            }
            System.out.println("Playlist exported to " + filename);
        } catch (IOException e) {
            System.out.println("Error exporting playlist: " + e.getMessage());
        }
    }

    private Playlist getPlaylist(String playlistIdentifier) {
        List<Playlist> userPlaylists = playlistCreation.getUserPlaylists(currentUserId);
        for (Playlist playlist : userPlaylists) {
            if (playlist.getName().equals(playlistIdentifier)
                    || String.valueOf(playlist.getId()).equals(playlistIdentifier)) {
                return playlist;
            }
        }
        return null;
    }

    private String generateFilename(String playlistName, ExportFormat format) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String date = LocalDate.now().format(formatter);
        return "export_"
                + currentUserName
                + "_"
                + playlistName.replaceAll("\\s+", "_")
                + "_"
                + date
                + "."
                + format.name().toLowerCase();
    }

    private void exportToCSV(Playlist playlist, String filename) throws IOException {
        try (FileWriter writer = new FileWriter(filename)) {
            writer.write("Playlist: " + playlist.getName() + "\n");
            writer.write("Name,Artist,Release Year\n");
            for (Song song : playlist.getSongs()) {
                writer.write(song.title() + "," + song.artist() + "," + song.releaseYear() + "\n");
            }
        }
    }

    private void exportToJSON(Playlist playlist, String filename) throws IOException {
        try (FileWriter writer = new FileWriter(filename)) {
            writer.write("{\n");
            writer.write("  \"playlist\": \"" + playlist.getName() + "\",\n");
            writer.write("  \"songs\": [\n");
            List<Song> songs = playlist.getSongs();
            for (int i = 0; i < songs.size(); i++) {
                Song song = songs.get(i);
                writer.write("    {\n");
                writer.write("      \"name\": \"" + song.title() + "\",\n");
                writer.write("      \"artist\": \"" + song.artist() + "\",\n");
                writer.write("      \"releaseYear\": " + song.releaseYear() + "\n");
                writer.write("    }");
                if (i < songs.size() - 1) {
                    writer.write(",");
                }
                writer.write("\n");
            }
            writer.write("  ]\n");
            writer.write("}\n");
        }
    }
}
