package club.ensoul.framework.validator.extend;

public interface Regex {
    
    String MONTH_REGEX_1 = "^(0[1-9]|1[0-2])$";
    String MONTH_REGEX_2 = "^([1-9]|1[0-2])$";
    String MONTH_EN_REGEX_1 = "^(JANUARY|FEBRUARY|MARCH|APRIL|MAY|JUNE|JULY|AUGUST|SEPTEMBER|OCTOBER|NOVEMBER|DECEMBER])$";
    String MONTH_EN_REGEX_2 = "^(JAN|FEB|MAR|APR|MAY|JUN|JUL|AUG|SEP|OCT|NOV|DEC)$";
    String MONTH_ZH_REGEX_1 = "^(一月|二月|三月|四月|五月|六月|七月|八月|九月|十月|十一月|十二月)$";
    String MONTH_ZH_REGEX_2 = "^(一|二|三|四|五|六|七|八|九|十|十一|十二)$";
    
    String DAY_OF_MONTH = "^((0?[1-9])|((1|2)[0-9])|30|31)$";
    
    String CHINESE = "^[u4e00-u9fa5]*$";
    String IP4 = "^((?:(?:25[0-5]|2[0-4]\\\\d|[01]?\\\\d?\\\\d)\\\\.){3}(?:25[0-5]|2[0-4]\\\\d|[01]?\\\\d?\\\\d))$";
    String CHINA_IDCARD = "^([0-9]){7,18}(x|X)?$ 或 ^\\d{8,18}|[0-9x]{8,18}|[0-9X]{8,18}?$";
    
}
