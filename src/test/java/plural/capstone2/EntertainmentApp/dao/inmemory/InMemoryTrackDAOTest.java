package plural.capstone2.EntertainmentApp.dao.inmemory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import plural.capstone2.EntertainmentApp.dao.BaseDAO;
import plural.capstone2.EntertainmentApp.domain.Artist;
import plural.capstone2.EntertainmentApp.domain.Track;
import plural.capstone2.EntertainmentApp.enums.Genre;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class InMemoryTrackDAOTest {

    private Track track1;
    private Track track2;

    private BaseDAO<Track> trackDAO = new InMemoryTrackDAO();

    @BeforeEach
    public void init() {
        trackDAO = new InMemoryTrackDAO();
        trackDAO.resetDataStore();

        Artist artist1 = new Artist();
        Artist artist2 = new Artist();

        track1 = new Track("High Hopes",500, Genre.ROCK, List.of(artist1),1988,85);
        track2 = new Track("Sultans of Swing", 400, Genre.ROCK, List.of(artist2), 1982, 95);

        trackDAO.insert(track1);
        
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }

    @Test
    void insert() {
    }

    @Test
    void findById() {
    }

    @Test
    void findAll() {
    }

    @Test
    void resetDataStore() {
    }
}