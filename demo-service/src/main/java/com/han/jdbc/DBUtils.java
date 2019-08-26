package com.han.jdbc;

import com.han.commom.User;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description TODO
 * @Date 2019/7/20 15:24
 * @Author hanyf
 */
public class DBUtils {

	public void testQueryUser() {
		Connection conn = null;
		try {
			conn = JdbcUtil.getConnection();
			// 1.创建 QueryRunner 的实现类
			QueryRunner queryRunner = new QueryRunner();

			// 2.使用其 select 方法
			String sql = "SELECT id FROM user WHERE id > 10";
			List<User> userList = (List<User>) queryRunner.query(conn, sql, new BeanListHandler(User.class));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	// public static void main(String[] args) {
	// 	try {
	// 		QueryRunner queryRunner = new QueryRunner();
	//
	// 		String sql = "SELECT * FROM music WHERE id > 5";
	// 		ArrayList<Music> userList = (ArrayList<Music>) queryRunner.query(JdbcUtil.getConnection(), sql, new BeanListHandler(Music.class));
	// 	} catch (Exception e) {
	// 		e.printStackTrace();
	// 	}
	// }

	public ArrayList<User> jdbcQuery() {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		ArrayList<User> list = new ArrayList<User>();
		try {
			// 获得数据的连接
			conn = JdbcUtil.getConnection();
			// 发送SQL语句
			String sql = "SELECT * FROM users";
			// 获得Statement对象
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();
			// 处理结果集
			while (rs.next()) {
				User user = new User();
				user.setId(rs.getInt("id"));
				// user.setUsername(rs.getString("name"));
				// user.setPassword(rs.getString("password"));
				list.add(user);
			}
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public static void main(String[] args) {
		ArrayList<String> strings = new ArrayList<>();
		boolean b = strings.addAll(new ArrayList<User>().stream().map(User::getName).collect(Collectors.toList()));
		System.out.println(b);
	}
}
