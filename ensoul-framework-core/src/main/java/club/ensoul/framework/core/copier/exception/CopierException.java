package club.ensoul.framework.core.copier.exception;

public class CopierException extends RuntimeException {

    public CopierException() {
        super();
    }

    public CopierException(String message) {
        super(message);
    }

    public CopierException(String message, Throwable cause) {
        super(message, cause);
    }

    public CopierException(Throwable cause) {
        super(cause);
    }

    protected CopierException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
