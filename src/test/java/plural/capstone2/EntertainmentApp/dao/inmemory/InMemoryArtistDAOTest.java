package plural.capstone2.EntertainmentApp.dao.inmemory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import plural.capstone2.EntertainmentApp.dao.BaseDAO;
import plural.capstone2.EntertainmentApp.domain.Artist;
import plural.capstone2.EntertainmentApp.enums.ArtistType;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SuppressWarnings("OptionalGetWithoutIsPresent")
public class InMemoryArtistDAOTest {

    private Artist artist1;
    private Artist artist2;

    private BaseDAO<Artist> artistDAO = new InMemoryArtistDAO();

    @BeforeEach
    void setUp() {
        artistDAO = new InMemoryArtistDAO();
        artistDAO.resetDataStore();

        artist1 =  new Artist(
                "Pink Floyd",
                ArtistType.GROUP,
                "One of the most successful and influential rock groups in history","British",
                1964);
        artist2 = new Artist(
                "Dire Straits",
                ArtistType.GROUP,
                "Emerged during the post-punk era in the late 70s",
                "British",
                1978);

        artistDAO.insert(artist1);
        artistDAO.insert(artist2);
    }

    @Test
    void insert() {
        assertAll(
                () -> assertEquals(artist1, artistDAO.findById(1).get()),
                () -> assertEquals(artist2, artistDAO.findById(2).get()),
                () -> assertEquals(2, artistDAO.findAll().size())
        );
    }

    @Test
    void update_shouldUpdateArtist() {
        Artist updatedArtist = new Artist("Dire Straits",
                ArtistType.GROUP,
                "Emerged during the post-punk era in the late 70s",
                "British",
                1980);
        updatedArtist.setId(2);
        boolean result = artistDAO.update(updatedArtist);
        Optional<Artist> newCourse = artistDAO.findById(artist2.getId());
        assertAll(
                () -> assertTrue(result),
                () -> assertTrue(newCourse.isPresent()),
                () -> assertNotEquals(artist2.getYearFounded(), newCourse.get().getYearFounded()),
                () -> assertEquals(updatedArtist, newCourse.get())
        );
    }

    @Test
    void updated_shouldNotUpdateArtistIfArtistDoesNotExist() {
        Artist newArtist = new Artist();
        newArtist.setId(3);
        boolean result = artistDAO.update(newArtist);
        assertFalse(result);
        assertEquals(2, artistDAO.findAll().size());
    }

    @Test
    void delete_shouldDeleteArtist() {
        boolean results = artistDAO.delete(artist1);
        assertAll(
                () -> assertTrue(results),
                () -> assertFalse(artistDAO.findById(1).isPresent()),
                () -> assertEquals(1, artistDAO.findAll().size()),
                () -> assertEquals(artist2, artistDAO.findAll().get(0))
        );
    }

    @Test
    void delete_shouldNotDeleteAnyArtistIfArtistDoesNotExist() {
        Artist newArtist = new Artist();
        boolean results = artistDAO.delete(newArtist);
        assertAll(
                () -> assertFalse(results),
                () -> assertEquals(2, artistDAO.findAll().size())
        );
    }

    @Test
    void findById_shouldReturnArtist() {
        Optional<Artist> expectedArtist1 = artistDAO.findById(artist1.getId());
        Optional<Artist> expectedArtist2 = artistDAO.findById(artist2.getId());
        assertAll(
                () -> assertTrue(expectedArtist1.isPresent()),
                () -> assertTrue(expectedArtist2.isPresent()),
                () -> assertEquals(artist1, expectedArtist1.get()),
                () -> assertEquals(artist2, expectedArtist2.get()));
    }

    @Test
    void findAll_shouldReturnAllArtists() {
        List<Artist> artists = artistDAO.findAll();
        assertAll(
                () -> assertNotNull(artists),
                () -> assertEquals(2, artistDAO.findAll().size()),
                () -> assertEquals(0, artists.stream().filter(artist -> artist.getId() == 0).count()),
                () -> assertEquals(artist1, artists.get(0)),
                () -> assertEquals(artist2, artists.get(1))
         );
    }

    @Test
    void resetDataStore() {
        artistDAO.resetDataStore();
        assertEquals(0, artistDAO.findAll().size());
    }
}
