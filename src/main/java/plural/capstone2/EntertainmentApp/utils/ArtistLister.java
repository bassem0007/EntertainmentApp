package plural.capstone2.EntertainmentApp.utils;

import plural.capstone2.EntertainmentApp.domain.Artist;

import java.util.List;

public class ArtistLister {
    public static String listArtists(List<Artist> artists) {
        if (artists == null || artists.isEmpty())
            return "[]";

        StringBuffer sb = new StringBuffer("[");
//        for (Artist artist : artists) {
//            sb.append(artist.getName()).append(", ");
//        }
        return sb.toString();
    }
}
