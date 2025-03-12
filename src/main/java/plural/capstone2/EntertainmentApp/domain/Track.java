package plural.capstone2.EntertainmentApp.domain;

import lombok.Getter;
import lombok.Setter;
import plural.capstone2.EntertainmentApp.enums.Genre;
import plural.capstone2.EntertainmentApp.utils.ArtistLister;

import java.util.List;

@Getter
@Setter
public class Track {
    private int id;
    private String title;
    private int durationSeconds;
    private Genre genre;
    private List<Artist> artists;
    private int yearReleased;
    private int beatsPerMinute;

    public Track(String title, int durationSeconds, Genre genre, List<Artist> artists, int yearReleased, int beatsPerMinute) {
        this.title = title;
        this.durationSeconds = durationSeconds;
        this.genre = genre;
        this.artists = artists;
        this.yearReleased = yearReleased;
        this.beatsPerMinute = beatsPerMinute;
    }

    @Override
    public String toString() {
        return "Track{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", durationSeconds=" + durationSeconds +
                ", genre=" + genre +
                ", artists=" + ArtistLister.listArtists(artists) +
                ", yearReleased=" + yearReleased +
                ", beatsPerMinute=" + beatsPerMinute +
                '}';
    }
}
