package club.ensoul.framework.core.copier;

import club.ensoul.framework.core.copier.cache.CopierHelper;
import club.ensoul.framework.core.copier.converter.DefaultConverter;
import club.ensoul.framework.core.copier.exception.CopierException;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.cglib.core.Converter;

public class SimpleCopier implements ICopier {
    
    /**
     * 拷贝属性
     *
     * @param source 源类型实例
     * @param classz 拷贝的目标类型
     * @return 拷贝的目标类型实例
     * @throws CopierException 属性访问异常
     */
    @Override
    public <T> T copier(Object source, Class<T> classz) {
        try {
            T target = classz.newInstance();
            copier(source, target, new DefaultConverter());
            return target;
        } catch(InstantiationException | IllegalAccessException e) {
            throw new CopierException(e);
        }
    }
    
    /**
     * 拷贝属性
     *
     * @param source    源类型实例
     * @param classz    拷贝的目标类型
     * @param converter 忽略的属性
     * @return 拷贝的目标类型实例
     * @throws CopierException 属性访问异常
     */
    @Override
    public <T> T copier(Object source, Class<T> classz, Converter converter) {
        try {
            T target = classz.newInstance();
            copier(source, target, converter);
            return target;
        } catch(InstantiationException | IllegalAccessException e) {
            throw new CopierException(e);
        }
    }
    
    /**
     * 拷贝属性
     *
     * @param source 源类型实例
     * @param target 拷贝的目标类型
     */
    @Override
    public void copier(Object source, Object target) {
        final BeanCopier copier = CopierHelper.getBeanCopier(source.getClass(), target.getClass());
        copier.copy(source, target, new DefaultConverter());
    }
    
    /**
     * 拷贝属性
     *
     * @param source    源实例
     * @param target    目标实例
     * @param converter 忽略的属性
     */
    @Override
    public void copier(Object source, Object target, Converter converter) {
        final BeanCopier copier = CopierHelper.getBeanCopier(source.getClass(), target.getClass());
        copier.copy(source, target, converter);
    }
    
}
