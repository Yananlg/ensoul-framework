package club.ensoul.framework.jpa.core;

import club.ensoul.framework.core.domain.IEntity;
import club.ensoul.framework.core.domain.IQuery;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.persistence.Id;
import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

/**
 * 抽象类BaseService
 *
 * @author chpen
 */
public class BaseServiceImpl<T extends IEntity, ID extends Serializable> implements BaseService<T, ID> {
    
    @Autowired
    private BaseRepository<T, ID> baseRepository;
    
    private Class<T> tClass;
    
    public BaseServiceImpl() {
        Type tType = ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[1];
        this.tClass = (Class<T>) tType;
        initFieldsCache();
    }
    
    public BaseServiceImpl(Class<T> tClass) {
        this.tClass = tClass;
        initFieldsCache();
    }
    
    private static final ConcurrentHashMap<String, Field[]> fieldsMap = new ConcurrentHashMap<>();
    private static final ConcurrentHashMap<String, Field> idFieldMap = new ConcurrentHashMap<>();
    
    private void initFieldsCache() {
        Field[] fields = fieldsMap.get(tClass.getName());
        if(fields == null) {
            fields = tClass.getDeclaredFields();
            fieldsMap.put(tClass.getName(), fields);
        }
        initField(fields, Id.class, idFieldMap);
    }
    
    private void initField(Field[] fields, Class<? extends Annotation> annotationClass, ConcurrentHashMap<String, Field> fieldMap) {
        Field field = fieldMap.get(tClass.getName());
        if(field == null) {
            for(Field _field : fields) {
                if(_field.isAnnotationPresent(annotationClass)) {
                    field = _field;
                    field.setAccessible(true);
                    fieldMap.put(tClass.getName(), field);
                    break;
                }
            }
        }
    }
    
    //------------------------------------------------------------------------------------------------
    
    /**
     * 根据主键判断数据是否存在
     *
     * @param id {@link ID} 主键id
     * @return 存在返回 {@code true}，否则返回 {@code false}
     */
    @Override
    public boolean exists(ID id) {
        return baseRepository.existsById(id);
    }
    
    /**
     * 根据{@link Example}判断数据是否存在
     *
     * @param example {@link Example} 主键id
     * @return 存在返回 {@code true}，否则返回 {@code false}
     */
    @Override
    public boolean exists(Example<T> example) {
        return baseRepository.exists(example);
    }
    
    //------------------------------------------------------------------------------------------------
    
    /**
     * 根据主键查询数据
     *
     * @param id 主键
     * @return 查询的对象视图模型，未查询到返回false
     */
    @Override
    public Optional<T> findById(ID id) {
        return baseRepository.findById(id);
    }
    
    /**
     * 根据主键查询数据
     *
     * @param id 主键
     * @return 查询的对象视图模型，未查询到返回false
     */
    @Override
    public <R> Optional<R> findById(ID id, Function<? super T, R> mapper) {
        Optional<T> optional = baseRepository.findById(id);
        return optional.map(mapper);
    }
    
    /**
     * 简单查询功能
     *
     * @return {@link Page}
     */
    @Override
    public List<T> findAll() {
        return baseRepository.findAll();
    }
    
    /**
     * 简单查询功能
     *
     * @return {@link Page}
     */
    @Override
    public Page<T> findAll(Pageable pageable) {
        return baseRepository.findAll(pageable);
    }
    
    @Override
    public void save(T entity) {
        baseRepository.saveAndFlush(entity);
    }
    
    @SafeVarargs
    @Override
    public final void save(@NotNull T... entitys) {
        baseRepository.saveAll(Arrays.asList(entitys));
    }
    
    @Override
    public void save(Iterable<T> entitys) {
        baseRepository.saveAll(entitys);
    }
    
    @Override
    public <Q extends IQuery> void save(Q query) {
        T entity = query.copier(tClass);
        baseRepository.saveAndFlush(entity);
    }
    
    @Override
    public <Q extends IQuery> void save(Q query, Function<Q, T> mapper) {
        T entity = mapper.apply(query);
        baseRepository.saveAndFlush(entity);
    }
    
    @Override
    public <Q extends IQuery> void save(ID id, Q query) {
        T entity = query.copier(tClass);
        setEntityId(id, entity);
        baseRepository.saveAndFlush(entity);
    }
    
    @Override
    public <Q extends IQuery> void save(ID id, Q query, Function<Q, ? extends T> mapper) {
        T entity = mapper.apply(query);
        setEntityId(id, entity);
        baseRepository.saveAndFlush(entity);
    }
    
    @Override
    public void deleteById(ID id) {
        baseRepository.deleteById(id);
    }
    
    @Override
    public void delete(T entity) {
        baseRepository.delete(entity);
    }
    
    @SafeVarargs
    @Override
    public final void deleteById(ID... ids) {
        List<T> ts = baseRepository.findAllById(Arrays.asList(ids));
        baseRepository.deleteInBatch(ts);
    }
    
    @Override
    public void deleteById(Iterable<ID> ids) {
        List<T> ts = baseRepository.findAllById(ids);
        baseRepository.deleteInBatch(ts);
    }
    
    @SafeVarargs
    @Override
    public final void deleteByEntity(T... ts) {
        baseRepository.deleteInBatch(Arrays.asList(ts));
    }
    
    @Override
    public void deleteByEntity(Iterable<T> ts) {
        baseRepository.deleteInBatch(ts);
    }
    
    @Override
    public void deleteAll() {
        baseRepository.deleteAll();
    }
    
    @Override
    public void deleteAllInBatch() {
        baseRepository.deleteAllInBatch();
    }
    
    private void setEntityId(ID id, T entity) {
        try {
            idFieldMap.get(tClass.getName()).set(entity, id);
        } catch(IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
    
    
}
