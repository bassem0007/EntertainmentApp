package plural.capstone2.EntertainmentApp.utils;

public class TimeFormatter {
    public static String formatTrackDurationInMinutesAndSeconds(int durationSeconds) {
        if (durationSeconds < 0) { throw new IllegalArgumentException("Duration must be greater than or equal to zero"); }

        int minutes = Math.floorDiv(durationSeconds, 60);
        int seconds = durationSeconds % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }
}
