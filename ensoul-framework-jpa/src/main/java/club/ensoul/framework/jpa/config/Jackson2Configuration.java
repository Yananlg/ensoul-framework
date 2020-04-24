package club.ensoul.framework.jpa.config;

import club.ensoul.framework.core.domain.DescribeEnum;
import club.ensoul.framework.core.domain.MappedEnum;
import club.ensoul.framework.core.jackson.DescribeEnumSerialzer;
import club.ensoul.framework.core.jackson.MappedEnumSerialzer;
import club.ensoul.framework.helper.DateHelper;
import club.ensoul.framework.helper.DateHelper.Format;
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

import javax.annotation.Resource;
import java.util.TimeZone;

@Configuration
@EnableSpringDataWebSupport
public class Jackson2Configuration {
    
    @Resource
    private Jackson2ObjectMapperBuilder jackson2ObjectMapperBuilder;
    
    @Bean
    public Hibernate5Module hibernate5Module() {
        Hibernate5Module module = new Hibernate5Module();
        module.enable(Hibernate5Module.Feature.WRITE_MISSING_ENTITIES_AS_NULL);
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
        objectMapper.setTimeZone(TimeZone.getTimeZone(DateHelper.ZH_TIME_ZONE));
        objectMapper.setDateFormat(Format.TYPICAL_DATETIME.format.get());
        
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        objectMapper.configure(SerializationFeature.WRITE_DURATIONS_AS_TIMESTAMPS, false);
        
        SimpleModule module = new SimpleModule();
        module.addSerializer(MappedEnum.class, MappedEnumSerialzer.instance());
        module.addSerializer(DescribeEnum.class, DescribeEnumSerialzer.instance());
        objectMapper.registerModule(module);
        
        return new MappingJackson2HttpMessageConverter(objectMapper);
    }
    
}

