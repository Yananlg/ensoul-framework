package club.ensoul.framework.jpa.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

import javax.annotation.PostConstruct;

@Configuration
public class ResourceConfiguration {
    
    private final ResourceHandlerRegistry resourceHandlerRegistry;
    
    public ResourceConfiguration(ResourceHandlerRegistry resourceHandlerRegistry) {
        this.resourceHandlerRegistry = resourceHandlerRegistry;
    }
    
    @PostConstruct
    public void addResourceHandlers() {
        resourceHandlerRegistry.addResourceHandler("/**").addResourceLocations("classpath:/statics/");
        resourceHandlerRegistry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
        resourceHandlerRegistry.addResourceHandler("doc.html").addResourceLocations("classpath:/META-INF/resources/");
        resourceHandlerRegistry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    }
    
}

