package club.ensoul.framework.jpa.exception;

import club.ensoul.framework.core.response.AbstractResult;
import club.ensoul.framework.core.response.ResponseStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.ServletWebRequest;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@ApiIgnore
@Controller
public class InterfaceErrorController implements ErrorController {
    
    private static final String ERROR_PATH = "/error";
    private ErrorAttributes errorAttributes;
    
    @Override
    public String getErrorPath() {
        return ERROR_PATH;
    }
    
    @Autowired
    public InterfaceErrorController(ErrorAttributes errorAttributes) {
        this.errorAttributes = errorAttributes;
    }
    
    @ResponseBody
    @RequestMapping(value = ERROR_PATH)
    public AbstractResult<?> errorApiHander(HttpServletRequest request) {
        ServletWebRequest requestAttributes = new ServletWebRequest(request);
        Map<String, Object> attr = this.errorAttributes.getErrorAttributes(requestAttributes, false);
        String message = (String) attr.getOrDefault("message", ResponseStatus.FAILURE);
        return AbstractResult.failurem(message);
    }
    
}