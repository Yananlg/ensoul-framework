package club.ensoul.framework.jpa.copier;

import club.ensoul.framework.core.copier.AbstractResultCopier;
import club.ensoul.framework.core.domain.IEntity;
import club.ensoul.framework.core.domain.IView;
import club.ensoul.framework.core.response.AbstractResult;
import org.springframework.data.domain.Page;

import java.util.function.Function;

public class ResultCopier extends AbstractResultCopier {
    
    protected <T extends IEntity, V extends IView> AbstractResult<Page<V>> result(Page<T> page, Function<? super T, V> converter) {
        if(page == null) {
            return result(Page.empty());
        }
        Page<V> vpage = page.map(converter);
        return AbstractResult.success(vpage);
    }
    
    protected <T extends IEntity, V extends IView> AbstractResult<Page<V>> result(Page<T> page, Class<V> classz) {
        if(page == null) {
            return result(Page.empty());
        }
        Page<V> vpage = page.map(o -> o.copier(classz));
        return AbstractResult.success(vpage);
    }
    
}
