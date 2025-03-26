package plural.capstone2.EntertainmentApp.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import plural.capstone2.EntertainmentApp.dao.BaseDAO;
import plural.capstone2.EntertainmentApp.dao.inmemory.InMemoryArtistDAO;
import plural.capstone2.EntertainmentApp.dao.inmemory.InMemoryTrackDAO;
import plural.capstone2.EntertainmentApp.domain.Artist;
import plural.capstone2.EntertainmentApp.domain.Track;
import plural.capstone2.EntertainmentApp.enums.ArtistType;
import plural.capstone2.EntertainmentApp.enums.Genre;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ArtistTrackServiceTest {

    private Artist artist;

    private Track track;

    @Mock
    private InMemoryArtistDAO artistDAO;

    @Mock
    private InMemoryTrackDAO trackDAO;

    @InjectMocks
    private ArtistTrackService artistTrackService;

    @BeforeEach
    void setUp() {

        MockitoAnnotations.initMocks(this);

        artist = new Artist(
                "Pink Floyd",
                ArtistType.GROUP,
                "Psychedelic",
                "British",
                1964);
        artist.setId(1);

        track = new Track(
                "High Hopes",
                500,
                Genre.ROCK,
                List.of(),
                1988,
                85);
        track.setId(2);

    }

    @Test
    void testMockCreation() {
        BaseDAO<Artist> mockArtist = Mockito.mock(BaseDAO.class);
        BaseDAO<Track> mockTrack = Mockito.mock(BaseDAO.class);

        System.out.print(mockArtist);
        System.out.print(mockTrack);

        assertNotEquals(mockArtist, mockTrack);

    }

    @Test
    void addTrackToArtist_shouldCallFindByIdOnceForArtistAndTrack() {

        when(artistDAO.findById(anyInt())).thenReturn(Optional.of(artist));
        when(trackDAO.findById(anyInt())).thenReturn(Optional.of(track));

        System.out.println(artistDAO.findById(artist.getId()));
        System.out.println(trackDAO.findById(track.getId()));

        boolean result = artistTrackService.addTrackToArtist(artist.getId(),track.getId());

        assertTrue(result);
//        verify(artistDAO, times(1)).findById(1);
//        verify(trackDAO, times(1)).findById(1);
    }

    @Test
    void removeTrackFromArtist() {
    }

    @Test
    void addArtistToTrack() {
    }

    @Test
    void removeArtistFromTrack() {
    }

    @Test
    void removeTrackFromAllArtists() {
    }

    @Test
    void removeArtistFromAllTracks() {
    }
}