package plural.capstone2.EntertainmentApp.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import plural.capstone2.EntertainmentApp.DTO.ArtistDTO;
import plural.capstone2.EntertainmentApp.DTO.TrackDTO;
import plural.capstone2.EntertainmentApp.domain.Artist;
import plural.capstone2.EntertainmentApp.domain.Track;
import plural.capstone2.EntertainmentApp.service.ArtistService;
import plural.capstone2.EntertainmentApp.service.ArtistTrackService;
import plural.capstone2.EntertainmentApp.service.MappingService;
import plural.capstone2.EntertainmentApp.service.TrackService;

@RestController
@RequestMapping("/main")
@RequiredArgsConstructor
public class ArtistTrackController {

    private final ArtistTrackService artistTrackService;
    private final ArtistService artistService;
    private final TrackService trackService;
    private final MappingService mappingService;

    @PostMapping("/artists/{artistId}/tracks/{trackId}")
    public ResponseEntity<ArtistDTO> addTrackToArtist(@PathVariable int artistId, @PathVariable int trackId) {
        boolean result = artistTrackService.addTrackToArtist(artistId, trackId);
        Artist artist = artistService.findArtistById(artistId);
        if (result) {
            ArtistDTO artistDTO = mappingService.mapArtistToDTO(artist);
            return ResponseEntity.ok(artistDTO);
        }
        else { return ResponseEntity.notFound().build(); }
    }

    @DeleteMapping("/artists/{artistId}/tracks/{trackId}")
    public ResponseEntity<?> removeTrackFromArtist(@PathVariable int artistId, @PathVariable int trackId) {
        boolean result = artistTrackService.removeTrackFromArtist(artistId, trackId);
        return result ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    @PostMapping("/tracks/{trackId}/artists/{artistId}")
    public ResponseEntity<TrackDTO> addArtistToTrack(@PathVariable int trackId, @PathVariable int artistId) {
        boolean result = artistTrackService.addTrackToArtist(trackId, artistId);
        Track track = trackService.findTrackById(trackId);
        if (result) {
            TrackDTO trackDTO = mappingService.mapTrackToDTO(track);
            return ResponseEntity.ok(trackDTO);
        }
        else { return ResponseEntity.notFound().build(); }
    }

    @DeleteMapping("/tracks/{trackId}/artists/{artistId}")
    public ResponseEntity<?> removeArtistFromTrack(@PathVariable int trackId, @PathVariable int artistId) {
        boolean result = artistTrackService.removeTrackFromArtist(trackId, artistId);
        return result ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

}
