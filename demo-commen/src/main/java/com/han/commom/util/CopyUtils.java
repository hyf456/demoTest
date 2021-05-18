package com.han.commom.util;// package com.jd.jxpp.scm.opencity.utils;
//
// import org.springframework.cglib.beans.BeanCopier;
// import org.springframework.cglib.core.Converter;
//
// import java.util.HashSet;
// import java.util.Map;
// import java.util.Set;
// import java.util.concurrent.ConcurrentHashMap;
//
// /**
//  * @ClassName CalculateUtils
//  * @Description 对象转换 Utils
//  * @Author hanyunfei1
//  * @Date 2021/5/4 14:03
//  * @Version 1.0
//  **/
// @Deprecated
// public class CopyUtils {
//
// 	private static final Map<String, BeanCopier> COPIER_CACHE = new ConcurrentHashMap<>();
// 	private static final Map<String, Converter> CONVERTER_CACHE = new ConcurrentHashMap<>();
//
// 	/**
// 	 * 拷贝：目标类必须含有无参构造
// 	 * @param src
// 	 * @param tagClass
// 	 * @param ignoreProperties
// 	 * @param <T>
// 	 * @return
// 	 */
// 	public static <T> T copyProperties(Object src, Class<T> tagClass, String... ignoreProperties) {
// 		if (null == src) {
// 			return null;
// 		}
//
// 		try {
// 			T target = tagClass.newInstance();
// 			copyProperties(src, target, ignoreProperties);
// 			return target;
// 		} catch (Exception e) {
// 			throw new RuntimeException(e);
// 		}
// 	}
//
// 	/**
// 	 * 拷贝
// 	 * @param src
// 	 * @param tag
// 	 * @param ignoreProperties
// 	 */
// 	public static void copyProperties(Object src, Object tag, String... ignoreProperties) {
// 		if (null == src || null == tag) {
// 			return;
// 		}
//
// 		StringBuilder keyBuilder = new StringBuilder()
// 				.append(src.getClass().getName())
// 				.append(tag.getClass().getName())
// 				.append("_");
// 		if (null != ignoreProperties) {
// 			for (String ignoreProperty : ignoreProperties) {
// 				keyBuilder.append(ignoreProperty);
// 			}
// 		}
// 		String key = keyBuilder.toString();
// 		BeanCopier copier = COPIER_CACHE.computeIfAbsent(key, (k) -> {
// 			Converter converter = null;
// 			if (null != ignoreProperties && ignoreProperties.length > 0) {
// 				converter = new IgnorePropertiesConverter(ignoreProperties);
// 				CONVERTER_CACHE.put(k, converter);
// 			}
//
// 			try {
// 				return BeanCopier.create(src.getClass(), tag.getClass(), converter != null);
// 			} catch (Exception e) {
// 				throw new RuntimeException(e);
// 			}
// 		});
//
// 		copier.copy(src, tag, CONVERTER_CACHE.get(key));
// 	}
//
// 	/**
// 	 * 创建拷贝器，适合重复调用
// 	 * @param srcClass
// 	 * @param tagClass
// 	 * @param ignoreProperties 要忽略的属性
// 	 * @param <S>
// 	 * @param <T>
// 	 * @return
// 	 */
// 	public static <S, T> Copier<S, T> createCopier(Class<S> srcClass, Class<T> tagClass, String... ignoreProperties) {
// 		Converter converter = null;
// 		if (null != ignoreProperties && ignoreProperties.length > 0) {
// 			converter = new IgnorePropertiesConverter(ignoreProperties);
// 		}
//
// 		return new Copier<S, T>(srcClass, tagClass, converter);
// 	}
//
// 	public static class Copier<S, T> {
// 		private Class<S> srcClass;
// 		private Class<T> tagClass;
// 		private Converter converter;
// 		/**
// 		 * 拷贝
// 		 */
// 		private BeanCopier copier;
//
// 		/**
// 		 * 反向拷贝
// 		 */
// 		private BeanCopier reverseCopier;
//
// 		private Copier(Class<S> srcClass, Class<T> tagClass, Converter converter) {
// 			this.srcClass = srcClass;
// 			this.tagClass = tagClass;
// 			this.converter = converter;
// 			this.copier = BeanCopier.create(srcClass, tagClass, converter != null);
// 		}
//
// 		public void copy(S s, T t) {
// 			try {
// 				if (null != s && null != t) {
// 					copier.copy(s, t, converter);;
// 				}
// 			} catch (Exception e) {
// 				throw new RuntimeException(e);
// 			}
// 		}
//
// 		public void reverseCopy(T t, S s) {
// 			try {
// 				if (null != s && null != t) {
// 					copier.copy(t, s, converter);;
// 				}
// 			} catch (Exception e) {
// 				throw new RuntimeException(e);
// 			}
// 		}
//
// 		@Override
// 		public boolean equals(Object other) {
// 			if (!(other instanceof Copier)) {
// 				return false;
// 			}
// 			Copier c = (Copier)other;
// 			return this.srcClass.equals(c.srcClass) && this.tagClass.equals(c.tagClass);
// 		}
//
// 		@Override
// 		public int hashCode() {
// 			int result = 1;
// 			result = 59 * result + this.srcClass.hashCode();
// 			result = 59 * result + this.tagClass.hashCode();
// 			return result;
// 		}
// 	}
//
// 	public static class IgnorePropertiesConverter implements Converter {
// 		private Set<String> ignoreProperties;
//
// 		public IgnorePropertiesConverter(String[] ignoreProperties) {
// 			if (null != ignoreProperties && ignoreProperties.length > 0) {
// 				this.ignoreProperties = new HashSet<String>(ignoreProperties.length);
// 				for (String prop : ignoreProperties) {
// 					if (null != prop && !(prop = prop.trim()).isEmpty()) {
// 						if (prop.length() == 1) {
// 							this.ignoreProperties.add("set" + prop.toUpperCase());
// 						} else {
// 							char first = prop.charAt(0);
//
// 							String last = prop.substring(1, prop.length());
// 							this.ignoreProperties.add("set" + Character.toUpperCase(first) + last);
// 						}
// 					}
// 				}
// 			}
// 		}
//
// 		@Override
// 		public Object convert(Object srcPropValue, Class targetClass, Object setterMethodName) {
// 			if (ignoreProperties.contains(String.valueOf(setterMethodName))) {
// 				return null;
// 			} else {
// 				return srcPropValue;
// 			}
// 		}
// 	}
// }
