package club.ensoul.framework.shiro.authz;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.realm.Realm;

import java.util.*;

public class ShiroModularRealmAuthenticator extends ModularRealmAuthenticator {

    private Map<String, List<Realm>> realmMap;

    public ShiroModularRealmAuthenticator(Map<String, List<Realm>> realmMap) {
        this.realmMap = realmMap;
    }

    @Override
    protected AuthenticationInfo doAuthenticate(AuthenticationToken authenticationToken) throws AuthenticationException {

        if(!(authenticationToken instanceof ShiroUserToken)) {
            return super.doAuthenticate(authenticationToken);
        }

        // 判断getRealms()是否返回为空
        assertRealmsConfigured();

        // 强制转换回自定义的CustomizedToken
        ShiroUserToken shiroUserToken = (ShiroUserToken) authenticationToken;
        // 登录类型
        String loginType = shiroUserToken.getLoginType();

        // 所有Realm
        Collection<Realm> realms = getRealms();

        // 登录类型对应的所有Realm
        List<Realm> typeRealms = realmMap.get(loginType);

        // 判断是单Realm还是多Realm
        if (typeRealms.size() == 1){
            return doSingleRealmAuthentication(typeRealms.get(0), shiroUserToken);
        }
        else{
            return doMultiRealmAuthentication(typeRealms, shiroUserToken);
        }
    }

}
