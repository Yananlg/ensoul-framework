package club.ensoul.framework.core.copier.converter;


import lombok.extern.slf4j.Slf4j;
import org.springframework.cglib.core.Converter;

@Slf4j
public class DefaultConverter implements Converter {
    
    /**
     * @param value      源对象属性
     * @param tClass     目标对象属性类
     * @param setterName 目标对象setter方法名
     */
    @Override
    public Object convert(Object value, Class tClass, Object setterName) {
        
        if(value == null || tClass == Void.class) {
            log.debug("defalut converter value is null");
            return null;
        }
        
        Class<?> sClass = value.getClass();
        // 目标类型与原类型相同，直接返回
        if(tClass.isAssignableFrom(sClass)) {
            return value;
        }
        
        // 否则直接跳过
        return null;
    }
    
}