package club.ensoul.framework.core.jackson;

import club.ensoul.framework.core.domain.KVEnum;
import club.ensoul.framework.core.domain.KVEnum.Item;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

/**
 * jackson {@link KVEnum} 枚举类型序列化
 */
public class JacksonEnumSerialzer extends JsonSerializer<KVEnum> {
    
    private final static JacksonEnumSerialzer serialzer = new JacksonEnumSerialzer();
    
    public static JacksonEnumSerialzer instance() {
        return serialzer;
    }
    
    @Override
    public void serialize(KVEnum KVEnum, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        Item item = new Item(KVEnum.toString(), KVEnum.key(), KVEnum.value());
        jsonGenerator.writeObject(item);
    }
    
}
