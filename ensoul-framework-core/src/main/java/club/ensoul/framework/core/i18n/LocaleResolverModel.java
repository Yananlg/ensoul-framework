package club.ensoul.framework.core.i18n;

import cn.hutool.core.collection.CollectionUtil;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.FixedLocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.List;
import java.util.Locale;

public enum LocaleResolverModel {
    
    Session {
        @Override
        public LocaleResolver resolver(I18NProperties i18NProperties) {
            SessionLocaleResolver sessionLocaleResolver = new SessionLocaleResolver();
            sessionLocaleResolver.setDefaultLocale(i18NProperties.getSession().getDefaultLocale());
            sessionLocaleResolver.setDefaultTimeZone(i18NProperties.getSession().getDefaultTimeZone());
            sessionLocaleResolver.setLocaleAttributeName(i18NProperties.getSession().getLocaleAttributeName());
            sessionLocaleResolver.setTimeZoneAttributeName(i18NProperties.getSession().getTimeZoneAttributeName());
            return sessionLocaleResolver;
        }
    },
    
    Cookie {
        @Override
        public LocaleResolver resolver(I18NProperties i18NProperties) {
            CookieLocaleResolver cookieLocaleResolver = new CookieLocaleResolver();
            cookieLocaleResolver.setDefaultLocale(i18NProperties.getCookie().getDefaultLocale());
            cookieLocaleResolver.setDefaultTimeZone(i18NProperties.getCookie().getDefaultTimeZone());
            cookieLocaleResolver.setRejectInvalidCookies(i18NProperties.getCookie().getRejectInvalidCookies());
            cookieLocaleResolver.setLanguageTagCompliant(i18NProperties.getCookie().getLanguageTagCompliant());
            cookieLocaleResolver.setCookieDomain(i18NProperties.getCookie().getCookieDomain());
            cookieLocaleResolver.setCookieMaxAge(i18NProperties.getCookie().getCookieMaxAge());
            cookieLocaleResolver.setCookieHttpOnly(i18NProperties.getCookie().getCookieHttpOnly());
            cookieLocaleResolver.setCookieName(i18NProperties.getCookie().getCookieName());
            cookieLocaleResolver.setCookiePath(i18NProperties.getCookie().getCookiePath());
            cookieLocaleResolver.setCookieSecure(i18NProperties.getCookie().getCookieSecure());
            return cookieLocaleResolver;
        }
    },
    
    AcceptHeader {
        @Override
        public LocaleResolver resolver(I18NProperties i18NProperties) {
            AcceptHeaderLocaleResolver acceptHeaderLocaleResolver = new AcceptHeaderLocaleResolver();
            acceptHeaderLocaleResolver.setDefaultLocale(i18NProperties.getAcceptHeader().getDefaultLocale());
            List<Locale> supportedLocales = i18NProperties.getAcceptHeader().getSupportedLocales();
            if(CollectionUtil.isNotEmpty(supportedLocales)) {
                acceptHeaderLocaleResolver.setSupportedLocales(supportedLocales);
            }
            return acceptHeaderLocaleResolver;
        }
    },
    
    Fixed {
        @Override
        public LocaleResolver resolver(I18NProperties i18NProperties) {
            FixedLocaleResolver fixedLocaleResolver = new FixedLocaleResolver();
            fixedLocaleResolver.setDefaultLocale(i18NProperties.getFixed().getDefaultLocale());
            fixedLocaleResolver.setDefaultTimeZone(i18NProperties.getFixed().getDefaultTimeZone());
            return fixedLocaleResolver;
        }
    };
    
    public abstract LocaleResolver resolver(I18NProperties i18NProperties);
    
}
