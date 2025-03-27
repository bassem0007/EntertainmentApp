package plural.capstone2.EntertainmentApp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
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

            artist.get().getGenres().add(track.get().getGenre());

            artist.get().updateGenres();

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

            artist.getGenres().remove(track.getGenre());

            artist.updateGenres();

            artistDAO.update(artist);
            trackDAO.update(track);

            return true;
        }
        return false;
    }

    public void removeTrackFromAllArtists(int trackId) {
        Track track = trackDAO.findById(trackId).orElse(null);
        if (track != null) {
            for (Artist artist : track.getArtists()) {
                artist.getTracks().remove(track);

                artist.updateGenres();

                trackDAO.update(track);
                artistDAO.update(artist);
            }
        }
    }

    public void removeArtistFromAllTracks(int artistId) {
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
