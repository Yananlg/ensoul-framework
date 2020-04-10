package club.ensoul.framework.core.autoconfigure;

import club.ensoul.framework.core.copier.SimpleCopier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import java.util.Properties;

@Configuration
public class CopierAutoConfiguration {

    @Bean
    public SimpleCopier simpleCopier(){
        return new SimpleCopier();
    }
    
    
    @Bean
    @ConditionalOnMissingBean
    public SimpleMappingExceptionResolver simpleMappingExceptionResolver() {
        SimpleMappingExceptionResolver simpleMappingExceptionResolver = new SimpleMappingExceptionResolver();
        Properties mappings = new Properties();
        //数据库异常处理
        mappings.setProperty("DatabaseException", "databaseError");
        mappings.setProperty("DisabledAccountException", "/403");//禁用的帐号
        mappings.setProperty("LockedAccountException", "/403");//锁定的帐号
        mappings.setProperty("UnknownAccountException", "/403");//错误的帐号
        mappings.setProperty("ExcessiveAttemptsException", "/403");//登录失败次数过多
        mappings.setProperty("IncorrectCredentialsException", "/403");//错误的凭证
        mappings.setProperty("ExpiredCredentialsException", "/403");//过期的凭证
        simpleMappingExceptionResolver.setExceptionMappings(mappings);
        simpleMappingExceptionResolver.setDefaultErrorView("error");
        simpleMappingExceptionResolver.setExceptionAttribute("ex");
        return simpleMappingExceptionResolver;
    }
    
    
}