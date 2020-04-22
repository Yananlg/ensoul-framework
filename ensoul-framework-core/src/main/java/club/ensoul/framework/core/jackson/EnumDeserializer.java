package club.ensoul.framework.core.jackson;

import club.ensoul.framework.core.domain.MappedEnum;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;

public class EnumDeserializer extends JsonDeserializer<MappedEnum> {
    
    private final static EnumDeserializer serialzer = new EnumDeserializer();
    
    public static EnumDeserializer instance() {
        return serialzer;
    }
    
    @Override
    public MappedEnum deserialize(JsonParser parser, DeserializationContext context) {
        return null;
    }
    
}
