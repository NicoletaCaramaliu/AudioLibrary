package playlistManager;

import authentication.SessionManager;
import exceptions.InvalidPlaylistException;
import java.util.Scanner;
import playlistRepository.Playlist;
import playlistRepository.PlaylistRepository;
import tablesCreation.PlaylistCreation;

public class CreatePlaylistCommand extends PlaylistCommand {
    public CreatePlaylistCommand(
            SessionManager sessionManager,
            PlaylistCreation playlistCreation,
            PlaylistRepository playlistRepository) {
        super(sessionManager, playlistCreation, playlistRepository);
    }

    @Override
    public void execute() {
        requireLoggedIn();

        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter playlist name: ");
        String playlistName = scanner.nextLine();

        if (playlistRepository.playlistExists(
                playlistName, sessionManager.getCurrentUser().getUserId())) {
            throw new InvalidPlaylistException(
                    "Playlist with name " + playlistName + " already exists.");
        }

        Playlist newPlaylist =
                new Playlist(
                        playlistRepository.getAllPlaylists().size() + 1,
                        playlistName,
                        sessionManager.getCurrentUser().getUserId());

        playlistRepository.addPlaylist(newPlaylist);
        playlistCreation.insertPlaylist(newPlaylist);
        System.out.println("Added " + playlistName + " to your playlists.");
    }
}
