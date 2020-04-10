package club.ensoul.framework.shiro.credential;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.codec.Hex;
import org.apache.shiro.crypto.AesCipherService;
import org.apache.shiro.util.StringUtils;

import java.security.Key;

public class AESCredentialsMatcher extends SimpleCredentialsMatcher {

    private String hashAlgorithm = "AES";
    private int hashIterations;
    private boolean aesSalted;
    private boolean storedCredentialsHexEncoded;

    public AESCredentialsMatcher() {
        this.hashAlgorithm = null;
        this.aesSalted = false;
        this.hashIterations = 1;
        this.storedCredentialsHexEncoded = true; //false means Base64-encoded
    }

    public AESCredentialsMatcher(String hashAlgorithmName) {
        this();
        if (!StringUtils.hasText(hashAlgorithmName)) {
            throw new IllegalArgumentException("hashAlgorithmName cannot be null or empty.");
        }
        this.hashAlgorithm = hashAlgorithmName;
    }

    @Override
    public boolean doCredentialsMatch(AuthenticationToken authcToken, AuthenticationInfo info) {
        UsernamePasswordToken token = (UsernamePasswordToken) authcToken;

        Object tokenCredentials = getCredentials(token);
        Object accountCredentials = getCredentials(info);
        return equals(tokenCredentials, accountCredentials);
    }

    /**
     * 将传进来密码加密方法
     *
     * @param data 加密数据
     * @return 加密后的字符串
     */
    private Object encrypt(String data) {
        AesCipherService aesCipherService = new AesCipherService();
        aesCipherService.setKeySize(128); //设置key长度
        Key key = aesCipherService.generateNewKey();
        return aesCipherService.encrypt(data.getBytes(), key.getEncoded());
    }

    public boolean isStoredCredentialsHexEncoded() {
        return storedCredentialsHexEncoded;
    }


    public void setStoredCredentialsHexEncoded(boolean storedCredentialsHexEncoded) {
        this.storedCredentialsHexEncoded = storedCredentialsHexEncoded;
    }

    @Deprecated
    public boolean isAesSalted() {
        return aesSalted;
    }

    @Deprecated
    public void setAesSalted(boolean aesSalted) {
        this.aesSalted = aesSalted;
    }


    public int getHashIterations() {
        return hashIterations;
    }

    public void setHashIterations(int hashIterations) {
        if (hashIterations < 1) {
            this.hashIterations = 1;
        } else {
            this.hashIterations = hashIterations;
        }
    }


    @Deprecated
    protected Object getSalt(AuthenticationToken token) {
        return token.getPrincipal();
    }

    protected Object getCredentials(AuthenticationToken token) {

        Object credentials = token.getCredentials();
        Object salt = token.getPrincipal();
        byte[] storedBytes = toBytes(credentials);
        if (credentials instanceof String || credentials instanceof char[]) {
            if (isStoredCredentialsHexEncoded()) {
                storedBytes = Hex.decode(storedBytes);
            } else {
                storedBytes = Base64.decode(storedBytes);
            }
        }

        AesCipherService aesCipherService = new AesCipherService();
        aesCipherService.setKeySize(128); //设置key长度
        Key key = aesCipherService.generateNewKey();
        return aesCipherService.encrypt(storedBytes, key.getEncoded());
    }

    @Override
    protected Object getCredentials(AuthenticationInfo info) {
        return info.getCredentials();
    }

}
