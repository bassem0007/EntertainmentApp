package plural.capstone2.EntertainmentApp.DTO;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TrackWithArtistsDTO {

    private int id;
    private String title;
    private int durationSeconds;
//    private Genre genre;
    private int yearReleased;
    private int beatsPerMinute;
    private List<ArtistDTO> artists;

}
