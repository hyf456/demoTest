package com.han.spel;


import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.han.commom.util.MapConvertorUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.expression.MethodBasedEvaluationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.expression.AccessException;
import org.springframework.expression.BeanResolver;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Description 数据权限
 * @Date 2020/4/13 18:26
 * @Author hanyf
 */
@Slf4j
@Configuration
public class DataPermissionFilter {

	public static void main(String[] args) {
		// try {
		// 	test2();
		// } catch (NoSuchMethodException e) {
		// 	e.printStackTrace();
		// }
		// test4();
		// test5();
		// //创建ExpressionParser解析表达式
		// ExpressionParser parser = new SpelExpressionParser();
		// //创建一个虚拟的容器EvaluationContext
		// StandardEvaluationContext ctx = new StandardEvaluationContext();
		// String result2 = parser.parseExpression("#root").getValue(ctx, String.class);
		// String s = new String("abcdef");
		// String json = new String ("{\"id\":\"10001\",\"name\":\"肉类\",\"menus\":[{\"name\":\"牛肉\",\"price\":\"30.00\"},{\"name\":\"羊肉\",\"price\":\"20.00\"}]}");
		// ctx.setVariable("name",json);
		// //取id为abc的bean，然后调用其中的substring方法
		// String value2 = parser.parseExpression("#name").getValue(ctx, String.class);
		// System.out.println(value2);
		test17_3();
		// test26();
		// test213();

	}

	private static void test321()  {
		User user = new User();
		user.setUserCode("hyf");
		user.setUserName("韩云飞");
		user.setId(10L);
		user.setGoodsSearchAdvance(new AdvanceSearchVO());
		User user1 = new User();
		user1.setUserName("3214");
		user1.setId(1L);
		User user2 = new User();
		user2.setUserName("56437867");
		user2.setId(3L);
		user.setChildren(Lists.newArrayList(user1, user2));
		ExpressionParser parser = new org.springframework.expression.spel.standard.SpelExpressionParser();
		Expression expression = parser.parseExpression("goodsSearchAdvance?.goodsOptCode");
		StandardEvaluationContext evaluationContext = new StandardEvaluationContext(user);
		expression.setValue(evaluationContext, "1234");
		System.out.println(JSONObject.toJSONString(user));
	}

	private static void test()  {
		Method method = null;
		try {
			method = DataPermissionFilter.class.getMethod("test1");
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}

		Object[] params = new Object[2];
		params[0] = "123";
		User user = new User();
		user.setUserCode("hyf");
		user.setUserName("韩云飞");
		user.setId(10L);
		User user1 = new User();
		user1.setUserName("3214");
		user1.setId(1L);
		User user2 = new User();
		user2.setUserName("56437867");
		user2.setId(3L);
		user.setChildren(Lists.newArrayList(user1, user2));
		params[1] = user;
		String s = JSONObject.toJSONString(user);
		long nowTime = System.currentTimeMillis();
		ExpressionParser parser = new org.springframework.expression.spel.standard.SpelExpressionParser();
		Expression expression = parser.parseExpression("#p1.userName");
		Object[] paramsa = new Object[2];
		paramsa[0] = s;
		EvaluationContext evaluationContext = new MethodBasedEvaluationContext(paramsa, method, paramsa, new DefaultParameterNameDiscoverer());
		evaluationContext.setVariable("p1", user);
		expression.setValue(evaluationContext, "1234");
		System.out.println(JSONObject.toJSONString(user));
		System.out.println(JSONObject.toJSONString(params));
		System.out.println(JSONObject.toJSONString(s));
		System.out.println(JSONObject.toJSONString(paramsa));
	}

	private static void test2() throws NoSuchMethodException {
		Method method = DataPermissionFilter.class.getMethod("test1");
		//创建一个虚拟的容器EvaluationContext
		StandardEvaluationContext ctx = new StandardEvaluationContext();
		ctx.registerFunction("test1", method);

		Object[] params = new Object[2];
		params[0] = "123";
		User user = new User();
		user.setUserCode("hyf");
		user.setUserName("韩云飞");
		user.setId(10L);
		params[1] = user;
		long nowTime = System.currentTimeMillis();
		ExpressionParser parser = new org.springframework.expression.spel.standard.SpelExpressionParser();
		Expression expression = parser.parseExpression("#p1.userName");
		EvaluationContext evaluationContext = new MethodBasedEvaluationContext(params, method, params, new DefaultParameterNameDiscoverer());
		evaluationContext.lookupVariable("user");
		User userTest = new User();
		userTest.setUserName("测试一下覆盖参数");
		evaluationContext.setVariable("p1", userTest);
		System.out.println("expression.getValue()=" + expression.getValue(evaluationContext));
		System.out.println(JSONObject.toJSONString(user));
		System.out.println(JSONObject.toJSONString(userTest));
	}


	public static void test4() {
		String expressionStr = "1+2";
		ExpressionParser parser = new org.springframework.expression.spel.standard.SpelExpressionParser();
		Expression expression = parser.parseExpression(expressionStr);
		Integer val = expression.getValue(Integer.class);
		System.out.println(expressionStr + "的结果是：" + val);
		System.out.println(parser.parseExpression("'abc'").getValue().equals("abc"));
		System.out.println(parser.parseExpression("'''abc'").getValue().equals("'abc"));
		//直接访问String的length()方法。
		System.out.println(parser.parseExpression("'abc'.lenMapConvertorUtilgth()").getValue().equals(3));

		User user = new User();
		user.setUserCode("hyf");
		user.setUserName("韩云飞");
		user.setId(10L);
		String s = JSONObject.toJSONString(user);
		java.util.Map<String, Object> stringObjectMap = Maps.newHashMap();
		try {
			stringObjectMap = MapConvertorUtil.objectAsMap(user);
		} catch (IOException e) {
			e.printStackTrace();
		}
		EvaluationContext context = new StandardEvaluationContext(stringObjectMap);

		// System.out.println(parser.parseExpression("stringObjectMap['userName']").getValue());
		// System.out.println(parser.parseExpression("stringObjectMap['userName']").getValue(context));
		// System.out.println(parser.parseExpression("getUserName()").getValue(context, String.class));
		// parser.parseExpression("stringObjectMap['userName']").setValue(context, "w42341");

	}

	public static void test5() {
		User user = new User();
		user.setUserCode("hyf");
		user.setUserName("韩云飞");
		user.setId(10L);
		EvaluationContext context = new StandardEvaluationContext();
		//1、设置变量
		context.setVariable("user", user);
		ExpressionParser parser = new org.springframework.expression.spel.standard.SpelExpressionParser();
		//2、表达式中以#varName的形式使用变量
		Expression expression = parser.parseExpression("#user.userName");
		//3、在获取表达式对应的值时传入包含对应变量定义的EvaluationContext
		String userName = expression.getValue(context, String.class);
		expression.setValue(user, "userTest");
		//表达式中使用变量，并在获取值时传递包含对应变量定义的EvaluationContext。
		System.out.println(userName.equals("abc"));
		System.out.println(JSONObject.toJSONString(user));
	}

	//注册方法
	static class MathUtils {
		public static int plusTen(int i) {
			return i+10;
		}
	}

	public void test15() throws NoSuchMethodException, SecurityException {
		ExpressionParser parser = new org.springframework.expression.spel.standard.SpelExpressionParser();
		//1、获取需要设置的java.lang.reflect.Method，需是static类型
		Method plusTen = MathUtils.class.getDeclaredMethod("plusTen", int.class);
		StandardEvaluationContext context = new StandardEvaluationContext();
		//2、注册方法到StandardEvaluationContext，第一个参数对应表达式中需要使用的方法名
		context.registerFunction("plusTen", plusTen);
		//3、表达式中使用注册的方法
		Expression expression = parser.parseExpression("#plusTen(10)");
		//4、传递包含对应方法注册的StandardEvaluationContext给Expression以获取对应的值
		int result = expression.getValue(context, int.class);
	}

	public void test17_1() {
		ExpressionParser parser = new org.springframework.expression.spel.standard.SpelExpressionParser();
		Date d = new Date();
		//设日期为1号
		parser.parseExpression("date").setValue(d, 1);
		int date = (Integer)parser.parseExpression("date").getValue(d);

	}

	public static void test17_3() {
		ExpressionParser parser = new org.springframework.expression.spel.standard.SpelExpressionParser();
		User user = new User();
		user.setUserCode("hyf");
		user.setUserName("韩云飞");
		user.setId(10L);
		String s = JSONObject.toJSONString(user);
		java.util.Map<String, Object> stringObjectMap = Maps.newHashMap();
		try {
			stringObjectMap = MapConvertorUtil.objectAsMapExcludeNullValue(s);
		} catch (IOException e) {
			e.printStackTrace();
		}
		user.setMap(stringObjectMap);
		EvaluationContext context = new StandardEvaluationContext();
		//添加变量以方便表达式访问
		context.setVariable("map", user);
		//设置第一个元素的值为1
		parser.parseExpression("#p0.map['userName']").setValue(context, 1);
		System.out.println(parser.parseExpression("#p0.map['userName']").getValue(context));
		System.out.println(s);
		System.out.println(stringObjectMap.toString());
	}

	public static void test17_4() {
		ExpressionParser parser = new org.springframework.expression.spel.standard.SpelExpressionParser();
		User user = new User();
		user.setUserCode("hyf");
		user.setUserName("韩云飞");
		user.setId(10L);
		User user1 = new User();
		user1.setUserName("3214");
		user1.setId(1L);
		User user2 = new User();
		user2.setUserName("56437867");
		user2.setId(3L);
		user.setChildren(Lists.newArrayList(user1, user2));
		String s = JSONObject.toJSONString(user);
		JSONObject jsonObject = JSONObject.parseObject(s);
		EvaluationContext context = new StandardEvaluationContext();
		//添加变量以方便表达式访问
		context.setVariable("jsonObject", jsonObject);
		parser.parseExpression("#jsonObject['userName']").setValue(context, "qerwtgdsfg");
		Object children = jsonObject.get("children");
		context.setVariable("children", children);
		parser.parseExpression("#children[0]['userName']").setValue(context, "fsdaf");
		System.out.println(children);
		// List<Map<String, Object>> value = (List<Map<String, Object>>)parser.parseExpression("#children").getValue(context);
		// if (ToolUtil.isNotEmpty(value)) {
		// 	for (Map<String, Object> stringObjectMap : value) {
		// 		stringObjectMap.put("userName", "无权限");
		// 	}
		// }
		// System.out.println(value);
		System.out.println(jsonObject.toJSONString());


	}

	public static void test26() {
		ExpressionParser parser = new org.springframework.expression.spel.standard.SpelExpressionParser();
		StandardEvaluationContext context = new StandardEvaluationContext();
		context.setBeanResolver(new MyBeanResolver());
		//访问bean名称为hello的bean对象的getKey()方法。
		Object obj = parser.parseExpression("@user.userName").getValue(context);
		System.out.println(obj);
	}

	private static class MyBeanResolver implements BeanResolver {

		private static ApplicationContext appContext = new ClassPathXmlApplicationContext("applicationContext.xml");

		@Override
		public Object resolve(EvaluationContext context, String beanName)
				throws AccessException {
			return appContext.getBean(beanName);
		}

	}

	public static void test213() {

		String jsonString = "{\"page_size\":\"12321321\",\"page_size\":\"123\",\"page_index\":0,\"page_count\":0,\"has_next\":false,\"datas\":null,\"code\":0,\"issuccess\":true,\"msg\":null}";
		String regex = "\"page_size\":\"(.*?)\\\"";//使用非贪婪模式
		// String regex = "\"page_size\":\"(.*?)\\\", \"page_size\":\"无权限\"";
		// jsonString = jsonString.replaceAll("\"page_size\":\"(.*?)\\\"", "\"page_size\":\"无权限\"");
		// System.out.println(jsonString);
		Matcher matcher = Pattern.compile(regex).matcher(jsonString);
		System.out.println(jsonString);
		while (matcher.find()) {
			String ret = matcher.group(1);
			System.out.println("key:" + matcher.group(1));
		}

		String str = "{\"keyword\":\"xin \"hua\",\"type\":\"news\",\"countryName\":\"not中国\"," +
				"\"pageNo\":1,\"pageSize\":10,\"beginDate\":\"\"\"2018-05-01 00:00:00\"\"\"," +
				"\"endDate\":\"2018-05-09 23:59:59\"}";
		Pattern pattern = Pattern.compile("\"([a-zA-z0-9]{0,})\":\"{1}([a-zA-z0-9\\-\\s\\:\\u4e00-\\u9fa5\"]{0,})\"{1}[\\,\\}]{1}|" +
				"\"([a-zA-z0-9]{0,})\":([a-zA-z0-9\\-\\s\\:\\u4e00-\\u9fa5\"]{0,})[\\,\\}]{1}");
		Matcher matcher1 = pattern.matcher(str);
		while (matcher1.find()){
			if(matcher1.group(1)!=null) {
				//System.out.println("key:" + matcher1.group(1));
				System.out.println("value:" + matcher1.group(2));
			}else{
				// System.out.println("key:" + matcher1.group(3));
				System.out.println("value:" + matcher1.group(4));
			}
		}

		User user = new User();
		user.setUserCode("hyf");
		user.setUserName("韩云飞");
		user.setId(10L);
		user.setGoodsSearchAdvance(new AdvanceSearchVO());
		User user1 = new User();
		user1.setUserName("3214");
		user1.setId(1L);
		User user2 = new User();
		user2.setUserName("56437867");
		user2.setId(3L);
		user.setChildren(Lists.newArrayList(user1, user2));
		String jsonString1 = JSONObject.toJSONString(user);
		// jsonString1 = jsonString1.replaceAll("\"id\":([a-zA-z0-9\\-\\s\\:\\u4e00-\\u9fa5\"]{0,})[\\,\\}]{0}", "\"id\":\"无权限\"");
		jsonString1 = jsonString1.replaceAll("\"userName\":([a-zA-z0-9\\-\\s\\:\\u4e00-\\u9fa5\"]{0,})[\\,\\}]{0}", "\"userName\":\"无权限\"");
		System.out.println(jsonString1);
	}
}
