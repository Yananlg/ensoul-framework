package club.ensoul.framework.core.copier.converter;

import club.ensoul.framework.core.copier.annotation.EntityCopier;
import club.ensoul.framework.core.copier.annotation.EntityCopiers;
import club.ensoul.framework.core.copier.annotation.IgnoreCopier;
import club.ensoul.framework.core.copier.cache.CopierHelper;
import club.ensoul.framework.core.domain.MappedEnum;
import club.ensoul.framework.core.copier.exception.CopierException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.cglib.core.Converter;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
public class SimpleConverter implements Converter, Serializable {
    
    protected static final ThreadLocal<SimpleDateFormat> typicalDateTimeFMT = ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
    
    /**
     * @param sourceValue 源对象属性
     * @param targetClass 目标对象属性类
     * @param setterName  目标对象setter方法名
     */
    @Override
    public Object convert(Object sourceValue, Class targetClass, Object setterName) {
        if(sourceValue == null || targetClass == Void.class) {
            log.debug("defalut converter sourceValue is null");
            return null;
        }
        
        Object targetValue = sourceValue;
        Class<?> sourceClass = sourceValue.getClass();
        if(log.isDebugEnabled()) {
            log.debug("defalut converter sourceValue:{} sourceClass:{} targetClass:{}", sourceValue, sourceClass, targetClass);
        }
        
        // 目标类型与原类型相同，直接返回
        if(targetClass.isAssignableFrom(sourceClass)) {
            if(log.isDebugEnabled()) {
                log.debug("defalut converter sourceValue:{} sourceClass:{} targetClass:{}", sourceValue, sourceClass, targetClass);
            }
        } else if(Date.class.isAssignableFrom(sourceClass) && targetClass == String.class) {
            // 日期转String
            targetValue = typicalDateTimeFMT.get().format(sourceValue);
        } else if(!String.class.isAssignableFrom(sourceClass) && targetClass == String.class) {
            // 非日期转String。调用toString方法
            targetValue = String.valueOf(sourceValue);
        } else if(String.class.isAssignableFrom(sourceClass)) {
            // string转数值
            targetValue = str2Number(sourceValue, targetClass, targetValue);
        } else if(MappedEnum.class.isAssignableFrom(sourceClass) && targetClass == String.class) {
            // StateEnum枚举转换
            return ((MappedEnum) sourceValue).value();
        } else if(MappedEnum.class.isAssignableFrom(sourceClass) && (targetClass == Long.class || targetClass == Integer.class)) {
            return ((MappedEnum) sourceValue).key();
        } else if(MappedEnum.class.isAssignableFrom(targetClass) && sourceClass == String.class) {
            Object[] enumConstants = targetClass.getEnumConstants();
            for(Object enumConstant : enumConstants) {
                MappedEnum anEnum = (MappedEnum) enumConstant;
                if(sourceValue.equals(anEnum.value())) {
                    targetValue = anEnum;
                }
            }
        } else if(MappedEnum.class.isAssignableFrom(targetClass) && (sourceClass == Long.class || sourceClass == Integer.class)) {
            Object[] enumConstants = targetClass.getEnumConstants();
            for(Object enumConstant : enumConstants) {
                MappedEnum anEnum = (MappedEnum) enumConstant;
                if(sourceValue.equals(anEnum.key())) {
                    targetValue = anEnum;
                }
            }
        } else {
            try {
                targetValue = copierInternal(sourceValue, targetClass);
            } catch(IllegalAccessException | InstantiationException e) {
                log.error("copierInternal error: ", e);
                throw new CopierException(e);
            }
        }
        return targetValue;
    }
    
    private Object str2Number(Object sourceValue, Class targetClass, Object targetValue) {
        // 原类型为字符串，转到基本类型
        if(targetClass == Integer.class || targetClass == int.class) {
            targetValue =  Integer.parseInt(sourceValue.toString());
        }
        if(targetClass == Long.class || targetClass == long.class) {
            targetValue = Long.parseLong(sourceValue.toString());
        }
        if(targetClass == Float.class || targetClass == float.class) {
            targetValue = Float.parseFloat(sourceValue.toString());
        }
        if(targetClass == Double.class || targetClass == double.class) {
            targetValue = Double.parseDouble(sourceValue.toString());
        }
        if(targetClass == Short.class) {
            targetValue = Short.parseShort(sourceValue.toString());
        }
        return targetValue;
    }
    
    /**
     * 拷贝属性
     *
     * @param source 源类型实例
     * @param classz 拷贝的目标类型
     * @return 拷贝的目标类型实例
     * @throws CopierException 属性访问异常
     */
    private <T> T copierInternal(Object source, Class<T> classz) throws IllegalAccessException, InstantiationException {
        BeanCopier copier = CopierHelper.getBeanCopier(source.getClass(), classz);
        T target = classz.newInstance();
        copier.copy(source, target, new SimpleConverter());
        return target;
    }
    
    /**
     * 获取所有带忽略注解的属性
     *
     * @param classz 类型
     * @return {@link List <String>}
     */
    protected String[] getFields(Class classz) {
        Field[] fields = classz.getDeclaredFields();
        List<String> fieldsName = new ArrayList<>();
        for(Field field : fields) {
            if(field.isAnnotationPresent(IgnoreCopier.class)) {
                fieldsName.add(field.getName());
            }
        }
        return fieldsName.toArray(new String[0]);
    }
    
    protected Converter getConverter(Class<?> sClass, Class<?> tClass) throws IllegalAccessException, InstantiationException {
        if(sClass.isAnnotationPresent(EntityCopiers.class)) {
            EntityCopiers annotation = sClass.getAnnotation(EntityCopiers.class);
            EntityCopier[] entityCopiers = annotation.value();
            for(EntityCopier entityCopier : entityCopiers) {
                if(entityCopier.value() == tClass) {
                    return entityCopier.converter().newInstance();
                }
            }
        }
        if(sClass.isAnnotationPresent(EntityCopier.class)) {
            EntityCopier entityCopier = sClass.getAnnotation(EntityCopier.class);
            if(entityCopier.value() == tClass) {
                return entityCopier.converter().newInstance();
            }
        }
        return null;
    }
    
}