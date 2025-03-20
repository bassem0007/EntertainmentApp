package plural.capstone2.EntertainmentApp.service;

import org.springframework.stereotype.Service;
import plural.capstone2.EntertainmentApp.DTO.ArtistDTO;
import plural.capstone2.EntertainmentApp.DTO.ArtistWithTracksDTO;
import plural.capstone2.EntertainmentApp.DTO.TrackDTO;
import plural.capstone2.EntertainmentApp.DTO.TrackWithArtistsDTO;
import plural.capstone2.EntertainmentApp.domain.Artist;
import plural.capstone2.EntertainmentApp.domain.Track;

import java.util.ArrayList;
import java.util.List;

@Service
public class MappingService {

    public ArtistDTO mapArtistToDTO(Artist artist) {
        ArtistDTO artistDTO = new ArtistDTO();
        artistDTO.setId(artist.getId());
        artistDTO.setName(artist.getName());

        return artistDTO;
    }

    public TrackDTO mapTrackToDTO(Track track) {
        TrackDTO trackDTO = new TrackDTO();
        trackDTO.setId(track.getId());
        trackDTO.setTitle(track.getTitle());

        return trackDTO;
    }

    public ArtistWithTracksDTO mapArtistWithTracksDTO(Artist artist) {
        ArtistWithTracksDTO artistWithTracksDTO = new ArtistWithTracksDTO();
        artistWithTracksDTO.setId(artist.getId());
        artistWithTracksDTO.setName(artist.getName());

        List<TrackDTO> trackDTOs = new ArrayList<>();
        if (artist.getTracks() != null) {
            for (Track track : artist.getTracks()) {
                trackDTOs.add(mapTrackToDTO(track));
            }
        }
        artistWithTracksDTO.setTracks(trackDTOs);
        return artistWithTracksDTO;
    }

    public TrackWithArtistsDTO mapTrackWithArtistsDTO(Track track) {
        TrackWithArtistsDTO trackWithArtistsDTO = new TrackWithArtistsDTO();
        trackWithArtistsDTO.setId(track.getId());
        trackWithArtistsDTO.setTitle(track.getTitle());

        List<ArtistDTO> artistDTOs = new ArrayList<>();
        if (track.getArtists() != null) {
            for (Artist artist : track.getArtists()) {
                artistDTOs.add(mapArtistToDTO(artist));
            }
        }
        trackWithArtistsDTO.setArtists(artistDTOs);
        return trackWithArtistsDTO;
    }
}
