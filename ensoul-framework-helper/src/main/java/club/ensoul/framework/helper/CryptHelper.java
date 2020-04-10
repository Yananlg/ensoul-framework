package club.ensoul.framework.helper;

import cn.hutool.crypto.SecureUtil;

public class CryptHelper {

    public static String cryptPwd(String account, String salt, String pwd) {
        return SecureUtil.md5(account + salt + pwd);
    }
    
}
