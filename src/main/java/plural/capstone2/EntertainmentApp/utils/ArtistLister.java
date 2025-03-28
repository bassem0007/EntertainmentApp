package plural.capstone2.EntertainmentApp.utils;

import plural.capstone2.EntertainmentApp.domain.Artist;

import java.util.List;

public class ArtistLister {
    public static String listArtists(List<Artist> artists) {
        if (artists == null || artists.isEmpty())
            return "[]";

        StringBuilder artistList = new StringBuilder("[");
        for (Artist artist : artists) {
            artistList.append(artist.getName()).append(", ");
        }
        artistList.replace(artistList.length() -2, artistList.length(), "]");
        return artistList.toString();
    }
}
