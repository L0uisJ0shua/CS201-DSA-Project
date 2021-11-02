package Utils;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.*;

public class DateTimeComparator {

    private Map<String, Integer> timestamp;
    private String day;

    public String getDay() {
        LocalDateTime currentTimestamp = LocalDateTime.now();
        DayOfWeek currentDay = currentTimestamp.getDayOfWeek();
        String time = "" + currentTimestamp.getHour() + currentTimestamp.getMinute();
        int curretTime = Integer.parseInt(time);

        Map<String, Integer> dateTimeMap = new HashMap<String, Integer>();

        setTimestamp(dateTimeMap);

        String localDayStartingChar = currentDay.toString().substring(0, 1);
        String localDay = currentDay.toString().substring(1).toLowerCase();
        String dayOfWeek = localDayStartingChar + localDay;
        setDay(dayOfWeek);

        dateTimeMap.put(dayOfWeek, curretTime);

        return day;
    }

    public boolean isOpen(String operatingHours, int currHr, int currMin) {
        String[] operatingHrs = operatingHours.split("-");
        String[] opening = operatingHrs[0].split(":");
        int openingHr = Integer.parseInt(opening[0]);
        int openingMin = Integer.parseInt(opening[1]);

        String[] closing = operatingHrs[1].split(":");
        int closingHr = Integer.parseInt(closing[0]);
        int closingMin = Integer.parseInt(closing[1]);

        // Some stores open 24/7.
        if (closingHr == 0)
            closingHr = 23;
        if (closingMin == 0)
            closingMin = 59;

        return currHr >= openingHr && currMin >= openingMin && currHr <= closingHr && currMin <= closingMin;

    }

    public void setDay(String day) {
        this.day = day;
    }

    public void setTimestamp(Map<String, Integer> timestamp) {
        this.timestamp = timestamp;
    }

}
