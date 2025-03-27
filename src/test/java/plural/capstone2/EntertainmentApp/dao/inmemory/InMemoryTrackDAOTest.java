package plural.capstone2.EntertainmentApp.dao.inmemory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import plural.capstone2.EntertainmentApp.dao.BaseDAO;
import plural.capstone2.EntertainmentApp.domain.Artist;
import plural.capstone2.EntertainmentApp.domain.Track;
import plural.capstone2.EntertainmentApp.enums.Genre;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SuppressWarnings("OptionalGetWithoutIsPresent")
public class InMemoryTrackDAOTest {

    private Track track1;
    private Track track2;

    private BaseDAO<Track> trackDAO = new InMemoryTrackDAO();

    @BeforeEach
    public void setUp() {
        trackDAO = new InMemoryTrackDAO();
        trackDAO.resetDataStore();

        Artist artist1 = new Artist();
        Artist artist2 = new Artist();

        track1 = new Track("High Hopes",500, Genre.ROCK,1988,85);
        track1.setArtists(List.of(artist1));
        track2 = new Track("Sultans of Swing", 400, Genre.ROCK,1982, 95);
        track2.setArtists(List.of(artist2));

        trackDAO.insert(track1);
        trackDAO.insert(track2);
    }

    @Test
    void update_shouldUpdateTrack() {
        Track updatedTrack = new Track(
                "High Hopes",
                600,
                Genre.ROCK,
                1988,
                85
        );
        updatedTrack.setId(1);
        boolean result = trackDAO.update(updatedTrack);
        Optional<Track> newTrack = trackDAO.findById(1);

        assertAll(
                () -> assertTrue(result),
                () -> assertTrue(newTrack.isPresent()),
                () -> assertNotEquals(track1.getDurationSeconds(), updatedTrack.getDurationSeconds()),
                () -> assertEquals(updatedTrack, newTrack.get())
        );
    }
    @Test
    void update_shouldNotUpdateTrackIfTrackDoesNotExist() {
        Track track = new Track();
        track.setId(3);
        boolean result = trackDAO.update(track);

        assertAll(
                () -> assertFalse(result),
                () -> assertEquals(2, trackDAO.findAll().size())
        );
    }
    @Test
    void delete_shouldDeleteTrack() {
        boolean result = trackDAO.delete(track1);

        assertAll(
                () -> assertTrue(result),
                () -> assertFalse(trackDAO.findById(1).isPresent()),
                () -> assertEquals(1, trackDAO.findAll().size()),
                () -> assertEquals(track2, trackDAO.findAll().get(0))
        );
    }

    @Test
    void delete_shouldNotDeleteAnyTrackIfTrackDoesNotExist() {
        Track track3 = new Track();
        boolean result = trackDAO.delete(track3);

        assertAll(
                () -> assertFalse(result),
                () -> assertEquals(2, trackDAO.findAll().size())
        );
    }

    @Test
    void insert() {
        assertAll(
                () -> assertEquals(track1, trackDAO.findById(1).get()),
                () -> assertEquals(track2, trackDAO.findById(2).get()),
                () -> assertEquals(2, trackDAO.findAll().size())
        );
    }

    @Test
    void findById() {
        Optional<Track> expectedTrack1 = trackDAO.findById(track1.getId());
        Optional<Track> expectedTrack2 = trackDAO.findById(track2.getId());

        assertAll(
                () -> assertTrue(expectedTrack1.isPresent()),
                () -> assertTrue(expectedTrack2.isPresent()),
                () -> assertEquals(expectedTrack1.get(), track1),
                () -> assertEquals(expectedTrack2.get(), track2)
        );
    }

    @Test
    void findAll() {
        List<Track> tracks = trackDAO.findAll();

        assertAll(
                () -> assertNotNull(tracks),
                () -> assertEquals(2, trackDAO.findAll().size()),
                () -> assertEquals(0, tracks.stream().filter(t -> t.getId() == 0).count()),
                () -> assertEquals(tracks.get(0), track1),
                () -> assertEquals(tracks.get(1), track2)
        );
    }

    @Test
    void resetDataStore() {
        trackDAO.resetDataStore();
        assertEquals(0, trackDAO.findAll().size());
    }
}