package club.ensoul.framework.helper;

import org.springframework.lang.NonNull;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class DateHelper {
    
    public static final String ZH_TIME_ZONE = "GMT+8";
    
    private static ConcurrentMap<Pattern, ThreadLocal<SimpleDateFormat>> patternFomtMap = new ConcurrentHashMap<>();
    private static ConcurrentMap<Regex, ThreadLocal<SimpleDateFormat>> regexFomtMap = new ConcurrentHashMap<>();
    //
    // public static final ThreadLocal<SimpleDateFormat> typicalTimestampFormat = ThreadLocal.withInitial(() -> new SimpleDateFormat(Pattern..pattern));
    //
    // public static final ThreadLocal<SimpleDateFormat> typicalDateFormat = ThreadLocal.withInitial(() -> new SimpleDateFormat(Pattern.TYPICAL_DATE.pattern));
    // public static final ThreadLocal<SimpleDateFormat> typicalSortTimeFormat = ThreadLocal.withInitial(() -> new SimpleDateFormat(Pattern.TYPICAL_SORT_TIME.pattern));
    // public static final ThreadLocal<SimpleDateFormat> typicalTimeFormat = ThreadLocal.withInitial(() -> new SimpleDateFormat(Pattern.TYPICAL_TIME.pattern));
    // public static final ThreadLocal<SimpleDateFormat> typicalDateTimeFormat = ThreadLocal.withInitial(() -> new SimpleDateFormat(Pattern.TYPICAL_DATETIME.pattern));
    // public static final ThreadLocal<SimpleDateFormat> typicalDateTimeMillisecondFormat = ThreadLocal.withInitial(() -> new SimpleDateFormat(Pattern.TYPICAL_DATETIME_MILLISECOND.pattern));
    //
    // public static final ThreadLocal<SimpleDateFormat> zhDateFormat = ThreadLocal.withInitial(() -> new SimpleDateFormat(Pattern.ZH_DATE.pattern));
    // public static final ThreadLocal<SimpleDateFormat> zhSortTimeFormat = ThreadLocal.withInitial(() -> new SimpleDateFormat(Pattern.ZH_SORT_TIME.pattern));
    // public static final ThreadLocal<SimpleDateFormat> zhTimeFormat = ThreadLocal.withInitial(() -> new SimpleDateFormat(Pattern.ZH_TIME.pattern));
    // public static final ThreadLocal<SimpleDateFormat> zhDateTimeFormat = ThreadLocal.withInitial(() -> new SimpleDateFormat(Pattern.ZH_DATETIME.pattern));
    
    public static Date getDate(@NonNull String dateStr) throws ParseException {
        for(Entry<Regex, ThreadLocal<SimpleDateFormat>> entry : regexFomtMap.entrySet()) {
            if(dateStr.matches(entry.getKey().regex)) {
                ThreadLocal<SimpleDateFormat> threadLocal = entry.getValue();
                return threadLocal.get().parse(dateStr);
            }
        }
        return null;
    }
    
    public static java.sql.Date getSQLDate(String dateStr) {
        return java.sql.Date.valueOf(dateStr);
    }
    
    public static java.sql.Time getSQLTime(String dateStr) {
        return java.sql.Time.valueOf(dateStr);
    }
    
    public static java.sql.Timestamp getSQLTimestamp(String dateStr) {
        return java.sql.Timestamp.valueOf(dateStr);
    }
    
    public static void main(String[] args) {
        String s = "2020-12-12 12:15:57";
        // System.out.println(getSQLDate(s));
        // System.out.println(getSQLTime(s));
        System.out.println(getSQLTimestamp(s));
    }
    
    public void init() {
        
        patternFomtMap.put(Pattern.TYPICAL_DATE, Format.TYPICAL_DATE.dateFormatThreadLocal);
        patternFomtMap.put(Pattern.TYPICAL_SORT_TIME, Format.TYPICAL_SORT_TIME.dateFormatThreadLocal);
        patternFomtMap.put(Pattern.TYPICAL_TIME, Format.TYPICAL_TIME.dateFormatThreadLocal);
        patternFomtMap.put(Pattern.TYPICAL_DATETIME, Format.TYPICAL_DATETIME.dateFormatThreadLocal);
        patternFomtMap.put(Pattern.ZH_DATE, Format.ZH_DATE.dateFormatThreadLocal);
        patternFomtMap.put(Pattern.ZH_TIME, Format.ZH_TIME.dateFormatThreadLocal);
        patternFomtMap.put(Pattern.ZH_SORT_TIME, Format.ZH_SORT_TIME.dateFormatThreadLocal);
        patternFomtMap.put(Pattern.ZH_DATETIME, Format.ZH_DATETIME.dateFormatThreadLocal);
        
        // regexFomtMap.put(Regex.TIMESTAMP_REGX, Format.TIMESTAMP_REGX.dateFormatThreadLocal);
        regexFomtMap.put(Regex.TYPICAL_DATE, Format.TYPICAL_DATE.dateFormatThreadLocal);
        regexFomtMap.put(Regex.TYPICAL_SORT_TIME, Format.TYPICAL_SORT_TIME.dateFormatThreadLocal);
        regexFomtMap.put(Regex.TYPICAL_TIME, Format.TYPICAL_TIME.dateFormatThreadLocal);
        regexFomtMap.put(Regex.TYPICAL_DATETIME, Format.TYPICAL_DATETIME.dateFormatThreadLocal);
        regexFomtMap.put(Regex.TYPICAL_DATETIME_MILLISECOND, Format.TYPICAL_DATETIME_MILLISECOND.dateFormatThreadLocal);
        
    }
    
    public enum Format {
    
        TYPICAL_DATE(ThreadLocal.withInitial(() -> new SimpleDateFormat(Pattern.TYPICAL_DATE.pattern))),
        TYPICAL_SORT_TIME(ThreadLocal.withInitial(() -> new SimpleDateFormat(Pattern.TYPICAL_SORT_TIME.pattern))),
        TYPICAL_TIME(ThreadLocal.withInitial(() -> new SimpleDateFormat(Pattern.TYPICAL_TIME.pattern))),
        TYPICAL_DATETIME(ThreadLocal.withInitial(() -> new SimpleDateFormat(Pattern.TYPICAL_DATETIME.pattern))),
        TYPICAL_DATETIME_MILLISECOND(ThreadLocal.withInitial(() -> new SimpleDateFormat(Pattern.TYPICAL_DATETIME_MILLISECOND.pattern))),
        
        ZH_DATE(ThreadLocal.withInitial(() -> new SimpleDateFormat(Pattern.ZH_DATE.pattern))),
        ZH_SORT_TIME(ThreadLocal.withInitial(() -> new SimpleDateFormat(Pattern.ZH_SORT_TIME.pattern))),
        ZH_TIME(ThreadLocal.withInitial(() -> new SimpleDateFormat(Pattern.ZH_TIME.pattern))),
        ZH_DATETIME(ThreadLocal.withInitial(() -> new SimpleDateFormat(Pattern.ZH_DATETIME.pattern))),
        
        ;
        
        public ThreadLocal<SimpleDateFormat> dateFormatThreadLocal;
    
        Format(ThreadLocal<SimpleDateFormat> dateFormatThreadLocal) {
            this.dateFormatThreadLocal = dateFormatThreadLocal;
        }
        
    }
    
    
    public enum Pattern {
        
        TYPICAL_DATE("yyyy-MM-dd"),
        TYPICAL_SORT_TIME("HH:mm"),
        TYPICAL_TIME("HH:mm:ss"),
        TYPICAL_DATETIME("yyyy-MM-dd HH:mm:ss"),
        TYPICAL_DATETIME_MILLISECOND("yyyy-MM-dd HH:mm:ss.SSS"),
        
        ZH_DATE("yyyy年MM月dd日"),
        ZH_TIME("HH时mm分ss秒"),
        ZH_SORT_TIME("HH时mm分"),
        ZH_DATETIME("yyyy年MM月dd日 HH时mm分ss秒"),
        
        ;
        
        public String pattern;
        
        Pattern(String pattern) {
            this.pattern = pattern;
        }
        
    }
    
    public enum Regex {
        
        TIMESTAMP_REGX("^-?[1-9]\\d*$"),
        
        TYPICAL_DATE("^[12][0-9]{3}-(0?[1-9]|1[0-2])-(0?[1-9]|[12]?[0-9]|3[01])$"),
        TYPICAL_SORT_TIME("^([01]?[0-9]|2[0-3]):([0-5]?[0-9])$"),
        TYPICAL_TIME("^([01]?[0-9]|2[0-3]):([0-5]?[0-9]):([0-5]?[0-9])$"),
        
        TYPICAL_DATETIME("^[12][0-9]{3}-(0?[1-9]|1[0-2])-(0?[1-9]|[12]?[0-9]|3[01]) ([01]?[0-9]|2[0-3]):([0-5]?[0-9]):([0-5]?[0-9])$"),
        TYPICAL_DATETIME_MILLISECOND("^[12][0-9]{3}-(0?[1-9]|1[0-2])-(0?[1-9]|[12]?[0-9]|3[01]) ([01]?[0-9]|2[0-3]):([0-5]?[0-9]):([0-5]?[0-9])\\.[0-9]{3}$"),
        ;
        
        public String regex;
        
        Regex(String regex) {
            this.regex = regex;
        }
        
    }
    
}
