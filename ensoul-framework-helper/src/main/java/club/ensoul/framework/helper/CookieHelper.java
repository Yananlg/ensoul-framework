package club.ensoul.framework.helper;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

public class CookieHelper {
    
    public static Optional<String> getCookieValue(HttpServletRequest request, String name) {
        String cookieValue = null;
        Optional<Cookie> optional = getCookie(request, name);
        if(optional.isPresent()) {
            cookieValue = optional.get().getValue();
        }
        return Optional.ofNullable(cookieValue);
    }
    
    public static void removeCookie(HttpServletRequest request, HttpServletResponse response, String name) {
        Optional<Cookie> optional = getCookie(request, name);
        if(optional.isPresent()) {
            optional.get().setValue(null);
            optional.get().setMaxAge(0);//生命周期设置为0
            response.addCookie(optional.get());
        }
    }
    
    public static Optional<Cookie> getCookie(HttpServletRequest request, String name) {
        Cookie[] cookies = request.getCookies();
        Cookie cookie = null;
        if(cookies != null && cookies.length > 0) {
            for(Cookie _cookie : cookies) {
                if(name.equals(_cookie.getName())) {
                    cookie = _cookie;
                    break;
                }
            }
        }
        return Optional.ofNullable(cookie);
    }
    
}
