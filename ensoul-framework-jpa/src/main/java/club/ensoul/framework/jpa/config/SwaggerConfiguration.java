package club.ensoul.framework.jpa.config;

import com.github.xiaoymin.swaggerbootstrapui.annotations.EnableSwaggerBootstrapUI;
import com.spring4all.swagger.EnableSwagger2Doc;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * WEB 自动配置
 *
 * @author chenpeng
 */
@Configuration
@Profile({"test", "dev"})
@EnableSwagger2Doc
@EnableSwaggerBootstrapUI
public class SwaggerConfiguration implements WebMvcConfigurer {
    

}

