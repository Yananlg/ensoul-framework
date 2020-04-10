package club.ensoul.framework.core.response;

public enum ResponseStatus {

    // 成功
    SUCCESS(200, "成功"),
    
    // 4xx 客户端请求问题
    PARAMETER_INVALID(400, "参数错误"),
    UNSUPPORTED_MEDIA_TYPE(415 , "请求类型错误"),
    FAILURE(500, "网络异常，请稍后再试");

    private int status;
    private String message;

    ResponseStatus(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public int status() {
        return status;
    }

    public String message() {
        return message;
    }

}