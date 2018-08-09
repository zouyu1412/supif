package com.prince.RSA;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;

import java.util.List;
import java.util.Map;

/**
 *
 * @author chaixg
 *
 */
public class JsonUtils {
	private static final SerializeConfig config;

	static {
		config = new SerializeConfig();
		config.put(Long.class, new LongSerializer()); //long 转字符串
	}

	private static final SerializerFeature[] features = { SerializerFeature.WriteMapNullValue, // 输出空置字段
			SerializerFeature.WriteNullListAsEmpty, // list字段如果为null，输出为[]，而不是null
			SerializerFeature.WriteNullBooleanAsFalse, // Boolean字段如果为null，输出为false，而不是null
			SerializerFeature.WriteNullStringAsEmpty // 字符类型字段如果为null，输出为""，而不是null
	};

	public static String toJSONString(Object object) {
		return JSON.toJSONString(object, config, features);
	}

	public static String toJSONNoFeatures(Object object) {
		return JSON.toJSONString(object, config);
	}

	public static Object toBean(String text) {
		return JSON.parse(text);
	}

	public static <T> T toBean(String text, Class<T> clazz) {
		return JSON.parseObject(text, clazz);
	}

	// 转换为数组
	public static <T> Object[] toArray(String text) {
		return toArray(text, null);
	}

	// 转换为数组
	public static <T> Object[] toArray(String text, Class<T> clazz) {
		return JSON.parseArray(text, clazz).toArray();
	}

	// 转换为List
	public static <T> List<T> toList(String text, Class<T> clazz) {
		return JSON.parseArray(text, clazz);
	}

	/**
	 * json字符串转化为map
	 *
	 * @param s
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static Map stringToMap(String s) {
		Map m = JSONObject.parseObject(s);
		return m;
	}

	/**
	 * 将map转化为string
	 *
	 * @param m
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static String mapToString(Map m) {
		String s = JSONObject.toJSONString(m);
		return s;
	}

	/**
	 * 解析alibabaFastJson
	 * @param jsonString
	 * @return
	 */
	static public JSONObject pareseFastJson(String jsonString){

		try {

			JSONObject jsonObject = JSONObject.parseObject(jsonString, Feature.IgnoreNotMatch);
			return jsonObject;
		}
		catch (Exception e) {
			return null;
		}
	}
}
