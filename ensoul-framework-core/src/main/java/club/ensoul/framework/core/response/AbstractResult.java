package club.ensoul.framework.core.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 接口数据响应格式定义
 *
 * @author Ensoul
 */
@Data
@SuppressWarnings("all")
@ApiModel(description = "返回响应数据")
public abstract class AbstractResult<T> implements Serializable {
    
    @ApiModelProperty(value = "响应码 1:成功 0:失败")
    protected int status;
    
    @ApiModelProperty(value = "错误信息")
    protected String message;
    
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @ApiModelProperty(value = "错误码，无信息时不返回该字段")
    protected Integer errorCode;
    
    @ApiModelProperty(value = "服务器时间")
    protected Date timestamp;
    
    @ApiModelProperty(value = "返回对象数据")
    protected T data;
    
    protected AbstractResult() {
        super();
    }
    
    protected AbstractResult(ResponseStatus ResponseStatus) {
        this.status = ResponseStatus.status();
        this.message = ResponseStatus.message();
        this.timestamp = new Date();
    }
    
    protected AbstractResult(String message, ResponseStatus ResponseStatus) {
        this.status = ResponseStatus.status();
        this.message = message;
        this.timestamp = new Date();
    }
    
    protected AbstractResult(Integer status, String message) {
        this.status = status;
        this.message = message;
        this.timestamp = new Date();
    }
    
    protected AbstractResult(Integer status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
        this.timestamp = new Date();
    }
    
    protected AbstractResult(ResponseStatus ResponseStatus, T data) {
        this.status = ResponseStatus.status();
        this.message = ResponseStatus.message();
        this.data = data;
        this.timestamp = new Date();
    }
    
    @ApiModelProperty(value = "调用结果是否成功")
    public boolean isSuccess() {
        return ResponseStatus.SUCCESS.status() == status;
    }
    
    public AbstractResult<T> errorCode(Integer errorCode) {
        this.setErrorCode(errorCode);
        return this;
    }
    
}


