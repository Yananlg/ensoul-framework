package club.ensoul.framework.core.copier;

import club.ensoul.framework.core.copier.AbstractResultCopier.Messages.Success;
import club.ensoul.framework.core.domain.IEntity;
import club.ensoul.framework.core.domain.IView;
import club.ensoul.framework.core.response.AbstractResult;
import org.springframework.lang.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public abstract class AbstractResultCopier {
    
    protected AbstractResult<?> result() {
        return AbstractResult.success(Success.SUCCESS.mseeage);
    }
    
    protected AbstractResult<?> sResult(String succMsg) {
        return AbstractResult.failurem(succMsg);
    }
    
    protected AbstractResult<?> fResult(String failMsg) {
        return AbstractResult.failurem(failMsg);
    }
    
    protected AbstractResult<?> sResult(Messages.Success success) {
        return AbstractResult.successm(success.mseeage);
    }
    
    protected AbstractResult<?> fResult(Messages.Failure failure) {
        return AbstractResult.successm(failure.mseeage);
    }
    
    protected <T> AbstractResult<T> result(T entity) {
        if(entity == null) {
            return AbstractResult.successm(Success.UNDATA.mseeage);
        }
        return AbstractResult.success(entity);
    }
    
    protected <T extends IEntity, V extends IView> AbstractResult<V> result(@NonNull Optional<T> optional, Function<? super T, V> converter) {
        if(optional.isPresent()) {
            V v = converter.apply(optional.get());
            return result(v);
        }
        return AbstractResult.successm(Success.UNDATA.mseeage);
    }
    
    protected <T extends IEntity, V extends IView> AbstractResult<V> result(@NonNull Optional<T> optional, String failMsg, Function<? super T, V> converter) {
        if(optional.isPresent()) {
            V v = converter.apply(optional.get());
            return result(v);
        }
        return AbstractResult.failurem(failMsg);
    }
    
    protected <T extends IEntity, V extends IView> AbstractResult<V> result(@NonNull Optional<T> optional, Class<V> vClass) {
        if(optional.isPresent()) {
            return result(optional.get().copier(vClass));
        }
        return AbstractResult.successm(Success.UNDATA.mseeage);
    }
    
    protected <T extends IEntity, V extends IView> AbstractResult<V> result(@NonNull Optional<T> optional, String failMsg, Class<V> vClass) {
        if(optional.isPresent()) {
            return result(optional.get().copier(vClass));
        }
        return AbstractResult.failurem(failMsg);
    }
    
    protected <T extends IEntity, V extends IView> AbstractResult<List<V>> result(T[] ts, Function<? super T, V> converter) {
        if(ts == null) {
            return result(new ArrayList<>());
        }
        return result(Stream.of(ts), converter);
    }
    
    protected <T extends IEntity, V extends IView> AbstractResult<List<V>> result(Iterable<T> iterable, Function<? super T, V> converter) {
        if(iterable == null) {
            return result(new ArrayList<>());
        }
        Stream<T> stream = StreamSupport.stream(iterable.spliterator(), false);
        return result(stream, converter);
    }
    
    protected <T extends IEntity, V extends IView> AbstractResult<List<V>> result(Stream<T> stream, Function<? super T, V> converter) {
        if(stream == null) {
            return result(new ArrayList<>());
        }
        List<V> list = stream.map(converter).collect(Collectors.toList());
        return AbstractResult.success(list);
    }
    
    protected <T extends IEntity, V extends IView> AbstractResult<List<V>> result(T[] ts, Class<V> classz) {
        if(ts == null) {
            return result(new ArrayList<>());
        }
        return result(Stream.of(ts), classz);
    }
    
    protected <T extends IEntity, V extends IView> AbstractResult<List<V>> result(Iterable<T> iterable, Class<V> classz) {
        if(iterable == null) {
            return result(new ArrayList<>());
        }
        Stream<T> stream = StreamSupport.stream(iterable.spliterator(), false);
        return result(stream, classz);
    }
    
    protected <T extends IEntity, V extends IView> AbstractResult<List<V>> result(Stream<T> stream, Class<V> classz) {
        if(stream == null) {
            return result(new ArrayList<>());
        }
        List<V> list = stream.map(o -> o.copier(classz)).collect(Collectors.toList());
        return AbstractResult.success(list);
    }
    
    //------------------------------------------------------------------------------------------------------------------------
    
    public interface Messages {
        enum Success {
            
            SUCCESS("操作成功"),
            UNDATA("请求数据不存在"),
            
            SAVE("添加成功"),
            UPDATE("修改成功"),
            DELETE("删除成功"),
            LOCK("锁定成功"),
            UNLOCK("解锁成功"),
            
            ;
            
            private String mseeage;
            
            Success(String mseeage) {
                this.mseeage = mseeage;
            }
        }
        
        enum Failure {
            
            FAILURE("操作失败"),
            
            SAVE("添加失败"),
            SAVE_TYPE("添加失败，类型不存在"),
            UPDATE("修改失败"),
            UPDATE_TYPE("修改失败，类型不存在"),
            UPDATE_VALID("修改失败，该状态不允许此操作"),
            DELETE("删除失败"),
            LOCK("锁定失败"),
            UNLOCK("解锁失败"),
            
            UNUSER("用户不存在"),
            UNDATA("请求数据不存在"),
            UNLOGIN("请登录"),
            
            ;
            
            private String mseeage;
            
            Failure(String mseeage) {
                this.mseeage = mseeage;
            }
        }
        
    }
    
}
