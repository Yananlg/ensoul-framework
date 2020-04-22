package club.ensoul.framework.core.converter;

import java.time.Instant;
import java.time.LocalTime;

public class SortTime extends java.util.Date {
    
    @Deprecated
    public SortTime(int hour, int minute) {
        super(70, 0, 1, hour, minute);
    }
    
    public SortTime(long time) {
        super(time);
    }
    
    @Override
    public void setTime(long time) {
        super.setTime(time);
    }
    
    public static SortTime valueOf(String s) {
        int hour;
        int minute;
        int firstColon;
        int secondColon;
        
        if(s == null) {
            throw new IllegalArgumentException();
        }
        
        firstColon = s.indexOf(':');
        secondColon = s.indexOf(':', firstColon + 1);
        if((firstColon > 0) & (secondColon > 0) &
                (secondColon < s.length() - 1)) {
            hour = Integer.parseInt(s.substring(0, firstColon));
            minute =
                    Integer.parseInt(s.substring(firstColon + 1, secondColon));
        } else {
            throw new IllegalArgumentException();
        }
        
        return new SortTime(hour, minute);
    }
    
    @SuppressWarnings("deprecation")
    public static SortTime valueOf(LocalTime time) {
        return new SortTime(time.getHour(), time.getMinute());
    }
    
    @SuppressWarnings("deprecation")
    public LocalTime toLocalTime() {
        return LocalTime.of(getHours(), getMinutes());
    }
    
    @Override
    @SuppressWarnings("deprecation")
    public String toString() {
        int hour = super.getHours();
        int minute = super.getMinutes();
        String hourString;
        String minuteString;
        
        if(hour < 10) {
            hourString = "0" + hour;
        } else {
            hourString = Integer.toString(hour);
        }
        if(minute < 10) {
            minuteString = "0" + minute;
        } else {
            minuteString = Integer.toString(minute);
        }
        return (hourString + ":" + minuteString);
    }
    
    @Override
    @Deprecated
    public int getYear() {
        throw new IllegalArgumentException();
    }
    
    @Override
    @Deprecated
    public int getMonth() {
        throw new IllegalArgumentException();
    }
    
    @Override
    @Deprecated
    public int getDay() {
        throw new IllegalArgumentException();
    }
    
    @Override
    @Deprecated
    public int getDate() {
        throw new IllegalArgumentException();
    }
    
    @Override
    @Deprecated
    public void setYear(int i) {
        throw new IllegalArgumentException();
    }
    
    @Override
    @Deprecated
    public void setMonth(int i) {
        throw new IllegalArgumentException();
    }
    
    @Override
    @Deprecated
    public void setDate(int i) {
        throw new IllegalArgumentException();
    }
    
    
    @Override
    public Instant toInstant() {
        throw new UnsupportedOperationException();
    }
}
