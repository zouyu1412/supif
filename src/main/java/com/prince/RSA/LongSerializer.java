package com.prince.RSA;

import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import com.alibaba.fastjson.serializer.SerializeWriter;
import com.alibaba.fastjson.serializer.SerializerFeature;

import java.io.IOException;

/**
 *  Long类型转字符串
 * @author zhangjq-f
 *
 */
public class LongSerializer implements ObjectSerializer {
	@Override
	public void write(JSONSerializer serializer, Object object, Object fieldName, java.lang.reflect.Type fieldType,
			int features) throws IOException {
		SerializeWriter out = serializer.getWriter();
		if (object == null) {
			if (out.isEnabled(SerializerFeature.WriteNullNumberAsZero)) {
				out.write('0');
			} else {
				out.writeNull();
			}
			return;
		}
		out.writeString(object.toString());
	}
}
