package cn.org.rapid_framework.generator.util.typemapping;

import java.util.HashMap;
import java.util.Map;

import cn.org.rapid_framework.generator.util.StringHelper;

public class JavaPrimitiveTypeMapping {
	static Map<String, String> wraper2primitive = new HashMap();
	static Map<String, String> primitive2wraper = new HashMap();
	static {
		wraper2primitive.put("Byte", "byte");
		wraper2primitive.put("Short", "short");
		wraper2primitive.put("Integer", "int");
		wraper2primitive.put("Long", "long");
		wraper2primitive.put("Float", "float");
		wraper2primitive.put("Double", "double");
		wraper2primitive.put("Boolean", "boolean");
		wraper2primitive.put("Integer", "int");
		wraper2primitive.put("Character", "char");
		
		for(String key : wraper2primitive.keySet()) {
			primitive2wraper.put(wraper2primitive.get(key), key);
		}
	}
	
	public static String getPrimitiveType(String clazz) {
		String className = StringHelper.getExtension(clazz);
		String result = wraper2primitive.get(className);
		return result == null ? clazz : result;
	}

	public static String getWrapperType(String clazz) {
		String result = primitive2wraper.get(clazz);
		return result == null ? clazz : result;
	}
	
}
