package com.han.serializer.annotation;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

/**
 * @Description Long 类型字段序列化时转为字符串，避免js丢失精度
 * 加在属性上面
 *  @JsonSerialize(using = LongJsonSerializer.class)
 *  @JsonDeserialize(using = LongJsonDeserializer.class)
 * @Date 2019/7/16 16:05
 * @Author hanyf
 */
public class LongJsonSerializer extends JsonSerializer<Long> {
	@Override
	public void serialize(Long value, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
		String text = (value == null ? null : String.valueOf(value));
		if (text != null) {
			jsonGenerator.writeString(text);
		}
	}
}
