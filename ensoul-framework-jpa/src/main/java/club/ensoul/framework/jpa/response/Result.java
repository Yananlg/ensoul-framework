package club.ensoul.framework.jpa.response;

import club.ensoul.framework.core.response.AbstractResult;
import club.ensoul.framework.core.response.ResponseStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.domain.Pageable;

/**
 * 接口数据响应格式定义
 *
 * @author Ensoul
 */
@Data
@SuppressWarnings("all")
@ApiModel(description = "返回响应数据")
public class Result<T> extends AbstractResult<T> {
    
    protected static final Result DEFAULT_SUCCESS = new Result<>(ResponseStatus.SUCCESS);
    
    protected static final Result DEFAULT_FAILURE = new Result<>(ResponseStatus.FAILURE);
    
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @ApiModelProperty(value = "返回分页数据，无分页时不返回该字段")
    protected Pageable pageable;
    
    protected Result() {
        super();
    }
    
    protected Result(ResponseStatus responseStatus) {
        super(responseStatus);
    }
    
    protected Result(String message, ResponseStatus responseStatus) {
        super(message, responseStatus);
    }
    
    protected Result(Integer status, String message) {
        super(status, message);
    }
    
    protected Result(Integer status, String message, T data) {
        super(status, message, data);
    }
    
    protected Result(ResponseStatus responseStatus, T data) {
        super(responseStatus, data);
    }
    
    protected Result(ResponseStatus responseStatus, T data, Pageable pageable) {
        super(responseStatus, data);
        this.pageable = pageable;
    }
    
    public static Result success() {
        return DEFAULT_SUCCESS;
    }
    
    public static <T> Result failure() {
        return DEFAULT_FAILURE;
    }
    
    public static <T> Result<T> successm(String message) {
        return new Result(message, ResponseStatus.SUCCESS);
    }
    
    public static <T> Result<T> failurem(String message) {
        return new Result(message, ResponseStatus.FAILURE);
    }
    
    public static <T> Result<T> success(T data) {
        return new Result(ResponseStatus.SUCCESS, data);
    }
    
    public static <T> Result<T> success(T data, Pageable pageable) {
        return new Result(ResponseStatus.SUCCESS, data, pageable);
    }
    
    public static <T> Result<T> success(ResponseStatus responseStatus, T data) {
        return new Result(responseStatus, data);
    }
    
    public static <T> Result<T> success(Integer status, String message, T data) {
        if(status == null) {
            status = ResponseStatus.SUCCESS.status();
        }
        if(message == null) {
            message = ResponseStatus.SUCCESS.message();
        }
        return new Result(status, message, data);
    }
    
    public static <T> Result<T> failure(ResponseStatus responseStatus) {
        return new Result(responseStatus);
    }
    
    public static <T> Result<T> failure(ResponseStatus responseStatus, T data) {
        return new Result(responseStatus, data);
    }
    
    public static <T> Result<T> failure(Integer status, String message) {
        return new Result(status, message);
    }
    
    public static <T> Result<T> failure(Integer status, String message, T data) {
        if(status == null) {
            status = ResponseStatus.SUCCESS.status();
        }
        if(message == null) {
            message = ResponseStatus.SUCCESS.message();
        }
        return new Result(status, message, data);
    }
    
}


