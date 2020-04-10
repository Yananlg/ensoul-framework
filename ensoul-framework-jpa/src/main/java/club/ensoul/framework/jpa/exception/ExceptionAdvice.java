package club.ensoul.framework.jpa.exception;

import club.ensoul.framework.core.response.AbstractResult;
import club.ensoul.framework.core.response.ResponseStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 统一异常返回数据处理
 *
 * @author Ensoul
 */
@Slf4j
@ControllerAdvice
public class ExceptionAdvice {
    
    /**
     * 参数绑定失败
     *
     * @param request 请求
     * @param e       异常
     */
    @ResponseBody
    @ExceptionHandler({WebExchangeBindException.class})
    public AbstractResult<?> webExchangeBindExceptionHandler(HttpServletRequest request, WebExchangeBindException e) {
        log.error("exception advice: ", e);
        return getResponseResult(e.getFieldErrors());
    }
    
    /**
     * 参数绑定失败
     *
     * @param request 请求
     * @param e       异常
     */
    @ResponseBody
    @ExceptionHandler({BindException.class})
    public AbstractResult<?> bindExceptionHandler(HttpServletRequest request, BindException e) {
        log.error("exception advice: ", e);
        return getResponseResult(e.getFieldErrors());
    }
    
    private AbstractResult<?> getResponseResult(List<FieldError> fieldErrors) {
        // 格式化以提供友好的错误提示
        String message = String.format("参数错误：%s",
                fieldErrors.stream()
                        .map(FieldError::getDefaultMessage)
                        .collect(Collectors.joining(";")));
        return AbstractResult.failure(ResponseStatus.PARAMETER_INVALID.status(), message);
    }
    
    /**
     * 参数校验失败
     *
     * @param request 请求
     * @param e       异常
     */
    @ResponseBody
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public AbstractResult<?> methodArgumentNotValidExceptionHandler(HttpServletRequest request, MethodArgumentNotValidException e) {
        log.error("exception advice: ", e);
        BindingResult bindingResult = e.getBindingResult();
        List<ObjectError> fieldErrors = bindingResult.getAllErrors();
        
        // 格式化以提供友好的错误提示
        String message = String.format("参数错误：%s",
                fieldErrors.stream()
                        .map(ObjectError::getDefaultMessage)
                        .collect(Collectors.joining(";")));
        
        return AbstractResult.failure(ResponseStatus.PARAMETER_INVALID.status(), message);
    }
    
    /**
     * 参数校验失败
     *
     * @param request 请求
     * @param e       异常
     */
    @ResponseBody
    @ExceptionHandler({ConstraintViolationException.class})
    public AbstractResult<?> constraintViolationExceptionHandler(HttpServletRequest request, ConstraintViolationException e) {
        log.error("exception advice: ", e);
        Set<ConstraintViolation<?>> constraintViolations = e.getConstraintViolations();
        // 格式化以提供友好的错误提示
        String message = String.format("参数错误：%s",
                constraintViolations.stream()
                        .map(ConstraintViolation::getMessage)
                        .collect(Collectors.joining(";")));
        return AbstractResult.failure(ResponseStatus.PARAMETER_INVALID.status(), message);
    }
    
    /**
     * 参数校验失败, 参数类型错误
     *
     * @param request 请求
     * @param e       异常
     */
    @ResponseBody
    @ExceptionHandler({MethodArgumentTypeMismatchException.class})
    public AbstractResult<?> mehodArgumentTypeMismatchExceptionHandler(HttpServletRequest request, MethodArgumentTypeMismatchException e) {
        log.error("exception advice: ", e);
        String name = e.getName();
        String message = String.format("参数错误：参数类型错误[%s]", name);
        return AbstractResult.failure(ResponseStatus.PARAMETER_INVALID.status(), message);
    }
    
    /**
     * 参数校验失败, 参数类型错误
     *
     * @param request 请求
     * @param e       异常
     */
    @ResponseBody
    @ExceptionHandler({IllegalArgumentException.class})
    public AbstractResult<?> illegalArgumentException(HttpServletRequest request, IllegalArgumentException e) {
        log.error("exception advice: ", e);
        String name = e.getMessage();
        String message = String.format("参数错误：参数[%s]错误", name);
        return AbstractResult.failure(ResponseStatus.PARAMETER_INVALID.status(), message);
    }
    
    
    /**
     * 参数校验失败, 参数类型错误
     *
     * @param request 请求
     * @param e       异常
     */
    @ResponseBody
    @ExceptionHandler({MissingServletRequestParameterException.class})
    public AbstractResult<?> mehodArgumentTypeMismatchExceptionHandler(HttpServletRequest request, MissingServletRequestParameterException e) {
        log.error("exception advice: ", e);
        String name = e.getParameterName();
        String message = String.format("参数错误：参数[%s]不能为空", name);
        return AbstractResult.failure(ResponseStatus.PARAMETER_INVALID.status(), message);
    }
    
    /**
     * 参数校验失败, 缺少参数
     *
     * @param request 请求
     * @param e       异常
     */
    @ResponseBody
    @ExceptionHandler({MissingServletRequestPartException.class})
    public AbstractResult<?> missingServletRequestPartExceptionHandler(HttpServletRequest request, MissingServletRequestPartException e) {
        log.error("exception advice: ", e);
        String name = e.getRequestPartName();
        String message = String.format("参数错误：参数[%s]不能为空", name);
        return AbstractResult.failure(ResponseStatus.PARAMETER_INVALID.status(), message);
    }
    
    /**
     * 请求类型错误
     *
     * @param request 请求
     * @param e       异常
     */
    @ResponseBody
    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
    public AbstractResult<?> httpRequestMethodNotSupportedExceptionHandler(HttpServletRequest request, HttpRequestMethodNotSupportedException e) {
        log.error("exception advice: ", e);
        String method = e.getMethod();
        String[] supportedMethods = e.getSupportedMethods();
        String message = String.format("不支持[%s]请求，方法支持请求：%s", method, Arrays.toString(supportedMethods));
        
        return AbstractResult.failure(ResponseStatus.UNSUPPORTED_MEDIA_TYPE.status(), message);
    }
    
    /** 未知异常 */
    @ResponseBody
    @ExceptionHandler(AuthorizedException.class)
    public AbstractResult<?> authorizedHandler(HttpServletRequest request, AuthorizedException e) {
        return AbstractResult.failurem(e.getMessage());
    }
    
    /** 未知异常 */
    @ResponseBody
    @ExceptionHandler(Throwable.class)
    public AbstractResult<?> exceptionHandler(HttpServletRequest request, Throwable e) {
        log.error("exception advice: ", e);
        return AbstractResult.failurem(e.getMessage());
    }
    
}