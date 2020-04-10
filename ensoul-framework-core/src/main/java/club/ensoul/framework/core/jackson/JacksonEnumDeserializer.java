package club.ensoul.framework.core.jackson;

import club.ensoul.framework.core.domain.KVEnum;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;

public class JacksonEnumDeserializer extends JsonDeserializer<KVEnum> {
    
    private final static JacksonEnumDeserializer serialzer = new JacksonEnumDeserializer();
    
    public static JacksonEnumDeserializer instance() {
        return serialzer;
    }
    
    @Override
    public KVEnum deserialize(JsonParser parser, DeserializationContext context) throws IOException {
        return null;
    }
    
}
