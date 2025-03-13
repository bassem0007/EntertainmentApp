package plural.capstone2.EntertainmentApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import plural.capstone2.EntertainmentApp.domain.Artist;
import plural.capstone2.EntertainmentApp.domain.Track;
import plural.capstone2.EntertainmentApp.enums.ArtistType;
import plural.capstone2.EntertainmentApp.enums.Genre;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class EntertainmentAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(EntertainmentAppApplication.class, args);
		init();
	}

	public static void init() {
		Artist artist = new Artist("Pink FLoyd", ArtistType.GROUP, "Psychedelic", "British", 1964);
		ArrayList<Track> tracks = new ArrayList<>();
		Track track1 = new Track("High Hopes", 450, Genre.ROCK, List.of(artist), 1980, 85);
		tracks.add(track1);
		Track track2 = new Track("Comfortably Numb", 400, Genre.ROCK, List.of(artist), 1990, 80);
		tracks.add(track2);
		artist.setTracks(tracks);
		System.out.println(track1);
	}
}
