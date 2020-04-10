package club.ensoul.framework.shiro.filter;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;
import org.springframework.stereotype.Component;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.util.stream.Stream;

@Component("anyRoles")
public class AnyRolesAuthorizationFilter extends AuthorizationFilter {
    
    @Override
    protected boolean isAccessAllowed(ServletRequest req, ServletResponse resp, Object mappedValue) {
        Subject subject = getSubject(req, resp);
        String[] rolesArray = (String[]) mappedValue;
    
        //没有角色限制，有权限访问
        if(rolesArray == null || rolesArray.length == 0) {
            return true;
        }

        return Stream.of(rolesArray).anyMatch(subject::hasRole);
    }
    
}