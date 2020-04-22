package club.ensoul.framework.core.i18n;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

@Data
@ConfigurationProperties(prefix = "spring.message")
public class I18NProperties {
    
    private LocaleResolverModel localeResolverModel = LocaleResolverModel.AcceptHeader;
    private Session session;
    private AcceptHeader acceptHeader;
    private Fixed fixed;
    private Cookie cookie;
    
    @Data
    public static class Session {
        private Locale defaultLocale = Locale.getDefault();
        private TimeZone defaultTimeZone = TimeZone.getDefault();
        private String localeAttributeName = SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME;
        private String timeZoneAttributeName = SessionLocaleResolver.TIME_ZONE_SESSION_ATTRIBUTE_NAME;
    }
    
    @Data
    public static class AcceptHeader {
        private Locale defaultLocale = Locale.getDefault();
        private List<Locale> supportedLocales;
    }
    
    @Data
    public static class Fixed {
        private Locale defaultLocale = Locale.getDefault();
        private TimeZone defaultTimeZone = TimeZone.getDefault();
    }
    
    @Data
    public static class Cookie {
        private Locale defaultLocale = Locale.getDefault();
        private TimeZone defaultTimeZone = TimeZone.getDefault();

        private Boolean rejectInvalidCookies = true;
        private Boolean languageTagCompliant = true;
        private String cookieDomain;
        private Integer cookieMaxAge = -1;
        private Boolean cookieHttpOnly = true;
        private String cookieName = "language";
        private String cookiePath = "/";
        private Boolean cookieSecure = true;
        
    }
    
    
}
