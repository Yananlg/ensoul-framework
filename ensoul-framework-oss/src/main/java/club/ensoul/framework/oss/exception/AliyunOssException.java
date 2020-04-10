package club.ensoul.framework.oss.exception;

public class AliyunOssException extends RuntimeException {
    
    public AliyunOssException() {
        super();
    }
    
    public AliyunOssException(String message) {
        super(message);
    }
    
    public AliyunOssException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public AliyunOssException(Throwable cause) {
        super(cause);
    }
    
    protected AliyunOssException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }


}
