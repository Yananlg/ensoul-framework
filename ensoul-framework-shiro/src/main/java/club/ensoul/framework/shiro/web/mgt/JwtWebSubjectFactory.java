package club.ensoul.framework.shiro.web.mgt;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.subject.SubjectContext;
import org.apache.shiro.web.mgt.DefaultWebSubjectFactory;

public class JwtWebSubjectFactory extends DefaultWebSubjectFactory {

    @Override
    public Subject createSubject(SubjectContext context) {
        // 禁用Session会话
        context.setSessionCreationEnabled(false);
        return super.createSubject(context);
    }

}
