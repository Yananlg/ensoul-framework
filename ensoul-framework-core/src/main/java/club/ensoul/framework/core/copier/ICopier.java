package club.ensoul.framework.core.copier;

import org.springframework.cglib.core.Converter;

public interface ICopier {
    
    <T> T copier(Object source, Class<T> classz);
    
    <T> T copier(Object source, Class<T> classz, Converter converter);
    
    void copier(Object source, Object target);
    
    void copier(Object source, Object target, Converter converter);
    
}


