package songRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class InMemorySongRepository implements SongRepository {
    private final List<Song> songs = new ArrayList<>();

    @Override
    public List<Song> findAll() {
        return new ArrayList<>(songs);
    }

    @Override
    public void save(Song song) {
        songs.add(song);
    }

    @Override
    public List<Song> findByTitleAndArtist(String title, String artist) {
        return songs.stream()
                .filter(song -> song.title().equals(title) && song.artist().equals(artist))
                .collect(Collectors.toList());
    }

    @Override
    public List<Song> findByIdentifiers(String[] songIdentifiers) {
        return songs.stream()
                .filter(
                        song -> {
                            for (String id : songIdentifiers) {
                                if (song.songId() == Integer.parseInt(id)) {
                                    return true;
                                }
                            }
                            return false;
                        })
                .collect(Collectors.toList());
    }

    @Override
    public List<Song> findByArtist(String artist) {
        return songs.stream()
                .filter(song -> song.artist().startsWith(artist))
                .collect(Collectors.toList());
    }

    @Override
    public List<Song> findByTitle(String title) {
        return songs.stream()
                .filter(song -> song.title().startsWith(title))
                .collect(Collectors.toList());
    }
}
