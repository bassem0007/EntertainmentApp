package plural.capstone2.EntertainmentApp.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import plural.capstone2.EntertainmentApp.enums.ArtistType;
import plural.capstone2.EntertainmentApp.enums.Genre;
import plural.capstone2.EntertainmentApp.utils.TrackLister;

import java.util.ArrayList;
import java.util.List;

@Data
public class Artist {

    @Id
    private int id;
    private String name;
    private ArtistType artistType;
    private List<Genre> genres;
    private String biography;
    private String nationality;
    private int yearFounded;
    private List<Track> tracks;

    public Artist(@NonNull String name, ArtistType artistType, String biography, String nationality, int yearFounded) {
        this.name = name;
        this.artistType = artistType;
        this.genres = new ArrayList<>();
        this.biography = biography;
        this.nationality = nationality;
        this.yearFounded = yearFounded;
        this.tracks = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "Artist{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", artistType=" + artistType +
                ", genres=" + genres.toString() +
                ", biography='" + biography + '\'' +
                ", nationality='" + nationality + '\'' +
                ", yearFounded='" + yearFounded + '\'' +
                ", tracks=" + TrackLister.listTracks(tracks) +
                '}';
    }
}
