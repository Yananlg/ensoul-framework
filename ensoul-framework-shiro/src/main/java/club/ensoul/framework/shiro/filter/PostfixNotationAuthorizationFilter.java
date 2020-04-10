package club.ensoul.framework.shiro.filter;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;
import org.springframework.stereotype.Component;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;
import java.util.*;

@Component("ruleAnyPrems")
public class PostfixNotationAuthorizationFilter extends AuthorizationFilter {

    @Override
    public boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws IOException {

        Subject subject = getSubject(request, response);
        String[] perms = (String[]) mappedValue;

        boolean isPermitted = false;
        for (String perm : perms) {
            if (isPermitted(subject, perm)) {
                isPermitted = true;
                break;
            }
        }
        return isPermitted;
    }


    //支持的运算符和运算符优先级
    public static final Map<String, Integer> expMap = new HashMap<String, Integer>() {{
        put("not", 0);
        put("!", 0);

        put("and", 0);
        put("&&", 0);

        put("or", 0);
        put("||", 0);

        put("(", 1);
        put(")", 1);
    }};

    public static final Set<String> expList = expMap.keySet();

    public boolean isPermitted(Subject subject, String permission) {
        Stack<String> exp = getExp(expList, permission);
        if (exp.size() == 1) {
            return subject.isPermitted(exp.pop());
        }
        List<String> expTemp = new ArrayList<>();
        //将其中的权限字符串解析成true , false
        for (String temp : exp) {
            if (expList.contains(temp)) {
                expTemp.add(temp);
            } else {
                expTemp.add(Boolean.toString(subject.isPermitted(temp)));
            }
        }
        //计算逆波兰
        return computeRpn(expList, expTemp);
    }

    private static boolean computeRpn(Collection<String> expList, Collection<String> exp) {
        Stack<Boolean> stack = new Stack<>();
        for (String temp : exp) {
            if (expList.contains(temp)) {
                if ("!".equals(temp) || "not".equals(temp)) {
                    stack.push(!stack.pop());
                } else if ("and".equals(temp) || "&&".equals(temp)) {
                    Boolean s1 = stack.pop();
                    Boolean s2 = stack.pop();
                    stack.push(s1 && s2);
                } else {
                    Boolean s1 = stack.pop();
                    Boolean s2 = stack.pop();
                    stack.push(s1 || s2);
                }
            } else {
                stack.push(Boolean.parseBoolean(temp));
            }
        }
        if (stack.size() > 1) {
            throw new RuntimeException("compute error！ stack: " + exp.toString());
        } else {
            return stack.pop();
        }
    }

    //获得逆波兰表达式
    private static Stack<String> getExp(Collection<String> expList, String exp) {
        Stack<String> s1 = new Stack<>();
        Stack<String> s2 = new Stack<>();
        for (String str : exp.split(" ")) {
            str = str.trim();
            String strL = str.toLowerCase();
            if ("".equals(str)) {
                continue;
            }
            if ("(".equals(str)) {
                //左括号
                s1.push(str);
            } else if (")".equals(str)) {
                //右括号
                while (!s1.empty()) {
                    String temp = s1.pop();
                    if ("(".equals(temp)) {
                        break;
                    } else {
                        s2.push(temp);
                    }
                }
            } else if (expList.contains(strL)) {
                //操作符
                if (s1.empty()) {
                    s1.push(strL);
                } else {
                    String temp = s1.peek();
                    if ("(".equals(temp) || ")".equals(temp)) {
                        s1.push(strL);
                    } else if (expMap.get(strL) >= expMap.get(temp)) {
                        s1.push(strL);
                    } else {
                        s2.push(s1.pop());
                        s1.push(strL);
                    }
                }
            } else {
                //运算数
                s2.push(str);
            }
        }
        while (!s1.empty()) {
            s2.push(s1.pop());
        }
        return s2;
    }

}