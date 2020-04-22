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
    
    enum MatchMode {
        EXACT {
            @Override
            public String toMatchString(String pattern) {
                return pattern;
            }
        },
        
        START {
            @Override
            public String toMatchString(String pattern) {
                return pattern + '%';
            }
        },
        
        END {
            @Override
            public String toMatchString(String pattern) {
                return '%' + pattern;
            }
        },
        
        ANYWHERE {
            @Override
            public String toMatchString(String pattern) {
                return '%' + pattern + '%';
            }
        };
        
        public abstract String toMatchString(String pattern);
    }
    
}


