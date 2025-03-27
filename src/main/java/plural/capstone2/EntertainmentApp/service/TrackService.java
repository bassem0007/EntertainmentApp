package plural.capstone2.EntertainmentApp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import plural.capstone2.EntertainmentApp.dao.BaseDAO;
import plural.capstone2.EntertainmentApp.domain.Track;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TrackService {

    private final BaseDAO<Track> trackDAO;

    public boolean updateTrack(Track track) {
        Optional<Track> existingTrack = trackDAO.findById(track.getId());
        return existingTrack.isPresent() && existingTrack
                .map( t -> {
                    t.setTitle(track.getTitle());
                    t.setDurationSeconds(track.getDurationSeconds());
                    t.setGenre(track.getGenre());
                    t.setArtists(track.getArtists());
                    t.setYearReleased(track.getYearReleased());
                    t.setBeatsPerMinute(track.getBeatsPerMinute());
                    return trackDAO.update(t);
        })
                .orElse(false);
    }

    public boolean deleteTrack(int id) {
        Optional<Track> existingTrack = trackDAO.findById(id);
        return existingTrack.filter(trackDAO::delete).isPresent();
    }

    public Track insertTrack(Track track) {
        return trackDAO.insert(track);
    }

    public Track findTrackById(int id) {
        return trackDAO.findById(id).orElse(null);
    }

    public List<Track> findAllTracks() {
        return trackDAO.findAll();
    }

    public void resetTrackDataStore() {
        trackDAO.resetDataStore();
    }
}
