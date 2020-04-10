package club.ensoul.framework.helper;

import java.text.SimpleDateFormat;
import java.util.TimeZone;

public class DateHelper {
    
    public static final String TYPICAL_DATE = "yyyy-MM-dd";
    public static final String TYPICAL_TIME = "HH:mm:ss";
    public static final String TYPICAL_DATETIME = "yyyy-MM-dd HH:mm:ss";
    
    public static final String ZH_CN_DATE = "yyyy年MM月dd日";
    public static final String ZH_CN_TIME = "HH时mm分ss秒";
    public static final String ZH_CN_DATETIME = "yyyy年MM月dd日 HH时mm分ss秒";
    public static final String ZH_TIME_ZONE = "GMT+8";
    
    public static final ThreadLocal<SimpleDateFormat> dateFormat = ThreadLocal.withInitial(() -> new SimpleDateFormat(TYPICAL_DATE));
    public static final ThreadLocal<SimpleDateFormat> timeFormat = ThreadLocal.withInitial(() -> new SimpleDateFormat(TYPICAL_TIME));
    public static final ThreadLocal<SimpleDateFormat> typicalDateTimeFMT = ThreadLocal.withInitial(() -> new SimpleDateFormat(TYPICAL_DATETIME));
    
    public static final ThreadLocal<SimpleDateFormat> zhDateFormat = ThreadLocal.withInitial(() -> new SimpleDateFormat(ZH_CN_DATE));
    public static final ThreadLocal<SimpleDateFormat> zhTimeFormat = ThreadLocal.withInitial(() -> new SimpleDateFormat(ZH_CN_TIME));
    public static final ThreadLocal<SimpleDateFormat> zhDateTimeFormat = ThreadLocal.withInitial(() -> new SimpleDateFormat(ZH_CN_DATETIME));
    
    public static SimpleDateFormat typicalDateTimeFMT() {
        return typicalDateTimeFMT.get();
    }
    
    public static TimeZone zhTimeZone() {
        return TimeZone.getTimeZone(ZH_TIME_ZONE);
    }
    
}
