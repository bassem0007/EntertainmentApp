package plural.capstone2.EntertainmentApp.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import plural.capstone2.EntertainmentApp.dao.inmemory.InMemoryArtistDAO;
import plural.capstone2.EntertainmentApp.dao.inmemory.InMemoryTrackDAO;
import plural.capstone2.EntertainmentApp.domain.Artist;
import plural.capstone2.EntertainmentApp.domain.Track;
import plural.capstone2.EntertainmentApp.enums.ArtistType;
import plural.capstone2.EntertainmentApp.enums.Genre;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ArtistTrackServiceTest {

    private Artist artist;

    private Artist artist2;

    private Track track;

    @Mock
    private InMemoryArtistDAO artistDAO;

    @Mock
    private InMemoryTrackDAO trackDAO;

    @InjectMocks
    private ArtistTrackService artistTrackService;

    @BeforeEach
    void setUp() {

        artist = new Artist("Pink Floyd", ArtistType.GROUP,"Psychedelic","British",1964);
        artist.setId(1);

        artist2 = new Artist("Syd Barret", ArtistType.SOLO,"Psychedelic","British",1940);
        artist2.setId(2);

        track = new Track("High Hopes", 500, Genre.ROCK, 1988,85);
        track.setId(1);

    }

    @Test
    void addTrackToArtist_shouldCallFindByIdAndUpdateOnceForArtistAndTrack() {
        when(artistDAO.findById(anyInt())).thenReturn(Optional.of(artist));
        when(trackDAO.findById(anyInt())).thenReturn(Optional.of(track));

        boolean result = artistTrackService.addTrackToArtist(artist.getId(),track.getId());

        assertTrue(result);
        verify(artistDAO, times(1)).findById(1);
        verify(trackDAO, times(1)).findById(1);
        verify(artistDAO, times(1)).update(artist);
        verify(trackDAO, times(1)).update(track);
    }

    @Test
    void addTrackToArtist_shouldUpdateArtistWithTrackAndGenre() {
        when(artistDAO.findById(anyInt())).thenReturn(Optional.of(artist));
        when(trackDAO.findById(anyInt())).thenReturn(Optional.of(track));

        artistTrackService.addTrackToArtist(artist.getId(),track.getId());

        assertEquals(track, artist.getTracks().get(0));
        assertEquals(1, artist.getGenres().size());
    }

    @Test
    void addTrackToArtist_shouldUpdateTrackWithArtist() {
        when(artistDAO.findById(anyInt())).thenReturn(Optional.of(artist));
        when(trackDAO.findById(anyInt())).thenReturn(Optional.of(track));

        artistTrackService.addTrackToArtist(artist.getId(),track.getId());

        assertEquals(artist, track.getArtists().get(0));
    }

    @Test
    void removeTrackFromArtist_shouldCallFindByIdAndUpdateOnceForArtistAndTrack() {
        when(artistDAO.findById(anyInt())).thenReturn(Optional.of(artist));
        when(trackDAO.findById(anyInt())).thenReturn(Optional.of(track));

        boolean result = artistTrackService.removeTrackFromArtist(artist.getId(),track.getId());

        assertTrue(result);
        verify(artistDAO, times(1)).findById(1);
        verify(trackDAO, times(1)).findById(1);
        verify(artistDAO, times(1)).update(artist);
        verify(trackDAO, times(1)).update(track);
    }

    @Test
    void removeTrackFromArtist_shouldDeleteArtistFromTrack() {
        when(artistDAO.findById(anyInt())).thenReturn(Optional.of(artist));
        when(trackDAO.findById(anyInt())).thenReturn(Optional.of(track));

        artistTrackService.addTrackToArtist(artist.getId(),track.getId());
        artistTrackService.removeTrackFromArtist(artist.getId(),track.getId());

        assertEquals(0, track.getArtists().size());

    }

    @Test
    void removeTrackFromArtist_shouldDeleteTrackFromArtistAndUpdateGenre() {
        when(artistDAO.findById(anyInt())).thenReturn(Optional.of(artist));
        when(trackDAO.findById(anyInt())).thenReturn(Optional.of(track));

        artistTrackService.addTrackToArtist(artist.getId(),track.getId());
        artistTrackService.removeTrackFromArtist(artist.getId(),track.getId());

        assertEquals(0, artist.getTracks().size());
        assertEquals(0, artist.getGenres().size());
    }

    @Test
    void removeTrackFromAllArtists_shouldRemoveTrackFromAllArtistsAndUpdateGenre() {
        when(trackDAO.findById(anyInt())).thenReturn(Optional.of(track));
        when(artistDAO.findById(artist.getId())).thenReturn(Optional.of(artist));
        when(artistDAO.findById(artist2.getId())).thenReturn(Optional.of(artist2));

        artistTrackService.addTrackToArtist(artist.getId(),track.getId());
        artistTrackService.addTrackToArtist(artist2.getId(),track.getId());

        artistTrackService.removeTrackFromAllArtists(track.getId());

        assertEquals(0, artist.getTracks().size());
        assertEquals(0, artist2.getTracks().size());
        assertEquals(0, artist.getGenres().size());
        assertEquals(0, artist2.getGenres().size());
    }

    @Test
    void removeArtistFromAllTracks_shouldRemoveArtistFromAllTrack() {
        when(trackDAO.findById(anyInt())).thenReturn(Optional.of(track));
        when(artistDAO.findById(artist.getId())).thenReturn(Optional.of(artist));
        when(artistDAO.findById(artist2.getId())).thenReturn(Optional.of(artist2));

        artistTrackService.addTrackToArtist(artist.getId(),track.getId());
        artistTrackService.addTrackToArtist(artist2.getId(),track.getId());

        artistTrackService.removeArtistFromAllTracks(artist.getId());
        artistTrackService.removeArtistFromAllTracks(artist2.getId());

        assertEquals(0, track.getArtists().size());
    }
}
