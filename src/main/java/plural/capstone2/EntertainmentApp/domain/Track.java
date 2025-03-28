package plural.capstone2.EntertainmentApp.domain;


import lombok.*;
import plural.capstone2.EntertainmentApp.enums.Genre;
import plural.capstone2.EntertainmentApp.utils.ArtistLister;
import plural.capstone2.EntertainmentApp.utils.TimeFormatter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Track {

    private int id;
    private String title;
    private int durationSeconds;
    private Genre genre;
    private int yearReleased;
    private int beatsPerMinute;
    private List<Artist> artists = new ArrayList<>();

    public Track(String title, int durationSeconds, Genre genre, int yearReleased, int beatsPerMinute) {
        this.title = title;
        this.durationSeconds = durationSeconds;
        this.genre = genre;
        this.artists = new ArrayList<>();
        this.yearReleased = yearReleased;
        this.beatsPerMinute = beatsPerMinute;
    }

    public Track() {
    }

    @Override
    public String toString() {
        return "Track{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", duration=" + TimeFormatter.formatTrackDurationInMinutesAndSeconds(durationSeconds) +
                ", genre=" + genre +
                ", artists=" + ArtistLister.listArtists(artists) +
                ", yearReleased=" + yearReleased +
                ", beatsPerMinute=" + beatsPerMinute +
                '}';
    }
}
