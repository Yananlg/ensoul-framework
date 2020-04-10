package club.ensoul.framework.oss;

import club.ensoul.framework.oss.config.AliyunOSS;
import lombok.Data;

@Data
public class OSSResult {

    private String bucketUrl;
    private String path;

    public static OSSResult builder(AliyunOSS.Bucket bucket, String path){
        OSSResult result = new OSSResult();
        result.bucketUrl = bucket.getBucketUrl();
        result.path = path;
        return result;
    }

    @Override
    public String toString() {
        return path;
    }

}
