package com.han.serializer.annotation;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * @Description 将字符串转为Long
 * 加在属性上面
 *  @JsonSerialize(using = LongJsonSerializer.class)
 *  @JsonDeserialize(using = LongJsonDeserializer.class)
 * @Date 2019/7/16 16:06
 * @Author hanyf
 */
public class LongJsonDeserializer extends JsonDeserializer<Long> {
	private static final Logger logger = LoggerFactory.getLogger(LongJsonDeserializer.class);

	@Override
	public Long deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
		String value = jsonParser.getText();
		try {
			return value == null ? null : Long.parseLong(value);
		} catch (NumberFormatException e) {
			return null;
		}
	}
}
