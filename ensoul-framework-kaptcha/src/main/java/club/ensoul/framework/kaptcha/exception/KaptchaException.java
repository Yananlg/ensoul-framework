package club.ensoul.framework.kaptcha.exception;

public class KaptchaException extends RuntimeException {
    
    
    public KaptchaException() {
        super();
    }
    
    public KaptchaException(String message) {
        super(message);
    }
    
    public KaptchaException(String message, Throwable cause) {
        super(message, cause);
    }
    
    
    public KaptchaException(Throwable cause) {
        super(cause);
    }
    
    protected KaptchaException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
