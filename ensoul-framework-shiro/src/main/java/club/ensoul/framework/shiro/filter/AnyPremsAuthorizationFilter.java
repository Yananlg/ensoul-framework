package club.ensoul.framework.shiro.filter;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;
import org.springframework.stereotype.Component;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;
import java.util.stream.Stream;

@Component("anyPrems")
public class AnyPremsAuthorizationFilter extends AuthorizationFilter {

    @Override
    public boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws IOException {

        Subject subject = getSubject(request, response);
        String[] perms = (String[]) mappedValue;

        boolean isPermitted = false;
        for (String perm : perms) {
            if (subject.isPermitted(perm)) {
                isPermitted = true;
                break;
            }
        }
        return isPermitted;
    }
    
}