package plural.capstone2.EntertainmentApp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import plural.capstone2.EntertainmentApp.dao.inmemory.InMemoryArtistDAO;
import plural.capstone2.EntertainmentApp.dao.inmemory.InMemoryTrackDAO;
import plural.capstone2.EntertainmentApp.domain.Artist;
import plural.capstone2.EntertainmentApp.domain.Track;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ArtistTrackService {

    private final InMemoryArtistDAO artistDAO;
    private final InMemoryTrackDAO trackDAO;

    public boolean addTrackToArtist(int artistId, int trackId) {
        Optional<Artist> artist = artistDAO.findById(artistId);
        Optional<Track> track = trackDAO.findById(trackId);

        if (artist.isPresent() && track.isPresent()) {
            artist.get().getTracks().add(track.get());
            track.get().getArtists().add(artist.get());

            artistDAO.update(artist.get());
            trackDAO.update(track.get());

            return true;
        }
        return false;
    }

    public boolean removeTrackFromArtist(int artistId, int trackId) {
        Artist artist = artistDAO.findById(artistId).orElse(null);
        Track track = trackDAO.findById(trackId).orElse(null);

        if (artist != null && track != null) {
            artist.getTracks().remove(track);
            track.getArtists().remove(artist);

            artistDAO.update(artist);
            trackDAO.update(track);

            return true;
        }
        return false;
    }

    public void removeTrackFromAllArtists(@PathVariable int trackId) {
        Track track = trackDAO.findById(trackId).orElse(null);
        if (track != null) {
            for (Artist artist : track.getArtists()) {
                artist.getTracks().remove(track);

                trackDAO.update(track);
                artistDAO.update(artist);
            }
        }
    }

    public void removeArtistFromAllTracks(@PathVariable int artistId) {
        Artist artist = artistDAO.findById(artistId).orElse(null);
        if (artist != null) {
            for (Track track : artist.getTracks()) {
                track.getArtists().remove(artist);

                trackDAO.update(track);
                artistDAO.update(artist);
            }
        }
    }

}
