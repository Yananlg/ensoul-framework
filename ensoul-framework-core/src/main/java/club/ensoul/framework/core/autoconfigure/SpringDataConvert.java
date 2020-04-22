package club.ensoul.framework.core.autoconfigure;

import club.ensoul.framework.core.converter.StringToSortTimeConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.support.GenericConversionService;
import org.springframework.web.bind.support.ConfigurableWebBindingInitializer;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import javax.annotation.PostConstruct;

@Configuration
public class SpringDataConvert {
    
    private final RequestMappingHandlerAdapter requestMappingHandlerAdapter;
    
    public SpringDataConvert(RequestMappingHandlerAdapter requestMappingHandlerAdapter) {this.requestMappingHandlerAdapter = requestMappingHandlerAdapter;}
    
    @PostConstruct
    public void addConversionConfig() {
        ConfigurableWebBindingInitializer initializer = (ConfigurableWebBindingInitializer) requestMappingHandlerAdapter.getWebBindingInitializer();
        assert initializer != null;
        if(initializer.getConversionService() != null) {
            GenericConversionService genericConversionService = (GenericConversionService) initializer.getConversionService();
            //添加字符串转换为日期类型的字符串
            genericConversionService.addConverter(new StringToSortTimeConverter());
        }
    }
}
