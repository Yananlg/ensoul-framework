package club.ensoul.framework.oss.config;

import club.ensoul.framework.oss.provider.DefalutPathProvider;
import club.ensoul.framework.oss.provider.PathProvider;
import com.aliyun.oss.common.utils.StringUtils;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import java.util.HashMap;
import java.util.Map;

@Data
@Validated
@ConfigurationProperties(prefix = "oss")
public class AliyunOSS {

        private static final String SEPARATOR1 = "/";
        private static final String SEPARATOR2 = "\\";

        /** 是否开启自动配置 */
        private boolean enabled = true;

        /** 地域节点 */
        private String endPoint;

        /** accessKeyId */
        private String accessKeyId;

        /** accessKeySecret */
        private String accessKeySecret;

        /** bucket 名称 */
        private String bucketName;

        /** Bucket 域名 */
        private String bucketUrl;

        private PathProvider pathProvider = new DefalutPathProvider();

        private Map<String, Bucket> buckets = new HashMap<>();

        public void setBucketUrl(String bucketUrl) {
                this.bucketUrl = bucketUrl;
                if(!bucketUrl.endsWith(SEPARATOR1) && !bucketUrl.endsWith(SEPARATOR2)) {
                        this.bucketUrl += SEPARATOR1;
                }
        }

        public void setPathProvider(String pathProvider) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
                this.pathProvider = (PathProvider) Class.forName(pathProvider).newInstance();
        }

        public Map<String, Bucket> getBuckets() {
                for(Map.Entry<String, Bucket> entry : buckets.entrySet()) {
                        Bucket bucket = entry.getValue();
                        if(StringUtils.isNullOrEmpty(bucket.getBucketName())) {
                                bucket.setBucketName(bucketName);
                        }
                        if(StringUtils.isNullOrEmpty(bucket.getBucketUrl())) {
                                bucket.setBucketUrl(bucketUrl);
                        }
                        if(bucket.getPathProvider() == null) {
                                bucket.setOnPathProvider(pathProvider);
                        }
                }
        return buckets;
        }

        @Data
        public static class Bucket {

                /** bucket 名称 */
                private String bucketName;
                /** Bucket 域名 */
                private String bucketUrl;

                private PathProvider pathProvider = new DefalutPathProvider();

                public Bucket(){}

                public Bucket(String bucketName, String bucketUrl, PathProvider pathProvider) {
                        this.bucketName = bucketName;
                        this.bucketUrl = bucketUrl;
                        this.pathProvider = pathProvider;
                }

                public void setPathProvider(String pathProvider) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
                        this.pathProvider = (PathProvider) Class.forName(pathProvider).newInstance();
                }

                public void setOnPathProvider(PathProvider pathProvider) {
                        this.pathProvider = pathProvider;
                }

        }

}
