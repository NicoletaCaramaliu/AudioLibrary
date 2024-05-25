package exportPlaylist;

import authentication.Command;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import playlistRepository.Playlist;
import songRepository.Song;
import tablesCreation.PlaylistCreation;

public class ExportPlaylistCommand implements Command {
    private final String playlistIdentifier;
    private final ExportFormat format;
    private final int currentUserId;
    private final PlaylistCreation playlistCreation;
    private final String currentUserName;

    public ExportPlaylistCommand(
            String playlistIdentifier,
            ExportFormat format,
            int currentUserId,
            PlaylistCreation playlistCreation,
            String currentUserName) {
        this.playlistIdentifier = playlistIdentifier;
        this.format = format;
        this.currentUserId = currentUserId;
        this.playlistCreation = playlistCreation;
        this.currentUserName = currentUserName;
    }

    @Override
    public void execute() {
        Playlist playlist = getPlaylist();
        if (playlist == null) {
            System.out.println("Playlist " + playlistIdentifier + " does not exist!");
            return;
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

    private Playlist getPlaylist() {
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
                + playlistName
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
