package club.ensoul.framework.core.i18n;

import org.springframework.boot.autoconfigure.context.MessageSourceProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;

@Configuration
@EnableConfigurationProperties({MessageSourceProperties.class, I18NProperties.class})
public class InternationalConfig {
    
    @Bean(name = "messageSource")
    public ResourceBundleMessageSource getMessageResource(MessageSourceProperties messageSourceProperties) {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setDefaultEncoding("UTF-8");
        String[] basenames = messageSourceProperties.getBasename().split(",");
        for(String name : basenames) {
            messageSource.addBasenames(name);
        }
        return messageSource;
    }
    
    /**
     * LocaleResolver 用于设置当前会话的默认的国际化语言
     */
    @Bean
    public LocaleResolver localeResolver(I18NProperties i18N) {
        return i18N.getLocaleResolverModel().resolver(i18N);
    }
    
}
