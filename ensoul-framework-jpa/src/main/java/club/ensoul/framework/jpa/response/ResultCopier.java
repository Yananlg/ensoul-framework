package club.ensoul.framework.jpa.response;

import club.ensoul.framework.core.domain.IEntity;
import club.ensoul.framework.core.domain.IView;
import org.springframework.lang.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public abstract class ResultCopier {
    
    protected Result<?> result() {
        return Result.success();
    }
    
    protected <T> Result<T> result(T entity) {
        return Result.success(entity);
    }
    
    protected <T extends IEntity, V extends IView> Result<V> result(@NonNull Optional<T> optional, Function<? super T, V> converter) {
        if(optional.isPresent()) {
            V v = converter.apply(optional.get());
            return result(v);
        }
        return Result.success(null);
    }
    
    protected <T extends IEntity, V extends IView> Result<V> result(@NonNull Optional<T> optional, String failMsg, Function<? super T, V> converter) {
        if(optional.isPresent()) {
            V v = converter.apply(optional.get());
            return result(v);
        }
        return Result.failurem(failMsg);
    }
    
    protected <T extends IEntity, V extends IView> Result<V> result(@NonNull Optional<T> optional, Class<V> vClass) {
        if(optional.isPresent()) {
            return result(optional.get().copier(vClass));
        }
        return Result.success(null);
    }
    
    protected <T extends IEntity, V extends IView> Result<V> result(@NonNull Optional<T> optional, String failMsg, Class<V> vClass) {
        if(optional.isPresent()) {
            return result(optional.get().copier(vClass));
        }
        return Result.failurem(failMsg);
    }
    
    protected <T extends IEntity, V extends IView> Result<List<V>> result(T[] ts, Function<? super T, V> converter) {
        if(ts == null) {
            return result(new ArrayList<>());
        }
        return result(Stream.of(ts), converter);
    }
    
    protected <T extends IEntity, V extends IView> Result<List<V>> result(Iterable<T> iterable, Function<? super T, V> converter) {
        if(iterable == null) {
            return result(new ArrayList<>());
        }
        Stream<T> stream = StreamSupport.stream(iterable.spliterator(), false);
        return result(stream, converter);
    }
    
    protected <T extends IEntity, V extends IView> Result<List<V>> result(Stream<T> stream, Function<? super T, V> converter) {
        if(stream == null) {
            return result(new ArrayList<>());
        }
        List<V> list = stream.map(converter).collect(Collectors.toList());
        return Result.success(list);
    }
    
    protected <T extends IEntity, V extends IView> Result<List<V>> result(T[] ts, Class<V> classz) {
        if(ts == null) {
            return result(new ArrayList<>());
        }
        return result(Stream.of(ts), classz);
    }
    
    protected <T extends IEntity, V extends IView> Result<List<V>> result(Iterable<T> iterable, Class<V> classz) {
        if(iterable == null) {
            return result(new ArrayList<>());
        }
        Stream<T> stream = StreamSupport.stream(iterable.spliterator(), false);
        return result(stream, classz);
    }
    
    protected <T extends IEntity, V extends IView> Result<List<V>> result(Stream<T> stream, Class<V> classz) {
        if(stream == null) {
            return result(new ArrayList<>());
        }
        List<V> list = stream.map(o -> o.copier(classz)).collect(Collectors.toList());
        return Result.success(list);
    }
    
}
