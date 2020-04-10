package club.ensoul.framework.oss;

import club.ensoul.framework.oss.config.AliyunOSS.Bucket;
import club.ensoul.framework.oss.provider.PathProvider;
import com.aliyun.oss.OSS;
import com.aliyun.oss.model.PutObjectResult;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.util.Map;

@Slf4j
public class OSSTemplate {

    private OSS ossClient;
    private Bucket defaultBucket;
    private Map<String, Bucket> buckets;

    public OSSTemplate(OSS ossClient, Bucket defaultBucket, Map<String, Bucket> buckets) {
        this.ossClient = ossClient;
        this.buckets = buckets;
        this.defaultBucket = defaultBucket;
    }

    public OSSResult putObject(String bucketType, String fileUri) {
        Bucket bucket = getBucket(bucketType);
        return putObject(bucketType, new File(fileUri), bucket.getPathProvider());
    }

    public OSSResult putObject(String bucketType, String fileUri, PathProvider pathProvider) {
        return putObject(bucketType, new File(fileUri), pathProvider);
    }

    public OSSResult putObject(String bucketType, File file) {
        Bucket bucket = getBucket(bucketType);
        return putObject(bucketType, file, bucket.getPathProvider());
    }
    
    public OSSResult putObject(String bucketType, File file, PathProvider pathProvider) {
        Bucket bucket = getBucket(bucketType);
        PutObjectResult result = ossClient.putObject(bucket.getBucketName(), pathProvider.path(), file);
        return OSSResult.builder(bucket, pathProvider.path());
    }

    public OSSResult putObject(String bucketType, byte[] bytes) {
        Bucket bucket = getBucket(bucketType);
        return putObject(bucketType, bytes, bucket.getPathProvider());
    }

    public OSSResult putObject(String bucketType, byte[] bytes, PathProvider pathProvider) {
        return putObject(bucketType, new ByteArrayInputStream(bytes), pathProvider);
    }

    public OSSResult putObject(String bucketType, InputStream inputStream) {
        Bucket bucket = getBucket(bucketType);
        return putObject(bucketType, inputStream, bucket.getPathProvider());
    }

    public OSSResult putObject(String bucketType, InputStream inputStream, PathProvider pathProvider) {
        Bucket bucket = getBucket(bucketType);
        PutObjectResult result = ossClient.putObject(bucket.getBucketName(), pathProvider.path(), inputStream);
        return OSSResult.builder(bucket, pathProvider.path());
    }

    private Bucket getBucket(String bucketType) {
        Bucket bucket = buckets.get(bucketType);
        if(bucket != null) {
            return bucket;
        }
        return defaultBucket;
    }

}
