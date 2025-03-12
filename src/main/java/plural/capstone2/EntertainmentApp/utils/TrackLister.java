package plural.capstone2.EntertainmentApp.utils;

import plural.capstone2.EntertainmentApp.domain.Track;

import java.util.List;

public class TrackLister {
    public static String listTracks(List<Track> tracks) {
        if (tracks == null || tracks.isEmpty())
            return "[]";

        StringBuffer sb = new StringBuffer("[");
        for (Track track : tracks) {
            sb.append(track.getTitle()).append(", ");
        }
        return sb.toString();
    }
}
