package com.han.commom.util;

import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @Description 数值转换
 * @Date 2019/7/30 10:33
 * @Author hanyf
 */
public class NumberUtil {

	/**
	 * @Author hanyf
	 * @Description 对象为空返回指定值
	 * @Date 10:33 2019/7/30
	 * @Param in 传入的对象
	 * @Param intForNull in 为空的返回值
	 * @return int
	 **/
	public static int getInt(Integer in, int intForNull) {
		if (in == null)
			return intForNull;
		else
			return in.intValue();
	}

	/**
	 * @Author hanyf
	 * @Description Integer 为空返回 0
	 * @Date 10:34 2019/7/30
	 * @Param [in]
	 * @return int
	 **/
	public static int getInt(Integer in) {
		return getInt(in, 0);
	}

	/**
	 * @Author hanyf
	 * @Description Integer 小于 0 返回 0
	 * @Date 10:35 2019/7/30
	 * @Param [in]
	 * @return int
	 **/
	public static int getPositiveInt(Integer in) {
		int value = getInt(in, 0);
		if (value < 0) {
			return 0;
		}
		return value;
	}

	/**
	 * @Author hanyf
	 * @Description String 转 int
	 * @Date 10:35 2019/7/30
	 * @Param [in, intForNull]
	 * @return int
	 **/
	public static int getInt(String in, int intForNull) {
		int iRet = intForNull;
		try {
			if ((in == null) || (in.trim().length() <= 0))
				iRet = intForNull;
			else
				iRet = Integer.parseInt(in);
		} catch (NumberFormatException e) {
			return iRet = intForNull;
		}

		return iRet;
	}

	/**
	 * @Author hanyf
	 * @Description
	 * @Date 10:36 2019/7/30
	 * @Param [in]
	 * @return int
	 **/
	public static int getInt(String in) {
		return getInt(in, 0);
	}

	/**
	 * @Author hanyf
	 * @Description 对象转 int
	 * @Date 10:36 2019/7/30
	 * @Param [in]
	 * @return int
	 **/
	public static int getInt(Object in) {
		if (in == null) {
			return getInt("", 0);
		} else {
			return getInt(in.toString(), 0);
		}
	}

	public static Integer getInteger(Long l) {
		return l == null ? null : l.intValue();
	}

	/**
	 * @Author hanyf
	 * @Description 对象转 double
	 * @Date 10:37 2019/7/30
	 * @Param [in]
	 * @return double
	 **/
	public static double getDouble(Object in) {
		if (in == null) {
			return 0;
		} else if (in instanceof BigDecimal) {
			return ((BigDecimal) in).doubleValue();
		} else if (in instanceof Double) {
			return ((Double) in).doubleValue();
		} else {
			double rst = 0;

			try {
				rst = Double.parseDouble(("" + in).trim());
			} catch (Exception e) {
				rst = 0;
			}

			return rst;
		}
	}

	/**
	 * @Author hanyf
	 * @Description 对象转 long
	 * @Date 10:38 2019/7/30
	 * @Param [in]
	 * @return long
	 **/
	public static long getLong(Object in) {
		if (in == null) {
			return 0;
		} else if (in instanceof BigDecimal) {
			return ((BigDecimal) in).longValue();
		} else if (in instanceof Double) {
			return ((Double) in).longValue();
		} else {
			long rst = 0;

			try {
				rst = Long.parseLong(("" + in).trim());
			} catch (Exception e) {
				rst = 0;
			}

			return rst;
		}
	}

	/**
	 * @Author hanyf
	 * @Description short类型 判断是否为空
	 * @Date 10:38 2019/7/30
	 * @Param [in]
	 * @return java.lang.Short
	 **/
	public static Short getShort(Short in) {
		if (in == null) {
			return 0;
		}
		return in;
	}

	/**
	 * @Author hanyf
	 * @Description String 转集合
	 * @Date 10:40 2019/7/30
	 * @Param [strNumber, regex]
	 * @return java.util.List<T>
	 **/
	public static <T> List<T> toNumberList(String strNumber, String regex){
		try {
			if (strNumber == null || strNumber.equals("")) {
				return null;
			}
			List<T> t = new ArrayList<T>();
			String[] numberArray = strNumber.split(regex);

			for (String s : numberArray) {
				String methodName = "parse" + ((T) s).getClass().getName();
				Method m1 = ((T) s).getClass().getMethod(methodName, null);

				t.add((T) (m1.invoke(s, null)));
			}
			return t;
		} catch (Exception e) {
			return Collections.emptyList();
		}
	}

	public static List<Long> toLongList(String strNumber, String regex) {
		List<Long> t = new ArrayList<Long>();
		if (strNumber == null || strNumber.equals("")) {
			return t;
		}
		String[] numberArray = strNumber.split(regex);
		for (String s : numberArray) {
			t.add(getLong(s));
		}

		return t;
	}

	public static List<Integer> toIntegerList(String strNumber, String regex) {
		List<Integer> t = new ArrayList<Integer>();
		if (strNumber == null || strNumber.equals("")) {
			return t;
		}
		String[] numberArray = strNumber.split(regex);
		for (String s : numberArray) {
			t.add(getInt(s));
		}

		return t;
	}



	public static Set<Long> toLongSet(String strNumber, String regex) {
		Set<Long> t = new HashSet<Long>();
		if (strNumber == null || strNumber.equals("")) {
			return t;
		}
		String[] numberArray = strNumber.split(regex);
		for (String s : numberArray) {
			t.add(getLong(s));
		}

		return t;
	}

	public static Set<String> toStringSet(String strNumber, String regex) {
		Set<String> t = new HashSet<String>();
		if (strNumber == null || strNumber.equals("")) {
			return t;
		}
		String[] numberArray = strNumber.split(regex);
		for (String s : numberArray) {
			t.add(s);
		}

		return t;
	}

	public static short getShort(String shot, short shortForNull) {
		short resuFlag = shortForNull;
		try {
			if ((shot == null) || (shot.trim().length() <= 0))
				resuFlag = shortForNull;
			else
				resuFlag = Short.parseShort(shot);
		} catch (Exception e) {
			return resuFlag = shortForNull;
		}
		return resuFlag;
	}

	public static short getShort(String shot) {
		return getShort(shot, (short) 0);
	}

	public static short getShort(Object shot) {
		if (shot == null) {
			return getShort("", (short) 0);
		} else {
			return getShort(shot.toString(), (short) 0);
		}
	}

	public static short getShort(Short shot, short shortForNull) {
		if (shot == null)
			return shortForNull;
		else
			return shot.shortValue();
	}

	public static Float getFloat(String str) {
		if ((str == null) || (str.trim().length() <= 0))
			return (float) 0;
		else
			return Float.parseFloat(str);
	}

	public static BigDecimal toBigDecimal(Object obj, int newScale) {
		if (obj == null) {
			return BigDecimal.ZERO;
		}
		try {
			BigDecimal bd = new BigDecimal(obj.toString());
			// 设置小数位数，第一个变量是小数位数，第二个变量是取舍方法(四舍五入)
			return bd.setScale(newScale, BigDecimal.ROUND_HALF_UP);

		} catch (Exception e) {
			return BigDecimal.ZERO;
		}
	}

	/**
	 * @Author hanyf
	 * @Description 默认保留两位小数
	 * @Date 11:16 2019/7/30
	 * @Param [obj]
	 * @return java.math.BigDecimal
	 **/
	public static BigDecimal toBigDecimal(Object obj) {
		return toBigDecimal(obj, 2);
	}

	/**
	 * @Author hanyf
	 * @Description List 转 String
	 * @Date 11:16 2019/7/30
	 * @Param [list, separator]
	 * @return java.lang.String
	 **/
	public static String listToString(List<?> list, char separator) {
		if (list == null) {
			return "";
		}
		return StringUtils.join(list.toArray(), separator);
	}

	public static void main(String[] args) {
		BigDecimal price = new BigDecimal(3214.3420000);
		BigDecimal bigDecimal = toBigDecimal(price);
		BigDecimal bigDecimal1 = toBigDecimal(price);
		toBigDecimal(price);
		System.out.println(price);
		System.out.println(bigDecimal);
		System.out.println(price.stripTrailingZeros());
		System.out.println(bigDecimal.stripTrailingZeros());
		System.out.println(bigDecimal1.stripTrailingZeros());
	}
}
