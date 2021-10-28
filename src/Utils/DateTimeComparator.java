package Utils;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.*;

public class DateTimeComparator {

    private Map<String, Integer> timestamp;
    private String day;

    public String getDay(){
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

    public boolean isOpen(String operatingHours){
        String[] hours = operatingHours.split("-");
        int opening = Integer.parseInt(hours[0].replace(":", ""));
        int closing = Integer.parseInt(hours[1].replace(":", ""));

        int currTime = timestamp.get(day);
        if(currTime >= opening && currTime <= closing) return true;

        return false;
    }

    public void setDay(String day){
        this.day = day;
    }

    public void setTimestamp(Map<String, Integer> timestamp){
        this.timestamp = timestamp;
    }
    
}
