package songsManager;

import authentication.SessionManager;
import exceptions.InvalidInputException;
import java.util.List;
import java.util.Scanner;
import pagination.Paginator;
import songRepository.Song;
import songRepository.SongRepository;

public class SearchCommand extends SongCommand {
    private final int itemsPerPage;

    public SearchCommand(
            SessionManager sessionManager, SongRepository repository, int itemsPerPage) {
        super(sessionManager, repository);
        this.itemsPerPage = itemsPerPage;
    }

    @Override
    public void execute() {
        requireLoggedIn();

        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter search type (author/name): ");
        String searchType = scanner.nextLine();

        if (!searchType.equalsIgnoreCase("author") && !searchType.equalsIgnoreCase("name")) {
            throw new InvalidInputException("Invalid search type.");
        }
        System.out.print("Enter search criteria: ");
        String searchCriteria = scanner.nextLine();

        List<Song> filteredSongs;
        if (searchType.equalsIgnoreCase("author")) {
            filteredSongs = songRepository.findByArtist(searchCriteria);
        } else {
            filteredSongs = songRepository.findByTitle(searchCriteria);
        }

        if (filteredSongs.isEmpty()) {
            System.out.println("No songs found matching the search criteria.");
            return;
        }
        Paginator<Song> paginator = new Paginator<>(filteredSongs, itemsPerPage);
        paginator.paginate();
    }
}
