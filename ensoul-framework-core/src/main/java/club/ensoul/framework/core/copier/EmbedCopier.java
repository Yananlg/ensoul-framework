package club.ensoul.framework.core.copier;

import club.ensoul.framework.core.copier.cache.CopierHelper;
import org.springframework.cglib.core.Converter;

import java.util.function.Function;

public interface EmbedCopier {

    default  <R> R copier(Class<R> classz) {
        SimpleCopier simpleCopier = new SimpleCopier();
        return simpleCopier.copier(this, classz, CopierHelper.converter);
    }

    default <R> R copier(Class<R> classz, Converter converter) {
        SimpleCopier simpleCopier = new SimpleCopier();
        return simpleCopier.copier(this, classz, converter);
    }

    default <R> R copier(Function<? super EmbedCopier, ? extends R> converter) {
        return converter.apply(this);
    }

}


