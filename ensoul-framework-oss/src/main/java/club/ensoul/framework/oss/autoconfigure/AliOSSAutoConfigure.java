package club.ensoul.framework.oss.autoconfigure;

import club.ensoul.framework.oss.OSSTemplate;
import club.ensoul.framework.oss.config.AliyunOSS;
import com.aliyun.oss.ClientBuilderConfiguration;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(name = "enabled", prefix = "oss", havingValue= "true")
@EnableConfigurationProperties({AliyunOSS.class})
public class AliOSSAutoConfigure {

    @Bean
    public OSSTemplate ossTemplate(OSS ossClient, AliyunOSS aliyunOSS) {
        AliyunOSS.Bucket defaultBucket = new AliyunOSS.Bucket(aliyunOSS.getBucketName(), aliyunOSS.getBucketUrl(), aliyunOSS.getPathProvider());
        return new OSSTemplate(ossClient, defaultBucket, aliyunOSS.getBuckets());
    }

    @Bean(destroyMethod = "shutdown")
    public OSS oss(AliyunOSS aliyunOSS) {

        // 创建ClientConfiguration实例，按照您的需要修改默认参数。
        ClientBuilderConfiguration conf = new ClientBuilderConfiguration();
//        for (Map.Entry<String, Object> entry : aliyunOSS.getConf().entrySet()) {
//
//        }

        return new OSSClientBuilder()
                .build(aliyunOSS.getEndPoint(),
                        aliyunOSS.getAccessKeyId(),
                        aliyunOSS.getAccessKeySecret(),
                        conf);

    }

}
