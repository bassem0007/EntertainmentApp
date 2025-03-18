package plural.capstone2.EntertainmentApp.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import plural.capstone2.EntertainmentApp.domain.Artist;
import plural.capstone2.EntertainmentApp.service.ArtistService;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/main/artists")
@RequiredArgsConstructor
public class ArtistController {

    private final ArtistService artistService;

    @GetMapping
    public ResponseEntity<List<Artist>> getArtists() {
        return ResponseEntity.ok(artistService.findAllArtists());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Artist> getArtistById(@PathVariable int id) {
        Artist artist = artistService.findArtistById(id);
        return artist != null ? ResponseEntity.ok(artist) : ResponseEntity.notFound().build();
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
        boolean result = artistService.deleteArtist(id);
        return result ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/reset")
    public ResponseEntity<?> resetArtists() {
        artistService.resetArtistDataStore();
        return ResponseEntity.noContent().build();
    }
}
