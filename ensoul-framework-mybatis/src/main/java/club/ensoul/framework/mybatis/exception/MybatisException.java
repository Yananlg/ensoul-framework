package club.ensoul.framework.mybatis.exception;

public class MybatisException extends RuntimeException {
    
    
    public MybatisException() {
        super();
    }
    
    public MybatisException(String message) {
        super(message);
    }
    
    public MybatisException(String message, Throwable cause) {
        super(message, cause);
    }
    
    
    public MybatisException(Throwable cause) {
        super(cause);
    }
    
    protected MybatisException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
