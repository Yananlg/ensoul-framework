package club.ensoul.framework.core.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorMessage {
    
    private Integer errorCode;
    private String messageKey;
    private Throwable[] throwable;
    
}
