import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        //create 2 calendars and sets the limits for each calendar
        List<String[]> calendar1 = new ArrayList<>();
        calendar1.add(new String[]{"9:00", "10:30"});
        calendar1.add(new String[]{"12:00", "13:00"});
        calendar1.add(new String[]{"16:00", "18:00"});
        String[] calendar1Limits = new String[]{"9:00", "19:00"};

        List<String[]> calendar2 = new ArrayList<>();
        calendar2.add(new String[]{"10:00", "11:30"});
        calendar2.add(new String[]{"12:30", "14:30"});
        calendar2.add(new String[]{"14:30", "15:00"});
        calendar2.add(new String[]{"16:00", "17:00"});
        String[] calendar2Limits = new String[]{"10:00", "18:30"};

        //set the duration for a meeting
        int meetingDuration = 29;
        //checking if there is enough time between meetings for the requested meeting duration, using 'findAvailableTimes' method to parse the limits of the 2 calendars
        List<String[]> availableTimes = MeetingScheduler.findAvailableTimes(calendar1, calendar1Limits, calendar2, calendar2Limits, meetingDuration);

        System.out.println("Available meeting times:");
        //check if there is any available time after the last meeting and adds it to the list of available times
        for (String[] time : availableTimes) {
            System.out.println(time[0] + " - " + time[1]);
        }

    }
}