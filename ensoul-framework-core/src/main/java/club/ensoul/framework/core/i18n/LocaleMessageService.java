package club.ensoul.framework.core.i18n;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Locale;

@Component
public class LocaleMessageService {
    
    @Resource
    private MessageSource messageSource;
    
    /**
     * @param key 对应messages配置的key.
     * @return 信息
     */
    public String getMessage(String key) {
        return getMessage(key, null);
    }
    
    /**
     * @param key  对应messages配置的key.
     * @param args 数组参数.
     * @return 信息
     */
    public String getMessage(String key, Object[] args) {
        return getMessage(key, args, "");
    }
    
    /**
     * @param key            对应messages配置的key.
     * @param args           数组参数.
     * @param defaultMessage 没有设置key的时候的默认值.
     * @return 信息
     */
    public String getMessage(String key, Object[] args, String defaultMessage) {
        Locale locale = LocaleContextHolder.getLocale();
        return messageSource.getMessage(key, args, defaultMessage, locale);
    }
    
}