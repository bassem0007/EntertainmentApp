package plural.capstone2.EntertainmentApp.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import plural.capstone2.EntertainmentApp.domain.Track;
import plural.capstone2.EntertainmentApp.service.ArtistService;
import plural.capstone2.EntertainmentApp.service.TrackService;

import java.util.List;

@RestController
@RequestMapping("/main/tracks")
@RequiredArgsConstructor
public class TrackController {

    private final TrackService trackService;

    @GetMapping
    public ResponseEntity<List<Track>> findAllTracks() {
        return ResponseEntity.ok(trackService.findAllTracks());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Track> findTrackById(@PathVariable int id) {
        Track track = trackService.findTrackById(id);
        return track != null ? ResponseEntity.ok(track) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Track> insertTrack(@RequestBody Track track) {
        return ResponseEntity.ok(trackService.insertTrack(track));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Track> updateTrack(@PathVariable int id, @RequestBody Track track) {
        if (id != track.getId()) { return ResponseEntity.badRequest().build();}
        boolean result = trackService.updateTrack(track);
        return result ? ResponseEntity.ok(track) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Track> deleteTrack(@PathVariable int id) {
        boolean result = trackService.deleteTrack(id);
        return result ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    @DeleteMapping
    public ResponseEntity<?> resetTracks() {
        trackService.resetTrackDataStore();
        return ResponseEntity.noContent().build();
    }
}
