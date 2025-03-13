package plural.capstone2.EntertainmentApp.dao.inmemory;

import plural.capstone2.EntertainmentApp.dao.BaseDAO;
import plural.capstone2.EntertainmentApp.domain.Artist;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class InMemoryArtistDAO implements BaseDAO<Artist> {

    private Map<Integer, Artist> artists = new ConcurrentHashMap<>();
    private AtomicInteger nextId = new AtomicInteger(1);

    @Override
    public boolean update(Artist artist) {
        return artists.computeIfPresent(artist.getId(), (key, oldValue) -> artist) != null;
    }

    @Override
    public boolean delete(Artist artist) {
        return artists.remove(artist.getId()) !=null;
    }

    @Override
    public Artist insert(Artist artist) {
        int newId = nextId.getAndIncrement();
        artist.setId(newId);
        artists.put(newId, artist);

        return artist;
    }

    @Override
    public Optional<Artist> findById(int id) {
        return Optional.ofNullable(artists.get(id));
    }

    @Override
    public List<Artist> findAll() {
        return new ArrayList<>(artists.values());
    }

    @Override
    public void resetDataStore() {
        artists = new ConcurrentHashMap<>();
        nextId = new AtomicInteger(1);
    }
}
