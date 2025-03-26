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
    void insertArtist_shouldCallInsertAndFindByIdOnceAndShouldInsertArtist() {
        when(artistDAO.insert(artist)).thenReturn(artist);
        when(artistDAO.findById(1)).thenReturn(Optional.of(artist));

        Artist createdArtist = artistService.insertArtist(artist);

        Artist returnedArtist = artistService.findArtistById(createdArtist.getId());

        verify(artistDAO, times(1)).insert(artist);
        verify(artistDAO, times(1)).findById(1);
        assertEquals(artist, returnedArtist);
    }

    @Test
    void insertArtist_shouldReturnNullIfArtistIsNullAndCallInsertOnce() {
        when(artistDAO.insert(any())).thenReturn(null);

        Artist createdArtist = artistService.insertArtist(any());

        assertNull(createdArtist);
        verify(artistDAO, times(1)).insert(any());
    }

    @Test
    void updateArtist_shouldCallFindByIdAndUpdateOnceAndShouldUpdateArtist() {
        when(artistDAO.findById(artist.getId())).thenReturn(Optional.ofNullable(artist));
        when(artistDAO.update(artist)).thenReturn(true);

        boolean result = artistService.updateArtist(artist);

        assertTrue(result);

        verify(artistDAO, times(1)).update(artist);
        verify(artistDAO, times(1)).findById(artist.getId());

        Artist updatedArtist = artistService.findArtistById(artist.getId());
        assertEquals(artist, updatedArtist);
    }

    @Test
    void updateArtist_shouldOnlyCallFindByIdOnceAndNotCallUpdateForNonExistentArtist() {
        artist.setId(2);
        when(artistDAO.findById(2)).thenReturn(Optional.empty());

        boolean result = artistService.updateArtist(artist);

        assertFalse(result);
        verify(artistDAO, times(1)).findById(artist.getId());
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
    void findById_shouldCallFindByIdOnceAndShouldReturnArtist() {
        when(artistDAO.findById(artist.getId())).thenReturn(Optional.ofNullable(artist));
        Artist expectedArtist = artistService.findArtistById(artist.getId());
        verify(artistDAO, times(1)).findById(artist.getId());
        assertEquals(expectedArtist, artist);
    }

    @Test
    void findById_shouldCallFindByIdOnceAndReturnNullIfArtistDoesNotExist() {
        artist.setId(2);
        when(artistDAO.findById(2)).thenReturn(Optional.empty());

        Artist expectedArtist = artistService.findArtistById(artist.getId());

        assertNull(expectedArtist);
        verify(artistDAO, times(1)).findById(artist.getId());

    }

    @Test
    void findAll_shouldCallFindAllOnceAndShouldReturnAllArtists() {
        when(artistDAO.findAll()).thenReturn(List.of(artist));
        List<Artist> expectedArtist = artistService.findAllArtists();
        verify(artistDAO, times(1)).findAll();
        assertAll(
                () -> assertNotNull(expectedArtist),
                () -> assertEquals(1, expectedArtist.size()),
                () -> assertEquals(expectedArtist.get(0), artist)
        );
    }

    @Test
    void resetDataStore() {
        artistService.resetArtistDataStore();
        verify(artistDAO, times(1)).resetDataStore();
    }
}
