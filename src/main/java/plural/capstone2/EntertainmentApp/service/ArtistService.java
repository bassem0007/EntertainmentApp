package plural.capstone2.EntertainmentApp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import plural.capstone2.EntertainmentApp.dao.BaseDAO;
import plural.capstone2.EntertainmentApp.domain.Artist;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ArtistService {

    private BaseDAO<Artist> artistDAO;

    public Artist insertArtist(Artist artist) {
        return artistDAO.insert(artist);
    }

    public boolean updateArtist(Artist artist) {
        Optional<Artist> existingArtist = artistDAO.findById(artist.getId());
        if (existingArtist.isPresent()) {
            Artist updatedArtist = existingArtist.get();
            updatedArtist.setName(artist.getName());
            updatedArtist.setArtistType(artist.getArtistType());
            updatedArtist.setGenres(artist.getGenres());
            updatedArtist.setBiography(artist.getBiography());
            updatedArtist.setNationality(artist.getNationality());
            updatedArtist.setYearFounded(artist.getYearFounded());
            updatedArtist.setTracks(artist.getTracks());

            return artistDAO.update(updatedArtist);
        }
        throw new IllegalArgumentException("Artist not found");
    }

    public boolean deleteArtist(Artist artist) {
        Optional<Artist> existingArtist = artistDAO.findById(artist.getId());
        if (existingArtist.isPresent()) {
            Artist deletedArtist = existingArtist.get();
            return artistDAO.delete(deletedArtist);
        }
        throw new IllegalArgumentException("Artist not found");
    }

    public Artist findById(int id) {
        Optional<Artist> existingArtist = artistDAO.findById(id);
        if (existingArtist.isPresent()) {
            return existingArtist.get();
        } else {
            throw new IllegalArgumentException("Artist not found");
        }
    }

    public List<Artist> findAll() {
        List<Artist> allArtists = artistDAO.findAll();
        if (allArtists.isEmpty())
            throw new IllegalArgumentException("No artists not found");
        else
            return artistDAO.findAll();
    }

    public void resetDataStore() {
        artistDAO.resetDataStore();
    }
}
