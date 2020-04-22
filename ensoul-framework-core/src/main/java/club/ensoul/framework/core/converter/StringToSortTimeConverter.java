package club.ensoul.framework.core.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;

public class StringToSortTimeConverter implements Converter<String, SortTime> {
    
    @Nullable
    @Override
    public SortTime convert(String str) {
        return SortTime.valueOf(str);
    }
    
}
