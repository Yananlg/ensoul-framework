package club.ensoul.framework.core.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public interface DescribeEnum {
    
    String describe();
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    class Item {
        private String name;
        private String describe;
    }
    
}

