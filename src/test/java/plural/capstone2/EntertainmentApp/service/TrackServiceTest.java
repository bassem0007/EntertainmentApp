package plural.capstone2.EntertainmentApp.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import plural.capstone2.EntertainmentApp.dao.inmemory.InMemoryTrackDAO;
import plural.capstone2.EntertainmentApp.domain.Track;
import plural.capstone2.EntertainmentApp.enums.Genre;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TrackServiceTest {

    private Track track;

    @Mock
    private InMemoryTrackDAO trackDAO;

    @InjectMocks
    private TrackService trackService;

    @BeforeEach
    void setUp() {

         track = new Track(
                "High Hopes",
                500,
                Genre.ROCK,
                1988,
                85);

        track.setId(1);
    }

    @Test
    void updateTrack_shouldCallFindByIdAndUpdateOnceAndUpdateTrack() {
        when(trackDAO.findById(1)).thenReturn(Optional.ofNullable(track));
        when(trackDAO.update(track)).thenReturn(true);

        boolean result = trackService.updateTrack(track);

        assertTrue(result);
        verify(trackDAO, times(1)).findById(1);
        verify(trackDAO, times(1)).update(track);
    }

    @Test
    void updateTrack_shouldOnlyCallFindByIdOnceAndNotUpdateForNonExistentTrack() {
        track.setId(2);
        when(trackDAO.findById(2)).thenReturn(Optional.empty());

        boolean result = trackService.updateTrack(track);

        assertFalse(result);

        verify(trackDAO, times(1)).findById(2);
        verify(trackDAO, times(0)).update(track);
    }

    @Test
    void deleteTrack_shouldCallFindByIdAndDeleteOnceAndShouldDeleteTrack() {
        when(trackDAO.findById(1)).thenReturn(Optional.ofNullable(track));
        when(trackDAO.delete(track)).thenReturn(true);

        boolean result = trackService.deleteTrack(1);

        assertTrue(result);

        verify(trackDAO, times(1)).findById(1);
        verify(trackDAO, times(1)).delete(track);
    }

    @Test
    void deleteTrack_shouldOnlyCallFindByIdOnceAndNotDeleteForNonExistentTrack() {
        track.setId(2);
        when(trackDAO.findById(2)).thenReturn(Optional.empty());
        boolean result = trackService.deleteTrack(2);

        assertFalse(result);

        verify(trackDAO, times(1)).findById(2);
        verify(trackDAO, times(0)).delete(track);
    }

    @Test
    void insertTrack_shouldCallInsertAndFindByIdOnceAndInsertTrack() {
        when(trackDAO.insert(track)).thenReturn(track);
        when(trackDAO.findById(1)).thenReturn(Optional.ofNullable(track));

        Track createdTrack = trackService.insertTrack(track);

        Track returnedTrack = trackService.findTrackById(createdTrack.getId());

        assertEquals(track, returnedTrack);

        verify(trackDAO, times(1)).insert(track);
        verify(trackDAO, times(1)).findById(1);
    }

    @Test
    void insertTrack_shouldReturnNullIfTrackIsNullAndCallInsertOnce() {
        when(trackDAO.insert(any())).thenReturn(null);

        Track createdTrack = trackService.insertTrack(null);

        assertNull(createdTrack);
        verify(trackDAO, times(1)).insert(any());
    }

    @Test
    void findTrackById_shouldCallFindByIdOnceAndReturnTrack() {
        when(trackDAO.findById(1)).thenReturn(Optional.ofNullable(track));

        Track expectedTrack = trackService.findTrackById(1);
        assertEquals(expectedTrack, track);

        verify(trackDAO, times(1)).findById(1);
    }

    @Test
    void findTrackById_shouldCallFindByIdOnceAndReturnNullIfTrackDoesNotExist() {
        when(trackDAO.findById(1)).thenReturn(Optional.empty());

        Track expectedTrack = trackService.findTrackById(1);

        assertNull(expectedTrack);
        verify(trackDAO, times(1)).findById(1);
    }

    @Test
    void findAllTracks_shouldCallFindAllOnceAndReturnAllTracks() {
        when(trackDAO.findAll()).thenReturn(List.of(track));

        List<Track> expectedTracks = trackService.findAllTracks();

        verify(trackDAO, times(1)).findAll();

        assertAll(
                () -> assertNotNull(expectedTracks),
                () -> assertEquals(1, expectedTracks.size()),
                () -> assertEquals(expectedTracks.get(0), track)
        );
    }

    @Test
    void resetTrackDataStore() {
        trackDAO.resetDataStore();
        verify(trackDAO,  times(1)).resetDataStore();
    }
}