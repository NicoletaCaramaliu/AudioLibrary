package songRepository;

import java.util.List;

public interface SongRepository {
    List<Song> findAll();

    void save(Song song);

    List<Song> findByTitleAndArtist(String title, String artist);

    List<Song> findByIdentifiers(String[] songIdentifiers);

    List<Song> findByArtist(String artist);

    List<Song> findByTitle(String title);
}
