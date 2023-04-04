import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class MeetingScheduler {

    //method 'findAvailableTimes' takes in the booked calendars and their limits, as well as the require meeting time in minutes, and returns a list of available times for a meeting
    public static List<String[]> findAvailableTimes(List<String[]> calendar1, String[] calendar1Limits,
                                                    List<String[]> calendar2, String[] calendar2Limits,
                                                    int meetingTimeMinutes) {
        //create an empty list called 'availableTimes' to store the available meeting times
        List<String[]> availableTimes = new ArrayList<>();

        //parse the limits of the 2 calendars into 'LocalTime' objects using 'LocalTime.parse' method and the 'DateTimeFormatter' class
        LocalTime calendar1StartTime = LocalTime.parse(calendar1Limits[0], DateTimeFormatter.ofPattern("H:mm"));
        LocalTime calendar1EndTime = LocalTime.parse(calendar1Limits[1], DateTimeFormatter.ofPattern("H:mm"));
        LocalTime calendar2StartTime = LocalTime.parse(calendar2Limits[0], DateTimeFormatter.ofPattern("H:mm"));
        LocalTime calendar2EndTime = LocalTime.parse(calendar2Limits[1], DateTimeFormatter.ofPattern("H:mm"));

        //combine the meetings in both calendars into a single list, and sorts them in ascending order of their start times using a lambda expression
        List<String[]> meetings = new ArrayList<>();
        meetings.addAll(calendar1);
        meetings.addAll(calendar2);
        meetings.sort(Comparator.comparing(m -> LocalTime.parse(m[0], DateTimeFormatter.ofPattern("H:mm"))));

        //initializes the 'startTime' variable to the later of the start times of the two calendars
        LocalTime startTime = calendar1StartTime.isAfter(calendar2StartTime) ? calendar1StartTime : calendar2StartTime;

        //Check if is any available time between the current meeting and the next meeting, or the end of the calendar
        for (String[] meeting : meetings) {
            //parse the start and end times of the meeting into 'LocalTime' objects using 'LocalTime.parse' method and the 'DateTimeFormatter' class
            LocalTime meetingStartTime = LocalTime.parse(meeting[0], DateTimeFormatter.ofPattern("H:mm"));
            LocalTime meetingEndTime = LocalTime.parse(meeting[1], DateTimeFormatter.ofPattern("H:mm"));
            //if the start of the meeting is after the current 'startTime', there is available time between the previous meeting and the current meeting, or the start of the calendar
            if (meetingStartTime.isAfter(startTime)) {
                if (startTime.plusMinutes(meetingTimeMinutes).isBefore(meetingStartTime)) {
                    //found available time
                    availableTimes.add(new String[] {formatTime(startTime), formatTime(meetingStartTime)});
                }
                startTime = meetingEndTime.isAfter(meetingStartTime) ? meetingEndTime : meetingStartTime;
            } else if (meetingEndTime.isAfter(startTime)) {
                startTime = meetingEndTime;
            }
        }

        //add final available time after the last meeting to the 'availableTimes'
        if (calendar1EndTime.isAfter(startTime) && calendar2EndTime.isAfter(startTime.plusMinutes(meetingTimeMinutes))) {
            LocalTime endTime = calendar1EndTime.isBefore(calendar2EndTime) ? calendar1EndTime : calendar2EndTime;
            availableTimes.add(new String[] {formatTime(startTime), formatTime(endTime)});
        }

        return availableTimes;
    }

    //takes in a 'LocalTime' object and formats it into a 'string' in the format of 'H:mm'
    //this method is used to format the start and end times of the available times before adding them to the 'availableTimes' list
    private static String formatTime(LocalTime time) {
        return time.format(DateTimeFormatter.ofPattern("H:mm"));
    }
}