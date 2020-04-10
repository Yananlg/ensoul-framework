package club.ensoul.framework.mybatis.core;

import club.ensoul.framework.core.domain.IModel;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;

/**
 * 通用Service实现, filter 继承该接口, 可以实现简单的数据增删改查操作。
 * <br/>
 * 注意：{@link BaseMapper} 必须使用 {@link Autowired}注解, {@link Resource} 注解不支持泛型
 *
 * @param <T> 数据库表对应的Model类型, 必须为 {@link IModel} 子类
 * @author wy_peng_chen6
 * @see BaseService
 */
public abstract class AbstractBaseService<T extends IModel, PK> implements BaseService<T, PK> {
    
    @Autowired
    protected BaseMapper<T, PK> mapper;
    
    /**
     * 根据字段进行删除，方法参数必须包含完整的主键属性
     *
     * @param record 删除条件
     * @return int 更新条数
     */
    @Override
    public int delete(T record) {
        return mapper.delete(record);
    }
    
    /**
     * 根据主键字段进行删除，方法参数必须包含完整的主键属性
     *
     * @param key 主键值
     * @return int 更新条数
     */
    @Override
    public int deleteByPrimaryKey(Object key) {
        return mapper.deleteByPrimaryKey(key);
    }
    
    /**
     * 根据主键字符串进行删除，类中只有存在一个带有@Id注解的字段
     *
     * @param idList ids
     * @return int 更新条数
     */
    @Override
    public int deleteByIdList(List<PK> idList) {
        return mapper.deleteByIdList(idList);
    }
    
    /**
     * 保存一个实体，null的属性也会保存，不会使用数据库默认值
     *
     * @param record 插入数据
     * @return int 更新条数
     */
    @Override
    public int insert(T record) {
        return mapper.insert(record);
    }
    
    /**
     * 保存一个实体，null的属性也会保存，不会使用数据库默认值
     *
     * @param record 保存数据
     * @return T 保存后的数据
     */
    @Override
    public T insertAndResult(T record) {
        mapper.insert(record);
        return mapper.selectOne(record);
    }
    
    /**
     * 保存一个实体，null的属性不会保存，会使用数据库默认值
     *
     * @param record 保存数据
     * @return int 更新条数
     */
    @Override
    public int insertSelective(T record) {
        return mapper.insertSelective(record);
    }
    
    /**
     * 保存一个实体，null的属性不会保存，会使用数据库默认值
     *
     * @param record 保存数据
     * @return T 保存后的数据
     */
    @Override
    public T insertSelectiveAndResult(T record) {
        mapper.insertSelective(record);
        return mapper.selectOne(record);
    }
    
    /**
     * 批量插入，支持批量插入的数据库可以使用，例如MySQL,H2等，另外该接口限制实体包含`id`属性并且必须为自增列
     *
     * @param recordList 保存数据
     * @return 更新条数
     */
    @Override
    public int insertList(List<? extends T> recordList) {
        return mapper.insertList(recordList);
    }
    
    /**
     * 根据主键字段进行查询，方法参数必须包含完整的主键属性，查询条件使用等号
     *
     * @param key 主键值
     * @return T 查询数据
     */
    @Override
    public T selectByPrimaryKey(Object key) {
        return mapper.selectByPrimaryKey(key);
    }
    
    /**
     * 根据实体中的属性值进行查询，查询条件使用等号
     *
     * @param record 查询条件
     * @return List&lt;T&gt; 查询到的数据列表
     */
    @Override
    public List<T> select(T record) {
        return mapper.select(record);
    }
    
    /**
     * 根据实体中的属性进行查询，只能有一个返回值，有多个结果是抛出异常，查询条件使用等号
     *
     * @param record 查询条件
     * @return T 符合条件的数据
     */
    @Override
    public T selectOne(T record) {
        return mapper.selectOne(record);
    }
    
    /**
     * 查询全部结果
     *
     * @return List&lt;T&gt; 查询到的数据列表
     */
    @Override
    public List<T> selectAll() {
        return mapper.selectAll();
    }
    
    /**
     * 根据实体中的属性查询总数，查询条件使用等号
     *
     * @param record 查询条件
     * @return int 总条数
     */
    @Override
    public int selectCount(T record) {
        return mapper.selectCount(record);
    }
    
    /**
     * 根据主键更新实体全部字段，null值会被更新
     *
     * @param record 更新数据
     * @return int 更新条数
     */
    @Override
    public int updateByPrimaryKey(T record) {
        return mapper.updateByPrimaryKey(record);
    }
    
    /**
     * 根据主键更新属性不为null的值
     *
     * @param record 更新数据
     * @return int 更新条数
     */
    @Override
    public int updateByPrimaryKeySelective(T record) {
        return mapper.updateByPrimaryKeySelective(record);
    }
    
    /**
     * 根据Example条件更新实体`record`包含的不是null的属性值
     *
     * @param property 主键字段名
     * @param record   更新实体
     * @return int 更新条数
     */
    @Override
    public int batchUpdateByPrimaryKeySelective(String property, T record, Iterable<PK> values) {
        
        Example example = new Example(record.getClass());
        example.createCriteria().andIn(property, values);
        
        return mapper.updateByExampleSelective(record, example);
    }
    
    
}
