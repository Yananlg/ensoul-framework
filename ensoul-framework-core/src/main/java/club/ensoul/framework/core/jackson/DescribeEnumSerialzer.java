package club.ensoul.framework.core.jackson;

import club.ensoul.framework.core.domain.DescribeEnum;
import club.ensoul.framework.core.domain.DescribeEnum.Item;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

/**
 * jackson {@link DescribeEnum} 枚举类型序列化
 */
public class DescribeEnumSerialzer extends JsonSerializer<DescribeEnum> {
    
    private final static DescribeEnumSerialzer serialzer = new DescribeEnumSerialzer();
    
    public static DescribeEnumSerialzer instance() {
        return serialzer;
    }
    
    @Override
    public void serialize(DescribeEnum describeEnum, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        Item item = new Item(describeEnum.toString(), describeEnum.describe());
        jsonGenerator.writeObject(item);
    }
    
}
