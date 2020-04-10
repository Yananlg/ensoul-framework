package club.ensoul.framework.jpa.core;

import club.ensoul.framework.core.copier.SimpleCopier;
import club.ensoul.framework.helper.DateHelper;
import club.ensoul.framework.jpa.copier.ResultCopier;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * web层通用数据处理
 *
 * @author chpen
 */
@Slf4j
@Component
public abstract class BaseController<U, ID> extends ResultCopier implements FetchUser<U, ID> {
    
    @Resource
    protected HttpServletRequest request;
    
    @Resource
    protected HttpServletResponse response;
    
    @Resource
    protected SimpleCopier simpleCopier;
    
    /**
     * 将前台传递过来的日期格式的字符串，自动转化为 {@link Date} 类型<br/>
     * 此方法主要处理 {@code application/x-www-form-urlencoded} 表单及 {@code multipart/form-data} 传递的日期类型
     *
     * @param binder WebDataBinder
     */
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat simpleDateFormat = DateHelper.typicalDateTimeFMT();
        simpleDateFormat.setLenient(true);
        // 格式话日期格式
        binder.registerCustomEditor(Date.class, new CustomDateEditor(simpleDateFormat, true));
        // 去除String参数两边空字符
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }
    
    // @Override
    // public U currentUser() {
    //     Optional<String> token = CookieHelper.getCookieValue(request, AUTHORIZATION);
    //     if(!token.isPresent()) {
    //         log.warn("base get current user error: token is null");
    //         throw new AuthorizedException("请登陆后再试");
    //     }
    //
    //     // 根据Token获取账号
    //     String userAccount = JWT.decode(token.get()).getAudience().get(0);
    //     if(StringUtils.isBlank(userAccount)) {
    //         log.warn("base get current user error: userAccount is null from token");
    //         throw new AuthorizedException("登陆信息过期，请重新登陆");
    //     }
    //
    //     Optional<SystemUserEntity> optional = systemUserService.findByAccount(userAccount);
    //     if(!optional.isPresent()) {
    //         log.warn("base get current user error: can not find userinfo");
    //         throw new AuthorizedException("登陆信息过期，请重新登陆");
    //     }
    //     return optional.get();
    // }
    
}
