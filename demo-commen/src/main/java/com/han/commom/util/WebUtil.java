package com.han.commom.util;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.yestae.base.user.model.enums.SystemExceptionEnum;
import com.yestae.framework.common.domain.DataResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Description TODO
 * @Date 2019/8/14 19:48
 * @Author hanyf
 */
public final class WebUtil {

	/**
	 * 上传附件标识key
	 */
	private static String FILE_NAME = "attachFile";
	private static Logger logger = LoggerFactory.getLogger(WebUtil.class);

	public static HttpServletRequest getRequest() {
		RequestAttributes requestAttributes = null;
		try {
			requestAttributes = RequestContextHolder.currentRequestAttributes();
		} catch (Exception e) {
			// ignore 如unit test
		}

		if (requestAttributes != null && requestAttributes instanceof ServletRequestAttributes) {
			return ((ServletRequestAttributes) requestAttributes).getRequest();
		}

		return null;

	}

	public static ConcurrentMap<String, Object> requestToMap() {
		HttpServletRequest request = getRequest();
		return requestToMap(request);
	}

	@SuppressWarnings("rawtypes")
	public static ConcurrentMap<String, Object> requestToMap(HttpServletRequest request) {
		ConcurrentMap<String, Object> result = Maps.newConcurrentMap();
		if (request != null) {
			Enumeration rnames = request.getParameterNames();
			for (Enumeration e = rnames; e.hasMoreElements();) {
				String thisName = e.nextElement().toString();
				if (FILE_NAME.equalsIgnoreCase(thisName)) {
					continue;
				}
				Object thisValue = request.getParameter(thisName);
				result.put(thisName, thisValue);
			}
		}
		return result;
	}

	public static ConcurrentMap<String, Object> getHeaders(HttpServletRequest request) {
		ConcurrentMap<String, Object> result = Maps.newConcurrentMap();
		if (request != null) {
			Enumeration rnames = request.getHeaderNames();
			for (Enumeration e = rnames; e.hasMoreElements();) {
				String thisName = e.nextElement().toString();
				Object thisValue = request.getHeader(thisName);
				result.put(thisName, thisValue);
			}
		}
		return result;
	}

	public static String getIp() {
		return getIp(getRequest());
	}

	public static String getIp(HttpServletRequest request) {
		if (request == null) {
			return "127.0.0.1";
		}
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("X-Forwarded-For");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("X-Real-IP");
		}

		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

	public static String getUa() {
		return getUa(getRequest());
	}

	public static String getUa(HttpServletRequest request) {
		return request.getHeader("User-Agent");
	}

	public static String getSiteIp() {
		try {
			Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces();
			while (en.hasMoreElements()) {
				NetworkInterface ni = en.nextElement();
				if (null != ni.getDisplayName() && ni.getDisplayName().contains("Wireless") && ni.isUp()) {
					List<InterfaceAddress> list = ni.getInterfaceAddresses();
					Iterator<InterfaceAddress> it = list.iterator();
					while (it.hasNext()) {
						InterfaceAddress ia = it.next();
						if (null != ia.getBroadcast()) {
							return ia.getAddress().getHostAddress();
						}
					}
				}
			}
		} catch (SocketException e) {
			e.printStackTrace();
		}
		return "127.0.0.1";
	}

	/**
	 * 取出一个指定长度大小的随机正整数.
	 *
	 * @param length
	 *            int 设定所取出随机数的长度。length小于11
	 * @return int 返回生成的随机数。
	 */
	public static int buildRandom(int length) {
		int num = 1;
		double random = Math.random();
		if (random < 0.1) {
			random = random + 0.1;
		}
		for (int i = 0; i < length; i++) {
			num = num * 10;
		}
		return (int) ((random * num));
	}

	/**
	 * 取出5个长度大小的随机正整数.
	 *
	 * @return String 返回生成的随机数。
	 */
	public static String getMd5Salt() {
		int num = buildRandom(5);
		return String.valueOf(num);
	}

	/**
	 * 自定义主键默认16位
	 *
	 * @return String
	 */
	public static String bulidUUID() {
		return UUIDUtils.getUUID16();
	}

	/**
	 * 除去数组中的空值和签名参数
	 *
	 * @param params
	 *            签名参数组
	 * @return 去掉空值与签名参数后的新签名参数组
	 */
	public static Map<String, Object> paraFilter(Map<String, Object> params) {
		Map<String, Object> result = Maps.newHashMap();
		if (params == null || params.size() <= 0) {
			return result;
		}
		for (String key : params.keySet()) {
			Object value = params.get(key);
			if (value == null || value.equals("") || key.equalsIgnoreCase("sign") || key.equalsIgnoreCase("key")
					|| key.equalsIgnoreCase(FILE_NAME)) {
				continue;
			}
			result.put(key, value);
		}
		return result;
	}

	/**
	 * 检验API返回的数据里面的签名是否合法，避免数据在传输的过程中被第三方篡改
	 *
	 * @param key
	 *            签名key
	 * @param sign
	 *            签名值
	 * @param map
	 *            返回数据
	 * @return API签名是否合法
	 */
	public static boolean validSign(String key, String sign, Map<String, Object> map) {
		// 过滤签名参数
		Map<String, Object> temp = paraFilter(map);
		// 生成待签名字符串
		String link = createLinkString(temp);
		// 生成签名
		String mySign = MD5Util.encrypt(link + key);
		logger.info("【待签名字符串：" + link + "】【后台生成签名:" + mySign + "】【app生成签名:" + sign + "】");
		// 校验
		if (!mySign.equals(sign)) {
			return false;
		}
		return true;
	}

	/**
	 * 检验API返回的数据里面的签名是否合法，避免数据在传输的过程中被第三方篡改
	 *
	 * @param sign
	 *            签名值
	 * @param map
	 *            返回数据
	 * @return API签名是否合法
	 */
	public static boolean validSign(String sign, Map<String, Object> map) {
		// 过滤签名参数
		Map<String, Object> temp = paraFilter(map);
		// 生成待签名字符串
		String link = createLinkString(temp);
		// 生成签名
		String mySign = MD5Util.encrypt(link + getSecretKey());
		logger.info("【待签名字符串：" + link + "】【后台生成签名:" + mySign + "】【app生成签名:" + sign + "】");
		// 校验
		if (!mySign.equals(sign)) {
			return false;
		}
		return true;
	}

	/**
	 * 把数组所有元素排序，并按照“参数=参数值”的模式用“&”字符拼接成字符串
	 *
	 * @param params
	 *            需要排序并参与字符拼接的参数组
	 * @return 拼接后字符串
	 */
	public static String createLinkString(Map<String, Object> params) {
		List<String> keys = new ArrayList<String>(params.keySet());
		Collections.sort(keys);
		StringBuilder prestr = new StringBuilder();
		for (int i = 0; i < keys.size(); i++) {
			String key = keys.get(i);
			Object valueObj = params.get(key);
			boolean isBasic = valueObj instanceof Integer || valueObj instanceof String || valueObj instanceof Double || valueObj instanceof Float;
			String value = "";
			if(isBasic) {
				value = String.valueOf(valueObj);
			}else if(valueObj instanceof Collection) {
				value = JSONObject.toJSONString(valueObj);
			}else {
				Map<String, Object> obj2map = null;
				try {
					obj2map = MapConvertorUtil.objectAsMap(valueObj);
				} catch (IOException e) {
					e.printStackTrace();
				}
				value = createLinkString(obj2map);
			}
			if (i == keys.size() - 1) {// 拼接时，不包括最后一个&字符
				prestr.append(key).append("=").append(value);
			} else {
				prestr.append(key).append("=").append(value).append("&");
			}
		}
		return prestr.toString();
	}

	// 判断是否有顺序
	public static boolean isOrder(String str) {
		String orderStr = ""; // 顺序表
		for (int i = 33; i < 127; i++) {
			orderStr += Character.toChars(i)[0];
		}
		String reverseStr = new StringBuilder(str).reverse().toString();
		return orderStr.contains(str) || orderStr.contains(reverseStr);
	}

	// 判断是否相同
	public static boolean isSame(String str) {
		String regex = str.substring(0, 1) + "{" + str.length() + "}";
		return str.matches(regex);
	}

	public static void renderJson(HttpServletResponse response, String code, String message) throws IOException {
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Cache-Control", "no-store");
		response.setHeader("Pragma", "no-cache");
		response.setDateHeader("Expires", 0);
		PrintWriter out = response.getWriter();
		DataResult<Object> failed = DataResult.failed(SystemExceptionEnum.SYSTEM_ERROR.getCode(), SystemExceptionEnum.SYSTEM_ERROR.getMessage());
		out.print(JSONObject.toJSONString(failed));
		out.flush();
		out.close();
	}

	/**
	 * 获取字符串的长度，对双字符（包括汉字）按两位计数
	 *
	 * @param value
	 *            字符串
	 * @return int 长度
	 */
	public static int getStrLength(String value) {
		int valueLength = 0;
		String chinese = "[\u0391-\uFFE5]";
		for (int i = 0; i < value.length(); i++) {
			String temp = value.substring(i, i + 1);
			if (temp.matches(chinese)) {
				valueLength += 2;
			} else {
				valueLength += 1;
			}
		}
		return valueLength;
	}

	/**
	 * 生成6位数字随机验证码
	 *
	 * @return String
	 */
	public static String getSmsCode() {
		int num = buildRandom(6);
		return String.valueOf(num);
	}

	/**
	 * 获得签名key原始值
	 *
	 * @return String
	 */
	public static String getSecretKey() {
		return "";
	}
//
//	/**
//	 * 解密密码
//	 *
//	 * @return String
//	 */
//	public static String decryptPwd(String password) {
//		String key = getSecretKey();
//		return XXTEA.decryptBase64StringToString(password, key);
//	}

	public static String readInputStream(HttpServletRequest request) throws IOException {
		BufferedReader bReader = request.getReader();
		String inputLine;
		String str = "";
		while ((inputLine = bReader.readLine()) != null) {
			str += inputLine;
		}
		bReader.close();
		return str;

	}

	public static final byte[] readBytes(InputStream is, int contentLen) {
		if (contentLen > 0) {
			int readLen = 0;

			int readLengthThisTime = 0;

			byte[] message = new byte[contentLen];

			try {

				while (readLen != contentLen) {

					readLengthThisTime = is.read(message, readLen, contentLen - readLen);

					if (readLengthThisTime == -1) {// Should not happen.
						break;
					}

					readLen += readLengthThisTime;
				}

				return message;
			} catch (IOException e) {
				// Ignore
				e.printStackTrace();
			}
		}

		return new byte[] {};
	}

	/**
	 * 当前当期年月日后6位 + digit位随机数,作为一个唯一的订单编号
	 *
	 * @return 14位订单编号
	 */
	public static String getOrderNum(int digit) {
		Date date = new Date();
		DateFormat df = new SimpleDateFormat("yyMMdd");
		String temp = df.format(date);
		return temp + buildRandom(digit);
	}

	public static String getYyyyMMddHHmmss() {
		Date date = new Date();
		DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
		return df.format(date);
	}

	/**
	 * 获取当前当前月份首日时间戳
	 *
	 * @return
	 */
	public static int getMonthFirstDay() {
		Calendar calendar = Calendar.getInstance();// 获取当前日期
		calendar.add(Calendar.MONTH, 0);
		calendar.set(Calendar.DAY_OF_MONTH, 1);// 设置为1号,当前日期既为本月第一天
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		Long aLong = new Long(calendar.getTimeInMillis() / 1000l);
		return aLong.intValue();
	}

	/**
	 * 根据请求头信息判断是否微信浏览器
	 *
	 * @param userAgent
	 * @return true
	 */
	public static boolean isWeixin(String userAgent) {
		String ua = userAgent.toLowerCase();
		return ua.indexOf("micromessenger") != -1;
	}

	/**
	 * 隐藏数字中间四位
	 *
	 * @param num
	 *            需要掩码的数字
	 * @return 111****2222
	 */
	public static String mark(String num) {
		return num.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
	}

	/**
	 * 获取一定长度的随机字符串
	 *
	 * @param length
	 *            指定字符串长度
	 * @return 一定长度的字符串
	 */
	public static String getRandomStringByLength(int length) {
		String base = "abcdefghijklmnopqrstuvwxyz0123456789";
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			int number = random.nextInt(base.length());
			sb.append(base.charAt(number));
		}
		return sb.toString();
	}

	/**
	 * 是否支付金额
	 *
	 * 规则 浮点型 以1-9开头或0开头但必须是0. 小数点后只允许两位
	 *
	 * @param str
	 * @return
	 */
	public static boolean isPrice(String str) {
		Pattern pattern = Pattern.compile("^(([1-9]{1}\\d*)|([0]{1}))(\\.(\\d){0,2})?$"); // 判断小数点后2位的数字的正则表达式
		Matcher match = pattern.matcher(str);
		if (match.matches() == false) {
			return false;
		} else {
			return true;
		}
	}

	public static String timeStamp2Date(long timeStamp) {
		SimpleDateFormat format = new SimpleDateFormat("M月dd日 HH:mm");
		Long time = new Long(timeStamp);
		String d = format.format(time);
		return d;
	}

	public static String timeStamp2MonthAndDay(long timeStamp) {
		SimpleDateFormat format = new SimpleDateFormat("M月dd日");
		Long time = new Long(timeStamp);
		String d = format.format(time);
		return d;
	}

	public static String timeStamp2DateYMD(long timeStamp) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Long time = new Long(timeStamp);
		String d = format.format(time);
		return d;
	}

	public static void main(String[] args) {
		Map<String, Object> maps = Maps.newHashMap();
		maps.put("name", "Jane");
		maps.put("age", 12);
		List<String> list = Lists.newArrayList();
		list.add("asd");
		list.add("asd1");
		list.add("asd2");
		list.add("asd3");
		maps.put("roleIds", list);
		String createLinkString = createLinkString(maps);
		System.out.println(createLinkString);
	}
}
