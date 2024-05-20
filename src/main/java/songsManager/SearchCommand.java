package songsManager;

import authentication.Command;
import pagination.Paginator;

import java.util.List;
import java.util.stream.Collectors;


public class SearchCommand implements Command {
    private final List<Song> songs;
    private final int itemsPerPage;
    private final String searchType;
    private final String searchCriteria;

    public SearchCommand(List<Song> songs, int itemsPerPage, String searchType, String searchCriteria) {
        this.songs = songs;
        this.itemsPerPage = itemsPerPage;
        this.searchType = searchType;
        this.searchCriteria = searchCriteria;
    }

    @Override
    public void execute() {
        List<Song> filteredSongs;

        if (searchType.equalsIgnoreCase("author")) {
            filteredSongs = songs.stream()
                    .filter(song -> song.artist().toLowerCase().startsWith(searchCriteria.toLowerCase()))
                    .collect(Collectors.toList());
        } else if (searchType.equalsIgnoreCase("name")) {
            filteredSongs = songs.stream()
                    .filter(song -> song.title().toLowerCase().startsWith(searchCriteria.toLowerCase()))
                    .collect(Collectors.toList());
        } else {
            System.out.println("Invalid search type. Please use 'author' or 'name'.");
            return;
        }

        if (filteredSongs.isEmpty()) {
            System.out.println("Page 0 of 0 (0 items):");
            return;
        }

        Paginator<Song> paginator = new Paginator<>(filteredSongs, itemsPerPage);
        paginator.paginate();
    }
}
