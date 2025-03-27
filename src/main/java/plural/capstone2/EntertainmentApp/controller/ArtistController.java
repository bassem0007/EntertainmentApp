package plural.capstone2.EntertainmentApp.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import plural.capstone2.EntertainmentApp.DTO.ArtistWithTracksDTO;
import plural.capstone2.EntertainmentApp.domain.Artist;
import plural.capstone2.EntertainmentApp.service.ArtistService;
import plural.capstone2.EntertainmentApp.service.ArtistTrackService;
import plural.capstone2.EntertainmentApp.service.MappingService;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/main/artists")
@RequiredArgsConstructor
public class ArtistController {

    private final ArtistService artistService;
    private final MappingService mappingService;
    private final ArtistTrackService artistTrackService;

    @GetMapping
    public ResponseEntity<List<ArtistWithTracksDTO>> getArtists() {
        List<Artist> artists = artistService.findAllArtists();
        List<ArtistWithTracksDTO> artistWithTracksDTOs = new ArrayList<>();
        for (Artist artist : artists) {
            ArtistWithTracksDTO artistWithTracksDTO;
            artistWithTracksDTO = mappingService.mapArtistWithTracksDTO(artist);
            artistWithTracksDTOs.add(artistWithTracksDTO);
        }
        return ResponseEntity.ok(artistWithTracksDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ArtistWithTracksDTO> getArtistById(@PathVariable int id) {
        Artist artist = artistService.findArtistById(id);
        if (artist == null) { return ResponseEntity.notFound().build(); }

        ArtistWithTracksDTO artistWithTracksDTO = mappingService.mapArtistWithTracksDTO(artist);
        return ResponseEntity.ok(artistWithTracksDTO);
    }

    @PostMapping
    public ResponseEntity<Artist> insertArtist(@RequestBody Artist artist) {
        return ResponseEntity.ok(artistService.insertArtist(artist));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Artist> updateArtist(@PathVariable int id, @RequestBody Artist artist) {
        if (!Objects.equals(artist.getId(), id))
            return ResponseEntity.badRequest().build();
        boolean result = artistService.updateArtist(artist);
        return result ? ResponseEntity.ok(artist) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteArtist(@PathVariable int id) {
        artistTrackService.removeArtistFromAllTracks(id);
        boolean result = artistService.deleteArtist(id);
        return (result) ? ResponseEntity.accepted().build() : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/reset")
    public ResponseEntity<?> resetArtists() {
        artistService.resetArtistDataStore();
        return ResponseEntity.noContent().build();
    }

}
