package club.ensoul.framework.shiro.authz;

import org.apache.shiro.authc.UsernamePasswordToken;

public class ShiroUserToken extends UsernamePasswordToken {

	private String loginType;
 
    public ShiroUserToken() {}
 
    public ShiroUserToken(final String username, final String password, final String loginType) {
        super(username, password);
        this.loginType = loginType;
    }
 
    public String getLoginType() {
        return loginType;
    }
 
    public void setLoginType(String loginType) {
        this.loginType = loginType;
    }
}
