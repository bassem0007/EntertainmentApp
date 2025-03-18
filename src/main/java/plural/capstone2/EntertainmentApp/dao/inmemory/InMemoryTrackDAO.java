package plural.capstone2.EntertainmentApp.dao.inmemory;

import org.springframework.stereotype.Repository;
import plural.capstone2.EntertainmentApp.dao.BaseDAO;
import plural.capstone2.EntertainmentApp.domain.Track;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class InMemoryTrackDAO implements BaseDAO<Track> {

    private Map<Integer, Track> tracks = new ConcurrentHashMap<>();
    private AtomicInteger nextId = new AtomicInteger(1);

    @Override
    public boolean update(Track track) {
        return tracks.computeIfPresent(track.getId(),(key, oldValue) -> track) != null;
    }

    @Override
    public boolean delete(Track track) {
        return tracks.remove(track.getId()) != null;
    }

    @Override
    public Track insert(Track track) {
        int newId = nextId.getAndIncrement();
        track.setId(newId);
        tracks.put(newId, track);

        return track;
    }

    @Override
    public Optional<Track> findById(int id) {
        return Optional.ofNullable(tracks.get(id));
    }

    @Override
    public List<Track> findAll() {
        return tracks.values().stream().toList();
    }

    @Override
    public void resetDataStore() {
        tracks = new ConcurrentHashMap<>();
        nextId = new AtomicInteger(1);
    }
}
