package com.han.commom.util;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringEscapeUtils;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Description String 操作类
 * @Date 2019/7/30 11:50
 * @Author hanyf
 */
public class StringUtils extends org.apache.commons.lang3.StringUtils {

	private static final char SEPARATOR = '_';
	private static final String CHARSET_NAME = "UTF-8";

	/**
	 * 转换为字节数组
	 *
	 * @param str
	 * @return
	 */
	public static byte[] getBytes(String str) {
		if (str != null) {
			try {
				return str.getBytes(CHARSET_NAME);
			} catch (UnsupportedEncodingException e) {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 转换为字节数组
	 *
	 * @param bytes
	 * @return
	 */
	public static String toString(byte[] bytes) {
		try {
			return new String(bytes, CHARSET_NAME);
		} catch (UnsupportedEncodingException e) {
			return EMPTY;
		}
	}

	/**
	 * 是否包含字符串
	 *
	 * @param str
	 *            验证字符串
	 * @param strs
	 *            字符串组
	 * @return 包含返回true
	 */
	public static boolean inString(String str, String... strs) {
		if (str != null) {
			for (String s : strs) {
				if (str.equals(trim(s))) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 替换掉HTML标签方法
	 */
	public static String replaceHtml(String html) {
		if (isBlank(html)) {
			return "";
		}
		String regEx = "<.+?>";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(html);
		String s = m.replaceAll("");
		return s;
	}

	/**
	 * 替换为手机识别的HTML，去掉样式及属性，保留回车。
	 *
	 * @param html
	 * @return
	 */
	public static String replaceMobileHtml(String html) {
		if (html == null) {
			return "";
		}
		return html.replaceAll("<([a-z]+?)\\s+?.*?>", "<$1>");
	}

	/**
	 * 替换为手机识别的HTML，去掉样式及属性，保留回车。
	 *
	 * @param txt
	 * @return
	 */
	public static String toHtml(String txt) {
		if (txt == null) {
			return "";
		}
		return replace(replace(Encodes.escapeHtml(txt), "\n", "<br/>"), "\t", "&nbsp; &nbsp; ");
	}

	/**
	 * 缩略字符串（不区分中英文字符）
	 *
	 * @param str
	 *            目标字符串
	 * @param length
	 *            截取长度
	 * @return
	 */
	public static String abbr(String str, int length) {
		if (str == null) {
			return "";
		}
		try {
			StringBuilder sb = new StringBuilder();
			int currentLength = 0;
			for (char c : replaceHtml(StringEscapeUtils.unescapeHtml4(str)).toCharArray()) {
				currentLength += String.valueOf(c).getBytes("GBK").length;
				if (currentLength <= length - 3) {
					sb.append(c);
				} else {
					sb.append("...");
					break;
				}
			}
			return sb.toString();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "";
	}

	public static String abbr2(String param, int length) {
		if (param == null) {
			return "";
		}
		StringBuffer result = new StringBuffer();
		int n = 0;
		char temp;
		boolean isCode = false; // 是不是HTML代码
		boolean isHTML = false; // 是不是HTML特殊字符,如&nbsp;
		for (int i = 0; i < param.length(); i++) {
			temp = param.charAt(i);
			if (temp == '<') {
				isCode = true;
			} else if (temp == '&') {
				isHTML = true;
			} else if (temp == '>' && isCode) {
				n = n - 1;
				isCode = false;
			} else if (temp == ';' && isHTML) {
				isHTML = false;
			}
			try {
				if (!isCode && !isHTML) {
					n += String.valueOf(temp).getBytes("GBK").length;
				}
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}

			if (n <= length - 3) {
				result.append(temp);
			} else {
				result.append("...");
				break;
			}
		}
		// 取出截取字符串中的HTML标记
		String temp_result = result.toString().replaceAll("(>)[^<>]*(<?)", "$1$2");
		// 去掉不需要结素标记的HTML标记
		temp_result = temp_result.replaceAll(
				"</?(AREA|BASE|BASEFONT|BODY|BR|COL|COLGROUP|DD|DT|FRAME|HEAD|HR|HTML|IMG|INPUT|ISINDEX|LI|LINK|META|OPTION|P|PARAM|TBODY|TD|TFOOT|TH|THEAD|TR|area|base|basefont|body|br|col|colgroup|dd|dt|frame|head|hr|html|img|input|isindex|li|link|meta|option|p|param|tbody|td|tfoot|th|thead|tr)[^<>]*/?>",
				"");
		// 去掉成对的HTML标记
		temp_result = temp_result.replaceAll("<([a-zA-Z]+)[^<>]*>(.*?)</\\1>", "$2");
		// 用正则表达式取出标记
		Pattern p = Pattern.compile("<([a-zA-Z]+)[^<>]*>");
		Matcher m = p.matcher(temp_result);
		List<String> endHTML = Lists.newArrayList();
		while (m.find()) {
			endHTML.add(m.group(1));
		}
		// 补全不成对的HTML标记
		for (int i = endHTML.size() - 1; i >= 0; i--) {
			result.append("</");
			result.append(endHTML.get(i));
			result.append(">");
		}
		return result.toString();
	}

	/**
	 * 转换为Double类型
	 */
	public static Double toDouble(Object val) {
		if (val == null) {
			return 0D;
		}
		try {
			return Double.valueOf(trim(val.toString()));
		} catch (Exception e) {
			return 0D;
		}
	}

	/**
	 * 转换为Float类型
	 */
	public static Float toFloat(Object val) {
		return toDouble(val).floatValue();
	}

	/**
	 * 转换为Long类型
	 */
	public static Long toLong(Object val) {
		return toDouble(val).longValue();
	}

	/**
	 * 转换为Integer类型
	 */
	public static Integer toInteger(Object val) {
		return toLong(val).intValue();
	}

	/**
	 * 转换为BigDecimal类型
	 *
	 * @param val
	 * @param scale 保留位数
	 * @return
	 */
	public static BigDecimal toBigDecimal(String val, int scale) {
		if (val == null) {
			return BigDecimal.ZERO;
		}
		try {
			BigDecimal bd = new BigDecimal(val);
			// 设置小数位数，第一个变量是小数位数，第二个变量是取舍方法(四舍五入)
			return bd.setScale(scale, BigDecimal.ROUND_HALF_UP);

		} catch (Exception e) {
			return BigDecimal.ZERO;
		}
	}

	/**
	 * 获得用户远程地址
	 */
	// public static String getRemoteAddr(HttpServletRequest request) {
	// 	String remoteAddr = request.getHeader("X-Real-IP");
	// 	if (isNotBlank(remoteAddr)) {
	// 		remoteAddr = request.getHeader("X-Forwarded-For");
	// 	} else if (isNotBlank(remoteAddr)) {
	// 		remoteAddr = request.getHeader("Proxy-Client-IP");
	// 	} else if (isNotBlank(remoteAddr)) {
	// 		remoteAddr = request.getHeader("WL-Proxy-Client-IP");
	// 	}
	// 	return remoteAddr != null ? remoteAddr : request.getRemoteAddr();
	// }

	/**
	 * 驼峰命名法工具en
	 *
	 * @return toCamelCase("hello_world") == "helloWorld"
	 *         toCapitalizeCamelCase("hello_world") == "HelloWorld"
	 *         toUnderScoreCase("helloWorld") = "hello_world"
	 */
	public static String toCamelCase(String s) {
		if (s == null) {
			return null;
		}

		s = s.toLowerCase();

		StringBuilder sb = new StringBuilder(s.length());
		boolean upperCase = false;
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);

			if (c == SEPARATOR) {
				upperCase = true;
			} else if (upperCase) {
				sb.append(Character.toUpperCase(c));
				upperCase = false;
			} else {
				sb.append(c);
			}
		}

		return sb.toString();
	}

	/**
	 * 驼峰命名法工具
	 *
	 * @return toCamelCase("hello_world") == "helloWorld"
	 *         toCapitalizeCamelCase("hello_world") == "HelloWorld"
	 *         toUnderScoreCase("helloWorld") = "hello_world"
	 */
	public static String toCapitalizeCamelCase(String s) {
		if (s == null) {
			return null;
		}
		s = toCamelCase(s);
		return s.substring(0, 1).toUpperCase() + s.substring(1);
	}

	/**
	 * 驼峰命名法工具
	 *
	 * @return toCamelCase("hello_world") == "helloWorld"
	 *         toCapitalizeCamelCase("hello_world") == "HelloWorld"
	 *         toUnderScoreCase("helloWorld") = "hello_world"
	 */
	public static String toUnderScoreCase(String s) {
		if (s == null) {
			return null;
		}

		StringBuilder sb = new StringBuilder();
		boolean upperCase = false;
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);

			boolean nextUpperCase = true;

			if (i < (s.length() - 1)) {
				nextUpperCase = Character.isUpperCase(s.charAt(i + 1));
			}

			if ((i > 0) && Character.isUpperCase(c)) {
				if (!upperCase || !nextUpperCase) {
					sb.append(SEPARATOR);
				}
				upperCase = true;
			} else {
				upperCase = false;
			}

			sb.append(Character.toLowerCase(c));
		}

		return sb.toString();
	}

	/**
	 * 如果不为空，则设置值
	 *
	 * @param target
	 * @param source
	 */
	public static void setValueIfNotBlank(String target, String source) {
		if (isNotBlank(source)) {
			target = source;
		}
	}




	public static String trimStr(String str){
		return str == null ? "" : str.trim();
	}



	/**
	 * 转换为JS获取对象值，生成三目运算返回结果
	 *
	 * @param objectString
	 *            对象串 例如：row.user.id
	 *            返回：!row?'':!row.user?'':!row.user.id?'':row.user.id
	 */
	public static String jsGetVal(String objectString) {
		StringBuilder result = new StringBuilder();
		StringBuilder val = new StringBuilder();
		String[] vals = split(objectString, ".");
		for (int i = 0; i < vals.length; i++) {
			val.append("." + vals[i]);
			result.append("!" + (val.substring(1)) + "?'':");
		}
		result.append(val.substring(1));
		return result.toString();
	}

	/**
	 * 截取orgNo.把最后一位的0替换为1
	 *
	 * @Description: (描述)
	 * @Title: subOrg
	 */
	public static String subOrg(String oNo) {
		StringBuffer tempOrgNo = new StringBuffer("");
		if (oNo != null && !"".equals(oNo)) {
			tempOrgNo.append(oNo.substring(0, oNo.length() - 1)).append("1");
		}
		return tempOrgNo.toString();
	}

	public static boolean isNumeric(String str) {
		for (int i = str.length(); --i >= 0;) {
			int chr = str.charAt(i);
			if (chr < 48 || chr > 57)
				return false;
		}
		return true;
	}

	public static boolean isDouble(String str) {
		Pattern pattern = Pattern.compile("[0-9]+\\.[0-9]+");
		return pattern.matcher(str).matches();
	}

	/**
	 * description: 去除字符串前后的空格，若str</code>为<code>null</code>,返回空串
	 *
	 * @param str
	 * @return
	 */
	public static String trim(String str) {
		return str == null ? "" : str.trim();
	}

	/**
	 * 判断是否值不相同
	 *
	 * @param str
	 * @return
	 */
	public static boolean isNotEqual(String str, String str1, boolean iscontains) {
		if (null == str || null == str1) {
			return false;
		}
		if (iscontains) {
			if (str.contains(str1)) {
				return false;
			}
		} else if (str.equals(str1)) {
			return false;
		}
		return true;
	}

	/**
	 *
	 * description: 判断<code>str</code>是否为空串或<code>null</code>
	 *
	 * @param str
	 * @return 若为空串或<code>null</code>返回<code>true</code>,否则返回<code>false</code>
	 */
	public static boolean isEmpty(String str) {
		if (null == str)
			return true;
		str = trim(str);
		return "".equals(str) ? true : false;
	}

	/**
	 * 获取指定位数的随机数
	 *
	 * @param digCount
	 * @return
	 */
	public static String getRandomNumber(int digCount) {
		Random rnd = new Random();
		StringBuilder sb = new StringBuilder(digCount);
		for (int i = 0; i < digCount; i++)
			sb.append((char) ('0' + rnd.nextInt(10)));
		return sb.toString();
	}

	/**
	 * 给数字型式的日期格式化
	 *
	 * @param str
	 * @return
	 */
	public static String getFomDate(String str) {
		if (null == str || str.equals("")) {
			return "";
		}
		try {
			SimpleDateFormat formatDate = new SimpleDateFormat("yyyyMMdd");
			Date date = formatDate.parse(str.trim());
			return StringUtils.getfomDate("yyyy-MM-dd", date);

		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/**
	 * 给日期格式化成指定格式
	 *
	 * @param format
	 * @param date
	 * @return
	 */
	public static String getfomDate(String format, Date date) {
		SimpleDateFormat formatDate = new SimpleDateFormat(format);
		return null == date ? "" : formatDate.format(date);
	}

	public static int getStrLen(String str) {
		int len = 0;
		for (int i = 0; i < str.length(); i++) {
			char c = str.charAt(i);
			// 单字节加1
			if ((c >= 0x0001 && c <= 0x007e) || (0xff60 <= c && c <= 0xff9f)) {
				len++;
			} else {
				len += 2;
			}
		}
		return len;
	}
	/**
	 * 返回一个Long行参数的指定长度字符（从最后算起）
	 * @param num
	 * @return
	 */
	public static String getStrByLong(Long num,int length){
		if(num == null){
			return "";
		}else{
			String strNum = String.valueOf(num);
			if(strNum.length() <= length){
				return strNum;
			}else{
				return strNum.substring(strNum.length() - length,strNum.length());
			}
		}
	}

	/**
	 * return String of the Object
	 */
	public static String toString(Object obj){
		return obj == null ? "" : obj.toString();
	}
	public static void main(String[] args) {
		System.out.println("ceshi");
	}
}
