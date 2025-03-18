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

    private final BaseDAO<Artist> artistDAO;

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
        else return false;
    }

    public boolean deleteArtist(int id) {
        Optional<Artist> existingArtist = artistDAO.findById(id);
        return existingArtist.filter(artistDAO::delete).isPresent();
    }

    public Artist findArtistById(int id) {
        return artistDAO.findById(id).orElse(null);
    }

    public List<Artist> findAllArtists() {
            return artistDAO.findAll();
    }

    public void resetArtistDataStore() {
        artistDAO.resetDataStore();
    }
}
