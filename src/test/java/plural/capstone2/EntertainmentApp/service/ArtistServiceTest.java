package plural.capstone2.EntertainmentApp.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import plural.capstone2.EntertainmentApp.dao.BaseDAO;
import plural.capstone2.EntertainmentApp.domain.Artist;
import plural.capstone2.EntertainmentApp.enums.ArtistType;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ArtistServiceTest {

    private Artist artist;

    @Mock
    private BaseDAO<Artist> artistDAO;

    @InjectMocks
    private ArtistService artistService;

    @BeforeEach
    void setUp() {
        artist = new Artist(
                "Pink Floyd",
                ArtistType.GROUP,
                "Psychedelic",
                "British",
                1964);
        artist.setId(1);
    }

    @Test
    void insertArtist_shouldCallInsertOnceAndShouldInsertArtist() {
        when(artistDAO.insert(artist)).thenReturn(artist);
        Artist expectedArtist = artistService.insertArtist(artist);
        verify(artistDAO, times(1)).insert(artist);
        assertEquals(expectedArtist, artist);
    }

    @Test
    void updateArtist_shouldCallFindByIdAndUpdateOnceAndShouldUpdateArtist() {
        when(artistDAO.findById(artist.getId())).thenReturn(Optional.ofNullable(artist));
        when(artistDAO.update(artist)).thenReturn(true);
        boolean result = artistService.updateArtist(artist);
        assertTrue(result);

        verify(artistDAO, times(1)).update(artist);
        verify(artistDAO, times(1)).findById(artist.getId());

        Artist updatedArtist = artistDAO.findById(artist.getId()).get();
        assertEquals(artist, updatedArtist);
    }

    @Test
    void updateArtist_shouldNotCallUpdateForNonExistentArtist() {
        artist.setId(2);
        boolean result = artistService.updateArtist(artist);
        assertFalse(result);
        verify(artistDAO, times(0)).update(artist);
    }

    @Test
    void deleteArtist_shouldCallFindByIdAndDeleteOnceAndShouldDeleteArtist() {
        when(artistDAO.findById(artist.getId())).thenReturn(Optional.ofNullable(artist));
        when(artistDAO.delete(artist)).thenReturn(true);

        boolean result = artistService.deleteArtist(artist.getId());

        assertTrue(result);
        verify(artistDAO, times(1)).findById(artist.getId());
        verify(artistDAO, times(1)).delete(artist);
    }

    @Test
    void deleteArtist_shouldNotCallDeleteForNonExistentArtist() {
        artist.setId(2);
        boolean result = artistService.deleteArtist(artist.getId());
        assertFalse(result);
        verify(artistDAO, times(0)).delete(artist);
    }

    @Test
    void findById_shouldCallFindByIdOnceAndShouldFindArtist() {
        when(artistDAO.findById(artist.getId())).thenReturn(Optional.ofNullable(artist));
        Artist expectedArtist = artistService.findArtistById(artist.getId());
        verify(artistDAO, times(1)).findById(artist.getId());
        assertAll(
                () -> assertNotNull(expectedArtist),
                () -> assertEquals(artist.getId(), expectedArtist.getId()),
                () -> assertEquals(artist.getArtistType(), expectedArtist.getArtistType()),
                () -> assertEquals(artist.getName(), expectedArtist.getName()),
                () -> assertEquals(artist.getNationality(), expectedArtist.getNationality()),
                () -> assertEquals(artist.getBiography(), expectedArtist.getBiography()),
                () -> assertEquals(artist.getYearFounded(), expectedArtist.getYearFounded()),
                () -> assertEquals(artist.getGenres(), expectedArtist.getGenres()),
                () -> assertEquals(artist.getTracks(), expectedArtist.getTracks())
        );
    }

    @Test
    void findById_shouldCallFindByIdOnceAndReturnNullIfArtistDoesNotExist() {
        artist.setId(2);
        artistService.findArtistById(artist.getId());
        verify(artistDAO, times(1)).findById(artist.getId());
        assertNull(artistService.findArtistById(artist.getId()));
    }

    @Test
    void findAll_shouldCallFindAllOnceAndShouldReturnAllArtists() {
        when(artistDAO.findAll()).thenReturn(List.of(artist));
        List<Artist> expectedArtist = artistService.findAllArtists();
        verify(artistDAO, times(1)).findAll();
        assertAll(
                () -> assertNotNull(expectedArtist),
                () -> assertEquals(1, expectedArtist.size()),
                () -> assertEquals(artist, expectedArtist.get(0))
        );
    }

    @Test
    void resetDataStore() {
        artistService.resetArtistDataStore();
        verify(artistDAO, times(1)).resetDataStore();
    }
}
