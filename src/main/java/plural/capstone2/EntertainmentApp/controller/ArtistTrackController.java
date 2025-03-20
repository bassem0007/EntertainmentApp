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
    public ResponseEntity<Artist> removeTrackFromArtist(@PathVariable int artistId, @PathVariable int trackId) {
        Artist updatedArtist = artistTrackService.removeTrackFromArtist(artistId, trackId);
        return updatedArtist != null? ResponseEntity.ok(updatedArtist) : ResponseEntity.notFound().build();
    }

    @PostMapping("/tracks/{trackId}/artists/{artistId}")
    public ResponseEntity<TrackDTO> addArtistToTrack(@PathVariable int trackId, @PathVariable int artistId) {
        boolean result = artistTrackService.addArtistToTrack(trackId, artistId);
        Track track = trackService.findTrackById(trackId);
        if (result) {
            TrackDTO trackDTO = mappingService.mapTrackToDTO(track);
            return ResponseEntity.ok(trackDTO);
        }
        else { return ResponseEntity.notFound().build(); }
    }

    @DeleteMapping("/tracks/{trackId}/artists/{artistId}")
    public ResponseEntity<Track> removeArtistFromTrack(@PathVariable int trackId, @PathVariable int artistId) {
        Track updatedTrack = artistTrackService.removeArtistFromTrack(trackId, artistId);
        return updatedTrack != null? ResponseEntity.ok(updatedTrack) : ResponseEntity.notFound().build();
    }

//    @GetMapping("/tracks/{trackId}/artists")
//    public ResponseEntity<List<ArtistDTO>> getArtistsForTrack(@PathVariable int trackId) {
//        List<ArtistDTO> artistDTOs = artistService.getArtistsForTrack(trackId);
//        return artistDTOs != null? ResponseEntity.ok(artistDTOs) : ResponseEntity.notFound().build();
//    }

}
