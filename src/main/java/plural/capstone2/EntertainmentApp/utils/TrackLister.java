package plural.capstone2.EntertainmentApp.utils;

import plural.capstone2.EntertainmentApp.domain.Track;

import java.util.List;

public class TrackLister {
    public static String listTracks(List<Track> tracks) {
        if (tracks == null || tracks.isEmpty())
            return "[]";

        StringBuffer trackList = new StringBuffer("[");
        for (Track track : tracks) {
            trackList.append(track.getTitle()).append(", ");
        }
        trackList.replace(trackList.length() -2, trackList.length(), "]");
        return trackList.toString();
    }
}
