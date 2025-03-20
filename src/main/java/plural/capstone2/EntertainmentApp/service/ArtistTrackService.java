package plural.capstone2.EntertainmentApp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import plural.capstone2.EntertainmentApp.dao.inmemory.InMemoryArtistDAO;
import plural.capstone2.EntertainmentApp.dao.inmemory.InMemoryTrackDAO;
import plural.capstone2.EntertainmentApp.domain.Artist;
import plural.capstone2.EntertainmentApp.domain.Track;

@Service
@RequiredArgsConstructor
public class ArtistTrackService {

    private final InMemoryArtistDAO artistDAO;

    private final InMemoryTrackDAO trackDAO;

    public boolean addTrackToArtist(int artistId, int trackId) {
        Artist artist = artistDAO.findById(artistId).orElse(null);
        Track track = trackDAO.findById(trackId).orElse(null);

        if (artist != null && track != null) {
            artist.getTracks().add(track);
            track.getArtists().add(artist);

            artistDAO.update(artist);
            trackDAO.update(track);

            return true;
        }
        return false;
    }

    public Artist removeTrackFromArtist(int artistId, int trackId) {
        Artist artist = artistDAO.findById(artistId).orElse(null);
        Track track = trackDAO.findById(trackId).orElse(null);

        if (artist != null && track != null) {
            artist.getTracks().remove(track);
            track.getArtists().remove(artist);

            artistDAO.update(artist);
            trackDAO.update(track);

            return artist;
        }
        return null;
    }

    public boolean addArtistToTrack(int trackId, int artistId) {
        Track track = trackDAO.findById(trackId).orElse(null);
        Artist artist = artistDAO.findById(artistId).orElse(null);

        if (track != null && artist != null) {
            track.getArtists().add(artist);
            artist.getTracks().add(track);

            trackDAO.update(track);
            artistDAO.update(artist);

            return true;
        }
        return false;
    }

    public Track removeArtistFromTrack(int trackId, int artistId) {
        Track track = trackDAO.findById(trackId).orElse(null);
        Artist artist = artistDAO.findById(artistId).orElse(null);

        if (track != null && artist != null) {
            track.getArtists().remove(artist);
            artist.getTracks().remove(track);

            trackDAO.update(track);
            artistDAO.update(artist);

            return track;
        }
        return null;
    }
}
