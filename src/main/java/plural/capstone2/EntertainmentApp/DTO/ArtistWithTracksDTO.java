package plural.capstone2.EntertainmentApp.DTO;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ArtistWithTracksDTO {

    private int id;
    private String name;
    private List<TrackDTO> tracks;

}
