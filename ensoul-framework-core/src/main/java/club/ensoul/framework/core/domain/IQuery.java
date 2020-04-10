package club.ensoul.framework.core.domain;

import club.ensoul.framework.core.copier.EmbedCopier;

import java.io.Serializable;

/**
 * 请求模型基类
 *
 * @author Ensoul
 */
public interface IQuery extends EmbedCopier, Serializable {
    
    default String toMatchString(String pattern) {
        return MatchMode.ANYWHERE.toMatchString(pattern);
    }
    
    default String toMatchString(String str, MatchMode matchMode) {
        return matchMode.toMatchString(str);
    }
    
}


