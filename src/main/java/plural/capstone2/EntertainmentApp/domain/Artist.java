package plural.capstone2.EntertainmentApp.domain;

import lombok.*;
import plural.capstone2.EntertainmentApp.enums.ArtistType;
import plural.capstone2.EntertainmentApp.enums.Genre;
import plural.capstone2.EntertainmentApp.utils.TrackLister;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
public class Artist {

    private int id;
    private String name;
    private ArtistType artistType;
    private Set<Genre> genres = new HashSet<>();
    private String biography;
    private String nationality;
    private int yearFounded;
    private List<Track> tracks = new ArrayList<>();

    public Artist(String name, ArtistType artistType, String biography, String nationality, int yearFounded) {
        this.name = name;
        this.artistType = artistType;
        this.genres = new HashSet<>();
        this.biography = biography;
        this.nationality = nationality;
        this.yearFounded = yearFounded;
        this.tracks = new ArrayList<>();
    }

    public Artist() {}

    public void updateGenres() {
        Set<Genre> remainingGenres = new HashSet<>();

        for(Track track : tracks) {
            remainingGenres.add(track.getGenre());
        }

        genres.removeIf(genre -> !remainingGenres.contains(genre));
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
