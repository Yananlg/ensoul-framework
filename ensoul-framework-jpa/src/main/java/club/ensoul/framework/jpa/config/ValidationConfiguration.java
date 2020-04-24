package club.ensoul.framework.jpa.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * /**
 *
 * @author chenpeng
 */
@Configuration
public class ValidationConfiguration {
    
    @Bean
    public MethodValidationPostProcessor methodValidationPostProcessor() {
        MethodValidationPostProcessor methodValidationPostProcessor = new MethodValidationPostProcessor();
        methodValidationPostProcessor.setValidatedAnnotationType(RequestMapping.class);
        return methodValidationPostProcessor;
    }
    
}

