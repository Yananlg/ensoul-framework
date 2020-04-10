package club.ensoul.framework.mybatis.core.service;

import club.ensoul.framework.core.domain.IModel;

/**
 * 通用Service方法，更新操作
 *
 * @param <T> 数据库model模型
 * @author wy_peng_chen6
 */
public interface UpdateService<T extends IModel, PK> {
    
    /**
     * 根据主键更新实体全部字段，null值会被更新
     *
     * @param record
     * @return
     */
    int updateByPrimaryKey(T record);
    
    /**
     * 根据主键更新属性不为null的值
     *
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(T record);
    
    /**
     * 根据Example条件更新实体`record`包含的不是null的属性值
     *
     * @param property 主键字段名
     * @param record   更新实体
     * @return int 更新条数
     */
    int batchUpdateByPrimaryKeySelective(String property, T record, Iterable<PK> values);
    
}
