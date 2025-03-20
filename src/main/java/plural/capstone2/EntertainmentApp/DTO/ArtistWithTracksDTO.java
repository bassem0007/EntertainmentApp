package plural.capstone2.EntertainmentApp.DTO;

import lombok.Getter;
import lombok.Setter;
import plural.capstone2.EntertainmentApp.enums.ArtistType;

import java.util.List;

@Getter
@Setter
public class ArtistWithTracksDTO {

    private int id;
    private String name;
    private ArtistType artistType;
//    private List<Genre> genres;
    private String biography;
    private String nationality;
    private int yearFounded;
    private List<TrackDTO> tracks;

}
