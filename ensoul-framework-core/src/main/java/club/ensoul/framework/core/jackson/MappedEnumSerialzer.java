package club.ensoul.framework.core.jackson;

import club.ensoul.framework.core.domain.MappedEnum;
import club.ensoul.framework.core.domain.MappedEnum.Item;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

/**
 * jackson {@link MappedEnum} 枚举类型序列化
 */
public class MappedEnumSerialzer extends JsonSerializer<MappedEnum> {
    
    private final static MappedEnumSerialzer serialzer = new MappedEnumSerialzer();
    
    public static MappedEnumSerialzer instance() {
        return serialzer;
    }
    
    @Override
    public void serialize(MappedEnum mappedEnum, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        Item item = new Item(mappedEnum.toString(), mappedEnum.key(), mappedEnum.value());
        jsonGenerator.writeObject(item);
    }
    
}
