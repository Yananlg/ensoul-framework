package club.ensoul.framework.core.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public interface MappedEnum {
    
    Integer key();
    
    String value();
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    class Item {
        private String name;
        private Integer key;
        private String value;
    }
    
}

