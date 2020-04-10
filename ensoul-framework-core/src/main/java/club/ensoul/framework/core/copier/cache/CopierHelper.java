package club.ensoul.framework.core.copier.cache;

import club.ensoul.framework.core.copier.converter.SimpleConverter;
import club.ensoul.framework.core.domain.KVEnum;
import org.springframework.cglib.beans.BeanCopier;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CopierHelper {
    
    public static final SimpleConverter converter = new SimpleConverter();
    
    private static final Map<String, BeanCopier> beanCopierMap = new ConcurrentHashMap<>();
    private static final Map<String, KVEnum> beanEnumMap = new ConcurrentHashMap<>();
    
    /**
     * 获取缓存的 {@link BeanCopier} 实例
     *
     * @param sClass 源类型
     * @param tClass 目标类型
     * @return {@link BeanCopier}
     */
    public static BeanCopier getBeanCopier(Class<?> sClass, Class<?> tClass) {
        String beanKey = generateKey(sClass, tClass);
        if(!beanCopierMap.containsKey(beanKey)) {
            BeanCopier copier = BeanCopier.create(sClass, tClass, true);
            beanCopierMap.putIfAbsent(beanKey, copier);
            return copier;
        }
        return beanCopierMap.get(beanKey);
    }
    
    private static String generateKey(Class<?> sClass, Class<?> tClass) {
        return sClass.getName() + ":" + tClass.getName();
    }
    
}
