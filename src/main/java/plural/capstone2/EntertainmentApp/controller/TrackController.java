package plural.capstone2.EntertainmentApp.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import plural.capstone2.EntertainmentApp.DTO.TrackWithArtistsDTO;
import plural.capstone2.EntertainmentApp.domain.Track;
import plural.capstone2.EntertainmentApp.service.ArtistTrackService;
import plural.capstone2.EntertainmentApp.service.MappingService;
import plural.capstone2.EntertainmentApp.service.TrackService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/main/tracks")
@RequiredArgsConstructor
public class TrackController {

    private final TrackService trackService;
    private final MappingService mappingService;
    private final ArtistTrackService artistTrackService;

    @GetMapping
    public ResponseEntity<List<TrackWithArtistsDTO>> findAllTracks() {
        List<Track> tracks = trackService.findAllTracks();
        List<TrackWithArtistsDTO> trackWithArtistsDTOs = new ArrayList<>();
        for (Track track : tracks) {
            TrackWithArtistsDTO trackWithArtistsDTO;
            trackWithArtistsDTO = mappingService.mapTrackWithArtistsDTO(track);
            trackWithArtistsDTOs.add(trackWithArtistsDTO);
        }
        return ResponseEntity.ok(trackWithArtistsDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TrackWithArtistsDTO> findTrackById(@PathVariable int id) {
        Track track = trackService.findTrackById(id);
        if (track == null) { return ResponseEntity.notFound().build(); }

        TrackWithArtistsDTO trackwithArtistsDTO = mappingService.mapTrackWithArtistsDTO(track);
        return ResponseEntity.ok(trackwithArtistsDTO);
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
        artistTrackService.removeTrackFromAllArtists(id);
        boolean result = trackService.deleteTrack(id);
        return (result) ? ResponseEntity.accepted().build() : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/reset")
    public ResponseEntity<?> resetTracks() {
        trackService.resetTrackDataStore();
        return ResponseEntity.noContent().build();
    }

}
