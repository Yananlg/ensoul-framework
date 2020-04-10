package club.ensoul.framework.mybatis.core.service;


import club.ensoul.framework.core.domain.IModel;

import java.util.List;

/**
 * 通用Service方法，删除操作
 *
 * @param <T> 数据库model模型
 * @author wy_peng_chen6
 */
public interface DeleteService<T extends IModel, PK> {
    
    /**
     * 根据字段进行删除，方法参数必须包含完整的主键属性
     *
     * @param record
     * @return
     */
    int delete(T record);
    
    /**
     * 根据主键字段进行删除，方法参数必须包含完整的主键属性
     *
     * @param key
     * @return
     */
    int deleteByPrimaryKey(Object key);
    
    /**
     * 根据主键字符串进行删除，类中只有存在一个带有@Id注解的字段
     *
     * @param idList
     * @return
     */
    int deleteByIdList(List<PK> idList);
    
}
