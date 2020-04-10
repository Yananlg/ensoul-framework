package club.ensoul.framework.jpa.core;

@FunctionalInterface
public interface FetchUser<U, ID> {
    
    /**
     * 获取当前登录用户接口
     *
     * @return 当前登录用户信息
     */
    U findCurrentUser(ID id);
    
}
