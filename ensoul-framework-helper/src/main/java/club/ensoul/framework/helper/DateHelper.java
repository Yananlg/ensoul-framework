package club.ensoul.framework.helper;

import org.springframework.lang.NonNull;

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
    
    static {
        
        patternFomtMap.put(Pattern.TYPICAL_DATE, Format.TYPICAL_DATE.format);
        patternFomtMap.put(Pattern.TYPICAL_SORT_TIME, Format.TYPICAL_SORT_TIME.format);
        patternFomtMap.put(Pattern.TYPICAL_TIME, Format.TYPICAL_TIME.format);
        patternFomtMap.put(Pattern.TYPICAL_DATETIME, Format.TYPICAL_DATETIME.format);
        patternFomtMap.put(Pattern.ZH_DATE, Format.ZH_DATE.format);
        patternFomtMap.put(Pattern.ZH_TIME, Format.ZH_TIME.format);
        patternFomtMap.put(Pattern.ZH_SORT_TIME, Format.ZH_SORT_TIME.format);
        patternFomtMap.put(Pattern.ZH_DATETIME, Format.ZH_DATETIME.format);
        
        regexFomtMap.put(Regex.TYPICAL_DATE, Format.TYPICAL_DATE.format);
        regexFomtMap.put(Regex.TYPICAL_SORT_TIME, Format.TYPICAL_SORT_TIME.format);
        regexFomtMap.put(Regex.TYPICAL_TIME, Format.TYPICAL_TIME.format);
        regexFomtMap.put(Regex.TYPICAL_DATETIME, Format.TYPICAL_DATETIME.format);
        regexFomtMap.put(Regex.TYPICAL_DATETIME_MILLISECOND, Format.TYPICAL_DATETIME_MILLISECOND.format);
        
    }
    
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
        
        public ThreadLocal<SimpleDateFormat> format;
        
        Format(ThreadLocal<SimpleDateFormat> format) {
            this.format = format;
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
