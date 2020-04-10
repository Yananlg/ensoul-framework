package club.ensoul.framework.mybatis.core.service;

import club.ensoul.framework.core.domain.IModel;

import java.util.List;

/**
 * 通用Service方法，添加操作
 *
 * @param <T> 数据库model模型
 * @author wy_peng_chen6
 */
public interface InsertService<T extends IModel, PK> {
    
    /**
     * 保存一个实体，null的属性也会保存，不会使用数据库默认值
     *
     * @param record
     * @return
     */
    int insert(T record);
    
    /**
     * 保存一个实体，null的属性也会保存，不会使用数据库默认值
     *
     * @param record
     * @return
     */
    T insertAndResult(T record);
    
    /**
     * 保存一个实体，null的属性不会保存，会使用数据库默认值
     *
     * @param record
     * @return
     */
    int insertSelective(T record);
    
    /**
     * 保存一个实体，null的属性不会保存，会使用数据库默认值
     *
     * @param record
     * @return
     */
    T insertSelectiveAndResult(T record);
    
    /**
     * 批量插入，支持批量插入的数据库可以使用，例如MySQL,H2等，另外该接口限制实体包含`id`属性并且必须为自增列
     *
     * @param recordList
     * @return
     */
    int insertList(List<? extends T> recordList);
    
}
