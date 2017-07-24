package com.cgeel.common.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.math.NumberUtils;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.io.StringWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by ZXW on 2014/11/12.
 */
public class StringUtils {

	public static String toCamelCase(String input) {
		StringBuilder sb = new StringBuilder();
		final char delim = '_';
		char value;
		boolean capitalize = false;
		for (int i = 0; i < input.length(); ++i) {
			value = input.charAt(i);
			if (value == delim) {
				capitalize = true;
			} else if (capitalize) {
				sb.append(Character.toUpperCase(value));
				capitalize = false;
			} else {
				sb.append(value);
			}
		}

		return sb.toString();
	}

	public static String toJson(Object obj) {
		ObjectMapper om = new ObjectMapper();
		StringWriter sw = new StringWriter();
		try {
			om.writeValue(sw, obj);
			String str = sw.toString();
			sw.close();
			return str;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static <T> T toObject(String data, Class<T> clazz) {
		ObjectMapper om = new ObjectMapper();
		try {
			return om.readValue(data, clazz);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static <T> T toObject(String data, TypeReference<T> tr) {
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			return objectMapper.readValue(data, tr);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String cutStr(String str, int limit) {
		if (str != null && str.length() > limit) {
			return str.substring(0, limit) + "…";
		} else {
			return str;
		}
	}

	public static List<Integer> StringToList(String str, String split) {
		if (str == null || "".equals(str)) {
			return null;
		} else {
			List<Integer> ids = new LinkedList<Integer>();
			if (str.contains(split)) {
				String[] strs = str.split(split);
				for (String st : strs) {
					if (NumberUtils.isNumber(st)) {
						ids.add(Integer.parseInt(st));
					}
				}
			} else {
				if (NumberUtils.isNumber(str)) {
					ids.add(Integer.parseInt(str));
				}
			}
			if (ids.size() > 0) {
				return ids;
			} else {
				return null;
			}

		}
	}

	/**
	 * @description SQL防注入验证
	 * @param title
	 * @return boolean 非注入SQL返回true
	 * @author don
	 * @time 2015年3月18日 下午1:44:40
	 */
	public static boolean isRightSQL(String title) {
		if (title != null) {
			title = title.toLowerCase();// 统一转为小写
			String badStr = "'|and|exec|execute|insert|select|delete|update|count|drop|*|%|chr|mid|master|truncate|"
					+ "char|declare|sitename|net user|xp_cmdshell|;|or|-|+|,|like'|and|exec|execute|insert|create|drop|"
					+ "table|from|grant|use|group_concat|column_name|"
					+ "information_schema.columns|table_schema|union|where|select|delete|update|order|by|count|*|"
					+ "chr|mid|master|truncate|char|declare|or|;|-|--|+|,|like|//|/|%|#";// 过滤掉的sql关键字，可以手动添加
			String[] badStrs = badStr.split("\\|");
			for (int i = 0; i < badStrs.length; i++) {
				if (title.indexOf(badStrs[i]) >= 0) {
					return false;
				}
			}

		}
		return true;
	}

	/**
	 * 将一个 JavaBean 对象转化为一个 Map
	 * 
	 * @param bean
	 *            要转化的JavaBean 对象
	 * @return 转化出来的 Map 对象
	 * @throws IntrospectionException
	 *             如果分析类属性失败
	 * @throws IllegalAccessException
	 *             如果实例化 JavaBean 失败
	 * @throws InvocationTargetException
	 *             如果调用属性的 setter 方法失败
	 */
	public static Map<String, Object> BeanToMap(Object bean) throws IntrospectionException,
			IllegalAccessException,
			InvocationTargetException {
		Class<? extends Object> type = bean.getClass();
		Map<String, Object> returnMap = new HashMap<String, Object>();
		BeanInfo beanInfo = Introspector.getBeanInfo(type);

		PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
		for (int i = 0; i < propertyDescriptors.length; i++) {
			PropertyDescriptor descriptor = propertyDescriptors[i];
			String propertyName = descriptor.getName();
			if (!propertyName.equals("class")) {
				Method readMethod = descriptor.getReadMethod();
				Object result = readMethod.invoke(bean, new Object[0]);
				if (result != null) {
					returnMap.put(propertyName, result);
				}
			}
		}
		return returnMap;
	}

}
