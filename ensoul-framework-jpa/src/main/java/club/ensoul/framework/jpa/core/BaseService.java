package club.ensoul.framework.jpa.core;

import club.ensoul.framework.core.domain.IEntity;
import club.ensoul.framework.core.domain.IQuery;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

/**
 * 通用Service接口
 *
 * @author chpen
 */
public interface BaseService<T extends IEntity, ID extends Serializable> {
    
    boolean exists(ID id);
    
    boolean exists(Example<T> example);
    
    Optional<T> findById(ID id);
    
    <R> Optional<R> findById(ID id, Function<? super T, R> mapper);
    
    List<T> findAll();
    
    Page<T> findAll(Pageable pageable);
    
    void save(T entity);
    
    void save(T... entitys);
    
    void save(Iterable<T> entitys);
    
    <Q extends IQuery> void save(Q query);
    
    <Q extends IQuery> void save(Q query, Function<Q, T> mapper);
    
    <Q extends IQuery> void save(ID id, Q query);
    
    <Q extends IQuery> void save(ID id, Q query, Function<Q, ? extends T> mapper);
    
    void deleteById(ID... ids);
    
    void deleteById(Iterable<ID> ids);
    
    void deleteByEntity(T... ts);
    
    void deleteByEntity(Iterable<T> ts);
    
    void deleteAll();
    
    void deleteById(ID id);
    
    void delete(T entity);
    
    void deleteAllInBatch();
    
}
