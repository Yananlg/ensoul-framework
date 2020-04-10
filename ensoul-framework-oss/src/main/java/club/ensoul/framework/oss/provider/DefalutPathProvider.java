package club.ensoul.framework.oss.provider;

import java.util.UUID;

public class DefalutPathProvider implements PathProvider {

    @Override
    public String path(Object... objs) {
        return UUID.randomUUID().toString();
    }
    
}
