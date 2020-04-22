package club.ensoul.framework.jpa.config;

import club.ensoul.framework.core.domain.MappedEnum;
import club.ensoul.framework.core.jackson.EnumDeserializer;
import club.ensoul.framework.core.jackson.DescribeEnumSerialzer;
import club.ensoul.framework.helper.DateHelper;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

/**
 * WEB 自动配置
 *
 * @author chenpeng
 */
@Configuration
@EnableSpringDataWebSupport
public class WebConfiguration implements WebMvcConfigurer {
    
    @Resource
    private Jackson2ObjectMapperBuilder jackson2ObjectMapperBuilder;
    
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**").addResourceLocations("classpath:/statics/");
        registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("doc.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    }
    
    /**
     * 启用方法参数的校验能力
     *
     * @return {@link MethodValidationPostProcessor}
     */
    @Bean
    public MethodValidationPostProcessor methodValidationPostProcessor() {
        MethodValidationPostProcessor methodValidationPostProcessor = new MethodValidationPostProcessor();
        methodValidationPostProcessor.setValidatedAnnotationType(RequestMapping.class);
        return methodValidationPostProcessor;
    }
    
    @Bean
    public Hibernate5Module hibernate5Module() {
        Hibernate5Module module = new Hibernate5Module();
        module.disable(Hibernate5Module.Feature.USE_TRANSIENT_ANNOTATION);
        module.enable(Hibernate5Module.Feature.SERIALIZE_IDENTIFIER_FOR_LAZY_NOT_LOADED_OBJECTS);
        return module;
    }
    
    @Bean
    public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter(Hibernate5Module hibernate5Module) {
        ObjectMapper objectMapper = jackson2ObjectMapperBuilder.build();
        // 注册hibernate5Module解析器
        objectMapper.registerModule(hibernate5Module);
        //反序列化的时候如果多了其他属性,不抛出异常
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        // 允许出现单引号
        objectMapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
        //如果是空对象的时候,不抛异常
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        // 使用getter取代setter探测属性
        objectMapper.configure(MapperFeature.USE_GETTERS_AS_SETTERS, true);
        // 设置日期格式化
        objectMapper.setDateFormat(DateHelper.typicalDateTimeFMT());
        objectMapper.setTimeZone(DateHelper.zhTimeZone());
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        objectMapper.configure(SerializationFeature.WRITE_DURATIONS_AS_TIMESTAMPS, false);
        
        SimpleModule module = new SimpleModule();
        module.addDeserializer(MappedEnum.class, EnumDeserializer.instance());
        module.addSerializer(MappedEnum.class, DescribeEnumSerialzer.instance());
        objectMapper.registerModule(module);
        
        return new MappingJackson2HttpMessageConverter(objectMapper);
    }
    
}

