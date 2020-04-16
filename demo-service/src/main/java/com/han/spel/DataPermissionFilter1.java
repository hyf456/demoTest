package com.han.spel;


import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @Description 数据权限
 * @Date 2020/4/13 18:26
 * @Author hanyf
 */
@Slf4j
@Configuration
public class DataPermissionFilter1 {

	public static void main(String[] args) {
		// test();
		// test1();
		try {
			test2();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	public static void test() {
		User user = new User();
		AdvanceSearchVO advanceSearchVO = new AdvanceSearchVO();
		advanceSearchVO.setGoodsOptCode("大于");
		user.setGoodsSearchAdvance(advanceSearchVO);
		System.out.println(JSONObject.toJSONString(user));
		MetaObject metaObject = new MetaObject(user);
		System.out.println(JSONObject.toJSONString(metaObject.getTarget()));
		String field = "goodsSearchAdvance?.goodsOptCode";
		System.out.println(field + "=" + metaObject.getFieldValue(field));
		metaObject.setFieldValue(field, "小于等于");
		System.out.println(field + "=" + metaObject.getFieldValue(field));
		System.out.println(JSONObject.toJSONString(metaObject.getTarget()));
		System.out.println(JSONObject.toJSONString(user));
	}

	public static void test1() {
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
		try {
			String item = ObjectAccessUtils.get(user, "children[?0]", String.class);
			System.out.println(item);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	private static void test2() throws IllegalAccessException {
		java.util.List<User> userList = new ArrayList<>();
		userList.add(new User().setId(1L).setIdList(Arrays.asList(10L, 14L)));
		userList.add(new User().setId(2L));
		userList.add(new User().setId(3L).setIdList(Arrays.asList(25L, 67L)));
		// Object idList = ObjectAccessUtils.get(userList, "0", Arrays.asList("id"));
		ExpressionParser expressionParser = new org.springframework.expression.spel.standard.SpelExpressionParser();
		Expression expression = expressionParser.parseExpression("id");
		EvaluationContext context = new StandardEvaluationContext(userList);
		Object idList = expression.getValue(context);
		System.out.println(idList);

		Dept dept = new Dept();
		dept.setUserList(userList);

		idList = ObjectAccessUtils.get(dept, "userList", Arrays.asList("idList"));
		System.out.println(idList);
	}
}
