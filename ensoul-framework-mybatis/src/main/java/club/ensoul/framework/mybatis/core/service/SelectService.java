package club.ensoul.framework.mybatis.core.service;


import club.ensoul.framework.core.domain.IModel;

import java.util.List;

/**
 * 通用Service方法，查询操作
 *
 * @param <T> 数据库model模型
 * @author wy_peng_chen6
 */
public interface SelectService<T extends IModel, PK> {
    
    /**
     * 根据主键字段进行查询，方法参数必须包含完整的主键属性，查询条件使用等号
     *
     * @param key
     * @return
     */
    T selectByPrimaryKey(Object key);
    
    /**
     * 根据实体中的属性值进行查询，查询条件使用等号
     *
     * @param record
     * @return
     */
    List<T> select(T record);
    
    
    /**
     * 根据实体中的属性进行查询，只能有一个返回值，有多个结果是抛出异常，查询条件使用等号
     *
     * @param record
     * @return
     */
    T selectOne(T record);
    
    
    /**
     * 查询全部结果
     *
     * @return
     */
    List<T> selectAll();
    
    
    /**
     * 根据实体中的属性查询总数，查询条件使用等号
     *
     * @param record
     * @return
     */
    int selectCount(T record);
    
}
